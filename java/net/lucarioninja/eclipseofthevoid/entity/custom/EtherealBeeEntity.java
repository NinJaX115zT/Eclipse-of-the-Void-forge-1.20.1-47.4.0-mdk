package net.lucarioninja.eclipseofthevoid.entity.custom;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHiveBlock;
import net.lucarioninja.eclipseofthevoid.datagen.ModBlockTagGenerator;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.goal.*;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class EtherealBeeEntity extends Bee {
    private static final EntityDataAccessor<Boolean> DATA_HAS_NECTAR = SynchedEntityData.defineId(EtherealBeeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<BlockPos>> FLOWER_POS = SynchedEntityData.defineId(EtherealBeeEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private BlockPos lastPollinatedFlower = BlockPos.ZERO;
    private BlockPos hivePos;
    private boolean wasJustInHive = false;
    private float nectarProgress = 0.0F;
    private int lingerTime = 0;
    private int remainingStings = 0;
    private int stingCooldown = 0;
    private int pollinationCooldown = 0;
    private int hiveReEntryCooldown = 0;
    private Vec3 lastPosition = Vec3.ZERO;
    private int stuckCounter = 0;

    public EtherealBeeEntity(EntityType<? extends Bee> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().setSpeedModifier(0.6F);

        // Remove vanilla goals
        this.goalSelector.getAvailableGoals().removeIf(goal -> goal.getGoal().getClass().getSimpleName().equals("BeePollinateGoal"));
        this.goalSelector.getAvailableGoals().removeIf(goal -> {return goal.getGoal() != null && (goal.getGoal().getClass().getSimpleName().toLowerCase().contains("hive"));});

        // Custom Goals
        this.goalSelector.addGoal(1, new TemptGoal(this, 0.6D, Ingredient.of(ModBlocks.NEBULITE_FLOWER.get().asItem()), false)); // Player temptation
        this.goalSelector.addGoal(2, new AvoidVanillaFlowerHolderGoal(this, 1.0D, 1.4D)); // Avoid vanilla flowers
        this.goalSelector.addGoal(3, new EtherealBeeGoToHiveGoal(this)); // Go to hive (has nectar)
        this.goalSelector.addGoal(4, new EnterEtherealHiveGoal(this)); // ✅ Try hive BEFORE nest
        this.goalSelector.addGoal(5, new EnterEtherealNestGoal(this)); // Now try nest as backup
        this.goalSelector.addGoal(6, new WanderNearEtherealNestGoal(this)); // Linger near nest/hive if both fail
        this.goalSelector.addGoal(7, new EtherealBeeGrowCropGoal(this)); // Grow crops if has nectar
        this.goalSelector.addGoal(8, new EtherealPollinationGoal(this)); // Pollinate when empty
        this.goalSelector.addGoal(9, new EtherealBeeWanderGoal(this)); // Random wandering
        this.goalSelector.addGoal(10, new FloatGoal(this)); // Float on water

        // Target Goals (anger, hurt)
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.FLYING_SPEED, 1.2D)
                .add(Attributes.MOVEMENT_SPEED, 0.9D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAS_NECTAR, false);
        this.entityData.define(FLOWER_POS, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("NectarProgress", nectarProgress);
        tag.putBoolean("HasNectar", this.hasNectar());
        if (hivePos != null) {
            tag.putLong("HivePos", hivePos.asLong());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        nectarProgress = tag.getFloat("NectarProgress");
        this.setHasNectarFlag(tag.getBoolean("HasNectar"));
        if (tag.contains("HivePos")) {
            hivePos = BlockPos.of(tag.getLong("HivePos"));
        }
    }

    public void setHasNectarFlag(boolean hasNectar) {
        if (this.entityData.get(DATA_HAS_NECTAR) != hasNectar) {
            this.entityData.set(DATA_HAS_NECTAR, hasNectar);
        }
    }

    @Override
    public void tick() {
        super.tick();

        tickPollinationCooldown();
        tickHiveCooldown();
        tickLingeringTime();

        // Early exit if lingering (post-pollination chill phase)
        if (lingerTime > 0) return;

        tickHoverEffects();
        tickStingFrenzy();
        tickBlockSafetyCheck();
        tickIdleDampening();
        tickStuckCheck();
    }

    private void tickPollinationCooldown() {
        if (pollinationCooldown > 0) pollinationCooldown--;
    }

    private void tickHiveCooldown() {
        if (hiveReEntryCooldown > 0) hiveReEntryCooldown--;
    }

    private void tickLingeringTime() {
        if (this.hasNectar() && this.getHivePos() == null && !this.isVehicle()) {
            lingerTime = this.random.nextInt(40) + 40;
        }
        if (lingerTime > 0) lingerTime--;
    }

    private void tickHoverEffects() {
        if (this.isAlive() && lingerTime == 0 && this.getHivePos() == null && !this.isVehicle()) {
            double hoverOffset = Math.sin(this.tickCount / 10.0) * 0.03;
            this.setPos(this.getX(), this.getY() + hoverOffset, this.getZ());

            if (this.level() instanceof ServerLevel serverLevel && this.tickCount % 4 == 0) {
                Vec3 lookVec = this.getLookAngle().normalize();
                Vec3 backOffset = this.position().subtract(lookVec.scale(0.3)).add(0, 0.2, 0);

                var particleData = new DustColorTransitionOptions(
                        Vec3.fromRGB24(0xCA658E).toVector3f(),
                        Vec3.fromRGB24(0x3A2339).toVector3f(),
                        1.0f
                );
                serverLevel.sendParticles(particleData, backOffset.x, backOffset.y, backOffset.z, 1, 0.02, 0.02, 0.02, 0.0);
            }
        }
    }

    private void tickStingFrenzy() {
        if (remainingStings > 0 && stingCooldown == 0 && this.getTarget() != null && this.distanceTo(this.getTarget()) < 2.0D) {
            this.doHurtTarget(this.getTarget());
            remainingStings--;
            stingCooldown = 10;
        }

        if (stingCooldown > 0) stingCooldown--;
    }

    private void tickBlockSafetyCheck() {
        BlockState stateBelow = this.getBlockStateOn();
        boolean isInBadBlock = this.isInWall() || (
                stateBelow.isSolid() &&
                        !stateBelow.is(ModBlockTagGenerator.POLLINATION_FLOWERS) &&
                        !stateBelow.is(ModBlockTagGenerator.VOID_CROP_GROWABLES) &&
                        !stateBelow.is(ModBlocks.ETHEREAL_HIVE.get())
        );

        if (isInBadBlock) {
            if (this.tickCount % 20 == 0) {
                this.smartNudge();
            }
        }
    }

    public void smartNudge() {
        for (Direction direction : Direction.values()) {
            BlockPos sidePos = this.blockPosition().relative(direction);
            BlockState sideState = this.level().getBlockState(sidePos);

            if (sideState.isAir()) {
                this.setPos(sidePos.getX() + 0.5D, sidePos.getY() + 0.5D, sidePos.getZ() + 0.5D);
                this.getNavigation().stop();
                this.level().playSound(null, sidePos, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 0.2f, 2.0f);

                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.END_ROD, sidePos.getX() + 0.5D, sidePos.getY() + 0.5D, sidePos.getZ() + 0.5D, 8, 0.1, 0.1, 0.1, 0.0);
                }
                break;
            }
        }
    }

    private void tickIdleDampening() {
        if (!this.getNavigation().isInProgress() && this.getTarget() == null && !this.hasNectar()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8D));
        }
    }

    private void tickStuckCheck() {
        if (!this.getNavigation().isInProgress() && this.getTarget() == null) {
            if (this.position().distanceToSqr(lastPosition) < 0.01) {
                stuckCounter++;
                if (stuckCounter > 60) {
                    teleportSmartlyNearBlock(this.blockPosition().above(3));
                    stuckCounter = 0;
                }
            } else {
                stuckCounter = 0;
            }
            lastPosition = this.position();
        }
    }

    public boolean hasNectar() {
        return this.entityData.get(DATA_HAS_NECTAR);
    }

    public void markNectarTrue() {
        setHasNectarFlag(true);
    }

    public void markNectarFalse() {
        if (this.hasNectar()) {
            setHasNectarFlag(false);
        }
    }

    public void setLingerTime(int time) {
        this.lingerTime = time;
    }

    public float getNectarProgress() {
        return nectarProgress;
    }

    public void addNectarProgress(float amount) {
        nectarProgress = Math.min(1.0F, nectarProgress + amount);
    }

    public void resetNectarProgress() {
        nectarProgress = 0.0F;
    }

    public boolean canPollinate() {
        return pollinationCooldown <= 0 && !this.hasNectar();
    }

    public boolean canEnterHive() {
        return this.hasNectar() && hiveReEntryCooldown <= 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEE_LOOP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (this.hasNectar()) {
            int amount = 1 + this.random.nextInt(looting + 1); // 1 guaranteed, up to +looting bonus
            for (int i = 0; i < amount; i++) {
                this.spawnAtLocation(ModItems.ETHEREAL_HONEYCOMB_CELL.get());
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        10, 0.2, 0.3, 0.2, 0.01
                );
            }

            this.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.2F);
        }
    }

    public boolean isEtherealFlower(BlockState state) {
        return state.is(ModBlockTagGenerator.POLLINATION_FLOWERS) && !state.is(BlockTags.FLOWERS);
    }

    @Nullable
    public BlockPos getFlowerPos() {
        return this.entityData.get(FLOWER_POS).orElse(null);
    }

    public void setFlowerPos(@Nullable BlockPos pos) {
        this.entityData.set(FLOWER_POS, Optional.ofNullable(pos));
    }

    public void clearFlowerPos() {
        this.entityData.set(FLOWER_POS, Optional.empty());
    }

    public BlockPos getLastPollinatedFlower() {
        return lastPollinatedFlower;
    }

    public void setLastPollinatedFlower(BlockPos pos) {
        this.lastPollinatedFlower = pos.immutable();
    }

    public boolean closerThan(BlockPos pos, double distance) {
        return pos.closerThan(this.blockPosition(), distance);
    }

    public void setHivePos(BlockPos pos) {
        this.hivePos = pos;
    }

    @Nullable
    public BlockPos getHivePos() {
        return hivePos;
    }

    public void onExitHive() {
        this.getNavigation().stop();
        this.setLingerTime(this.getRandom().nextInt(60) + 40);
        this.wasJustInHive = true;
        this.hiveReEntryCooldown = 600; // 30 seconds before allowed back in
        this.pollinationCooldown = 200; // 10 seconds before allowed to pollinate again
    }

    public boolean consumeJustExitedHiveFlag() {
        if (this.wasJustInHive) {
            this.wasJustInHive = false;
            return true;
        }
        return false;
    }

    public void resetJustExitedHiveFlag() {
        this.wasJustInHive = true;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModBlocks.NEBULITE_FLOWER.get().asItem().builtInRegistryHolder());
    }

    @Override
    public EtherealBeeEntity getBreedOffspring(ServerLevel level, AgeableMob partner) {
        EtherealBeeEntity baby = ModEntities.ETHEREAL_BEE.get().create(level);
        return baby;
    }

    public void teleportSmartlyNearBlock(BlockPos blockPos) {
        if (blockPos == null) return;

        // Check all sides (facing, clockwise, counter, opposite, up, down)
        BlockState state = this.level().getBlockState(blockPos);
        Direction facing = state.getOptionalValue(EtherealHiveBlock.FACING).orElse(Direction.NORTH);

        Direction[] directions = new Direction[]{
                facing,
                facing.getClockWise(),
                facing.getCounterClockWise(),
                facing.getOpposite(),
                Direction.UP,
                Direction.DOWN
        };

        // Try to teleport to an adjacent air block first
        for (Direction direction : directions) {
            BlockPos sidePos = blockPos.relative(direction);
            if (this.level().getBlockState(sidePos).isAir()) {
                teleportWithEffect(sidePos);
                return;
            }
        }

        // If no air block found, fallback teleport inside the block itself
        teleportWithEffect(blockPos);
    }

    private void teleportWithEffect(BlockPos pos) {
        this.setPos(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
        this.getNavigation().stop();
        this.level().playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 0.2f, 2.0f);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 10, 0.1, 0.1, 0.1, 0.0);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (stingCooldown > 0) return false;

        boolean success = super.doHurtTarget(target);
        if (success && target instanceof LivingEntity living) {
            living.hurt(level().damageSources().sting(this), 4.0F); // You can tweak base damage
            living.setSecondsOnFire(0); // No fire, but maybe wither?

            living.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0)); // Small wither tick

            // Visual sting feedback
            ((ServerLevel) level()).sendParticles(ParticleTypes.CRIT,
                    target.getX(), target.getY() + 0.5, target.getZ(),
                    5, 0.1, 0.1, 0.1, 0.0);

            // First sting triggers frenzy if not already
            if (remainingStings == 0) {
                remainingStings = 1 + random.nextInt(5); // 1 to 5 extra stings
            }

            stingCooldown = 20; // 1-second cooldown between stings

            // Final sting wrap-up
            if (remainingStings <= 0) {
                if (random.nextFloat() < 0.3F) { // 30% chance to die
                    this.discard(); // Bee dies ethereally
                } else {
                    this.setHasNectarFlag(false); // Drops nectar if relevant
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }
}
