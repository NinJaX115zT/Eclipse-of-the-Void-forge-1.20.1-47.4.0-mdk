package net.lucarioninja.eclipseofthevoid.entity.custom;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHiveBlock;
import net.lucarioninja.eclipseofthevoid.datagen.ModBlockTagGenerator;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.goals.beegoals.*;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.player.Player;
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
    private int teleportCooldown = 0;
    private BlockPos preferredHomePos;
    private boolean preferredHomeIsHive = false;

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
        this.goalSelector.addGoal(4, new EnterEtherealHiveGoal(this)); // Try hive BEFORE nest
        this.goalSelector.addGoal(5, new EnterEtherealNestGoal(this)); // Now try nest as backup
        this.goalSelector.addGoal(6, new WanderNearEtherealNestGoal(this)); // Linger near nest/hive if both fail
        this.goalSelector.addGoal(7, new EtherealBeeGrowCropGoal(this)); // Grow crops if it has nectar
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
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D);
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
        if (preferredHomePos != null) {
            tag.putLong("PreferredHomePos", preferredHomePos.asLong());
            tag.putBoolean("PreferredHomeIsHive", preferredHomeIsHive);
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
        if (tag.contains("PreferredHomePos")) {
            preferredHomePos = BlockPos.of(tag.getLong("PreferredHomePos"));
            preferredHomeIsHive = tag.getBoolean("PreferredHomeIsHive");
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

        if (!this.level().isClientSide && this.hasNectar() && this.tickCount % 200 == 0) {
            if (this.getHealth() < this.getMaxHealth()) {
                this.heal(1.0F);

                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            this.getX(), this.getY() + 1.0D, this.getZ(),
                            3, 0.2D, 0.2D, 0.2D, 0.01D);
                }
            }
        }

        if (!this.level().isClientSide && this.tickCount % 40 == 0) {
            if (this.preferredHomePos != null && !this.isValidPreferredHome()) {
                this.clearPreferredHome();
            }

            if (this.hasNectar() && this.preferredHomePos == null && this.hivePos != null) {
                BlockState state = this.level().getBlockState(this.hivePos);
                if (state.is(ModBlocks.ETHEREAL_HIVE.get())) {
                    this.preferredHomePos = this.hivePos;
                    this.preferredHomeIsHive = true;
                }
            }
        }

        tickPollinationCooldown();
        tickHiveCooldown();
        tickLingeringTime();

        // Cooldown for smart teleporting when stuck
        if (teleportCooldown > 0) teleportCooldown--;

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
        if (lingerTime > 0) {
            lingerTime--;
        }
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
            // If stuck in a bad block, immediately try to escape with full teleport
            if (this.isInWall()) {
                // Critical - bee is suffocating
                teleportToNearbyOpenAir();
            } else if (!this.getNavigation().isInProgress()) {
                // Less critical - but still prevent damage
                if (this.tickCount % 40 == 0) {
                    this.smartNudge();
                }
            }
        }
    }

    public void smartNudge() {
        // Prevent spamming teleport if already trying to escape
        if (teleportCooldown > 0) return;

        for (Direction direction : Direction.values()) {
            BlockPos sidePos = this.blockPosition().relative(direction);
            
            // Check if there's safe 2x2 space in this direction
            if (isSafe2x2Space(sidePos)) {
                this.setPos(sidePos.getX() + 0.5D, sidePos.getY() + 0.5D, sidePos.getZ() + 0.5D);
                this.getNavigation().stop();
                this.level().playSound(null, sidePos, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 0.2f, 2.0f);

                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.END_ROD, sidePos.getX() + 0.5D, sidePos.getY() + 0.5D, sidePos.getZ() + 0.5D, 8, 0.1, 0.1, 0.1, 0.0);
                }
                // Set cooldown to prevent immediate re-teleporting if still stuck
                teleportCooldown = 100;
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
        // Check if bee isn't moving despite navigation being in progress (spinning/stuck scenario)
        double velocityMagnitude = this.getDeltaMovement().lengthSqr();
        
        if (this.getTarget() == null) {
            if (this.position().distanceToSqr(lastPosition) < 0.01 || velocityMagnitude < 0.001) {
                stuckCounter++;
                if (stuckCounter > 120) {
                    teleportToNearbyOpenAir();
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

    public boolean isLingering() {
        return this.lingerTime > 0;
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModBlocks.NEBULITE_FLOWER.get().asItem())) {
            if (!this.level().isClientSide) {
                this.heal(10.0F);

                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HEART,
                            this.getX(), this.getY() + 1.0D, this.getZ(),
                            3, 0.2D, 0.2D, 0.2D, 0.01D);
                }

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
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
        this.preferredHomePos = pos;
        this.preferredHomeIsHive = true;
    }

    @Nullable
    public BlockPos getHivePos() {
        return hivePos;
    }

    public void setPreferredHome(BlockPos pos, boolean isHive) {
        this.preferredHomePos = pos;
        this.preferredHomeIsHive = isHive;
    }

    @Nullable
    public BlockPos getPreferredHomePos() {
        return preferredHomePos;
    }

    public boolean hasPreferredHome() {
        return preferredHomePos != null;
    }

    public boolean preferredHomeIsHive() {
        return preferredHomeIsHive;
    }

    public void clearPreferredHome() {
        this.preferredHomePos = null;
    }

    public boolean isValidPreferredHome() {
        if (this.preferredHomePos == null) return false;

        BlockState state = this.level().getBlockState(this.preferredHomePos);

        if (this.preferredHomeIsHive) {
            return state.is(ModBlocks.ETHEREAL_HIVE.get());
        } else {
            return state.is(ModBlocks.ETHEREAL_NEST.get());
        }
    }

    @Nullable
    public BlockPos getActiveHomePos() {
        if (isValidPreferredHome()) {
            return this.preferredHomePos;
        }

        if (this.hivePos != null) {
            BlockState state = this.level().getBlockState(this.hivePos);
            if (state.is(ModBlocks.ETHEREAL_HIVE.get())) {
                this.preferredHomePos = this.hivePos;
                this.preferredHomeIsHive = true;
                return this.hivePos;
            }
        }

        this.clearPreferredHome();
        return null;
    }

    public void setPreferredNest(BlockPos pos) {
        this.preferredHomePos = pos;
        this.preferredHomeIsHive = false;
    }

    public void onExitHive() {
        this.getNavigation().stop();
        this.setLingerTime(200 + this.getRandom().nextInt(200)); // 10–20 seconds
        this.wasJustInHive = true;
        this.hiveReEntryCooldown = 800; // longer before re-entry
        this.pollinationCooldown = 300; // longer before flower obsession resumes
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

        Direction[] horizontalDirections = new Direction[]{
                facing,
                facing.getClockWise(),
                facing.getCounterClockWise(),
                facing.getOpposite()
        };

        java.util.List<Direction> shuffled =
                new java.util.ArrayList<>(java.util.Arrays.asList(horizontalDirections));

        java.util.Collections.shuffle(shuffled, new java.util.Random());

        Direction[] directions = new Direction[]{
                shuffled.get(0),
                shuffled.get(1),
                shuffled.get(2),
                shuffled.get(3),
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
        // Prevent spamming teleport if already trying to escape
        if (teleportCooldown > 0) return;

        this.setPos(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
        this.getNavigation().stop();
        this.level().playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 0.2f, 2.0f);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 10, 0.1, 0.1, 0.1, 0.0);
        }
        teleportCooldown = 100;
    }

    public void resetBeeBrainAfterStuck() {
        this.getNavigation().stop();
        this.clearFlowerPos();
        this.setDeltaMovement(0, 0, 0);
        this.setLingerTime(40 + this.getRandom().nextInt(40)); // short reset wander time
    }

    private boolean isSafe2x2Space(BlockPos pos) {
        // Bee is 2x2, so check all 4 blocks of the footprint
        // Also check 2 blocks up for head clearance
        int[] xOffsets = {0, 1};
        int[] zOffsets = {0, 1};
        
        for (int x : xOffsets) {
            for (int z : zOffsets) {
                BlockPos checkPos = pos.offset(x, 0, z);
                // Check ground level and 2 blocks up
                for (int y = 0; y <= 2; y++) {
                    BlockState state = this.level().getBlockState(checkPos.above(y));
                    if (!state.isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void teleportToNearbyOpenAir() {
        BlockPos origin = this.blockPosition();
        BlockPos targetPos = null;

        // First, try to get the target from preferred home or active navigation
        if (this.getPreferredHomePos() != null) {
            targetPos = this.getPreferredHomePos();
        } else if (this.getTarget() != null) {
            targetPos = this.getTarget().blockPosition();
        } else if (this.getFlowerPos() != null) {
            targetPos = this.getFlowerPos();
        }

        // If target is a hive, try to find a valid 2x2 entrance around it
        if (targetPos != null && this.level().getBlockState(targetPos).getBlock() == ModBlocks.ETHEREAL_HIVE.get()) {
            if (teleportToValidHiveEntrance(targetPos)) {
                return;
            }
        }

        // Prioritize search towards the target direction first
        if (targetPos != null) {
            Vec3 directionToTarget = Vec3.atBottomCenterOf(targetPos).subtract(Vec3.atBottomCenterOf(origin)).normalize();

            // Search in concentric rings, biased towards target direction
            for (int tries = 0; tries < 20; tries++) {
                int offsetX = (int)(directionToTarget.x * (this.getRandom().nextInt(16) - 8));
                int offsetY = (int)(directionToTarget.y * (this.getRandom().nextInt(8) - 4) + this.getRandom().nextInt(5) - 2);
                int offsetZ = (int)(directionToTarget.z * (this.getRandom().nextInt(16) - 8));

                BlockPos candidate = origin.offset(offsetX, offsetY, offsetZ);

                if (isSafe2x2Space(candidate)) {
                    teleportWithEffect(candidate);
                    resetBeeBrainAfterStuck();
                    return;
                }
            }
        }

        // Fallback: random search if no target found or target search failed
        for (int tries = 0; tries < 20; tries++) {
            int offsetX = this.getRandom().nextInt(17) - 8;
            int offsetY = this.getRandom().nextInt(7) - 3;
            int offsetZ = this.getRandom().nextInt(17) - 8;

            BlockPos candidate = origin.offset(offsetX, offsetY, offsetZ);

            if (isSafe2x2Space(candidate)) {
                teleportWithEffect(candidate);
                resetBeeBrainAfterStuck();
                return;
            }
        }
    }

    private boolean teleportToValidHiveEntrance(BlockPos hivePos) {
        // Try to find a 2x2 air space adjacent to the hive on any face
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || direction == Direction.UP) continue; // Skip vertical faces
            
            BlockPos frontPos = hivePos.relative(direction);
            
            // Use the safe 2x2 space check
            if (isSafe2x2Space(frontPos)) {
                // Teleport to the center of this 2x2 space
                double teleportX = frontPos.getX() + 0.5D;
                double teleportZ = frontPos.getZ() + 0.5D;
                double teleportY = frontPos.getY() + 1.0D;
                
                this.setPos(teleportX, teleportY, teleportZ);
                teleportWithEffect(BlockPos.containing(teleportX, teleportY, teleportZ));
                resetBeeBrainAfterStuck();
                return true;
            }
        }
        
        // Fallback: try to squeeze into corners around the hive
        // Try corners on all horizontal faces
        int[][] cornerOffsets = {
            {0, 0}, {1, 0}, {0, 1}, {1, 1}  // 4 corners around the hive
        };
        
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || direction == Direction.UP) continue;
            
            BlockPos basePos = hivePos.relative(direction);
            
            for (int[] offset : cornerOffsets) {
                BlockPos cornerPos = basePos.offset(offset[0], 0, offset[1]);
                
                // Check if corner is passable (at least head height must be clear)
                BlockState cornerState = this.level().getBlockState(cornerPos);
                BlockState cornerAbove = this.level().getBlockState(cornerPos.above());
                
                if (cornerState.isAir() && cornerAbove.isAir()) {
                    // Squeeze into corner
                    double teleportX = cornerPos.getX() + 0.5D;
                    double teleportZ = cornerPos.getZ() + 0.5D;
                    double teleportY = cornerPos.getY() + 1.0D;
                    
                    this.setPos(teleportX, teleportY, teleportZ);
                    teleportWithEffect(BlockPos.containing(teleportX, teleportY, teleportZ));
                    resetBeeBrainAfterStuck();
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (stingCooldown > 0) return false;

        boolean success = super.doHurtTarget(target);
        if (success && target instanceof LivingEntity living) {
            living.hurt(level().damageSources().sting(this), 6.0F); // You can tweak base damage
            living.setSecondsOnFire(0); // No fire, but maybe wither?

            living.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 2));

            // Visual sting feedback
            ((ServerLevel) level()).sendParticles(ParticleTypes.CRIT,
                    target.getX(), target.getY() + 0.5, target.getZ(),
                    5, 0.1, 0.1, 0.1, 0.0);

            // First sting triggers frenzy if not already
            if (remainingStings == 0) {
                remainingStings = 1 + random.nextInt(5); // 1 to 5 extra stings
            }

            stingCooldown = 120; // 6 second cooldown between stings

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
