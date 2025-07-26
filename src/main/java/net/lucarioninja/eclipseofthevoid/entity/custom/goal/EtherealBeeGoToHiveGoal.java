package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class EtherealBeeGoToHiveGoal extends Goal {

    private final EtherealBeeEntity bee;
    private int travellingTicks = 0;
    private int ticksStuck = 0;
    private static final int MAX_TRAVEL_TICKS = 600;
    private static final int STUCK_TIMEOUT = 60;

    public EtherealBeeGoToHiveGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (bee.getHivePos() == null || !bee.hasNectar() || bee.getTarget() != null) {
            return false;
        }

        double distSq = bee.blockPosition().distSqr(bee.getHivePos());
        return distSq > 64;
    }

    @Override
    public boolean canContinueToUse() {
        if (bee.getHivePos() == null || !bee.hasNectar()) {
            return false;
        }

        double distSq = bee.blockPosition().distSqr(bee.getHivePos());
        return distSq > 16;
    }

    @Override
    public void start() {
        travellingTicks = 0;
        ticksStuck = 0;
        navigateToHive();
    }

    @Override
    public void tick() {
        travellingTicks++;

        if (travellingTicks > MAX_TRAVEL_TICKS) {
            bee.setHivePos(null); // Forget this hive if too long
            return;
        }

        if (!bee.getNavigation().isInProgress()) {
            navigateToHive();
        } else {
            // Detect stuck navigation
            if (bee.getNavigation().getPath() != null && bee.getNavigation().getPath().isDone()) {
                ticksStuck++;
                if (ticksStuck > STUCK_TIMEOUT) {
                    teleportToHive();
                    ticksStuck = 0;
                }
            } else {
                ticksStuck = 0; // Reset if progressing
            }
        }
    }

    private void teleportToHive() {
        BlockPos hivePos = bee.getHivePos();
        if (hivePos != null) {
            bee.setPos(hivePos.getX() + 0.5D, hivePos.getY() + 0.5D, hivePos.getZ() + 0.5D);
            bee.getNavigation().stop();

            // Optional: Play a teleport sound & particles
            bee.level().playSound(null, hivePos, SoundEvents.ENDERMAN_TELEPORT, bee.getSoundSource(), 0.2f, 2.0f);
            if (bee.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, hivePos.getX() + 0.5D, hivePos.getY() + 0.5D, hivePos.getZ() + 0.5D, 10, 0.1, 0.1, 0.1, 0.0);
            }
        }
    }

    @Override
    public void stop() {
        bee.getNavigation().stop();
    }

    private void navigateToHive() {
        BlockPos hivePos = bee.getHivePos();
        if (hivePos != null) {
            bee.getNavigation().moveTo(
                    hivePos.getX() + 0.5D,
                    hivePos.getY() + 0.5D,
                    hivePos.getZ() + 0.5D,
                    1.0D
            );
        }
    }
}
