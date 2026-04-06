/*package net.lucarioninja.eclipseofthevoid.entity.custom;

import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class InfernalConstructEntity extends IronGolem {
    private static final EntityDataAccessor<Boolean> DATA_HAS_CORE = SynchedEntityData.defineId(InfernalConstructEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(InfernalConstructEntity.class, EntityDataSerializers.BYTE);

    public InfernalConstructEntity(EntityType<? extends IronGolem> type, Level world) {
        super(type, world);
        this.setMaxUpStep(1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().setSpeedModifier(0.6F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public boolean hasCore() {
        return this.entityData.get(DATA_HAS_CORE);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (this.hasCore()) {
            int amount = 1 + this.random.nextInt(looting + 1); // 1 guaranteed, up to +looting bonus
            for (int i = 0; i < amount; i++) {
                this.spawnAtLocation(ModItems.INFERNAL_CORE.get());
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.ENCHANT,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        10, 0.2, 0.3, 0.2, 0.01
                );
            }
            this.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 0.5F, 1.2F);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
    }
}*/
