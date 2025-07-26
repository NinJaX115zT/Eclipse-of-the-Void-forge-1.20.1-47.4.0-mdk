package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.datagen.ModBlockTagGenerator;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class EtherealPollinationGoal extends Goal {

    private final EtherealBeeEntity bee;
    private BlockPos targetFlower = null;
    private Phase currentPhase = Phase.APPROACHING;
    private int evaluationTicks = 0;
    private int pollinateTicks = 0;
    private int cooldown = 0;
    private int requiredPollinateTicks = 0;

    private enum Phase { APPROACHING, EVALUATING, POLLINATING, REJECTED }

    public EtherealPollinationGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        if (!bee.canPollinate()) return false;

        targetFlower = findNearbyEtherealFlower();
        if (targetFlower != null) {
            bee.setFlowerPos(targetFlower);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetFlower != null && !bee.hasNectar() && currentPhase != Phase.REJECTED;
    }

    @Override
    public void start() {
        currentPhase = Phase.APPROACHING;
        evaluationTicks = 0;
        pollinateTicks = 0;
        requiredPollinateTicks = 50 + bee.getRandom().nextInt(21);
        bee.getNavigation().moveTo(targetFlower.getX() + 0.5, targetFlower.getY() + 0.5, targetFlower.getZ() + 0.5, 0.8D);
    }

    @Override
    public void tick() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (targetFlower == null || bee.level().getBlockState(targetFlower).isAir()) {
            rejectAndReset();
            return;
        }

        double distSq = bee.distanceToSqr(targetFlower.getX() + 0.5, targetFlower.getY() + 0.5, targetFlower.getZ() + 0.5);
        if (distSq > 1.5) {
            bee.getNavigation().moveTo(targetFlower.getX() + 0.5, targetFlower.getY() + 1.0, targetFlower.getZ() + 0.5, 0.8D);
        } else {
            bee.getNavigation().stop();
            handlePollinating();
        }

        // Failsafe: If pollination takes too long, reset
        if (pollinateTicks > requiredPollinateTicks * 2) {
            rejectAndReset();
        }
    }

    private void handleApproaching() {
        double targetX = targetFlower.getX() + 0.5;
        double targetY = targetFlower.getY() + 1.0;
        double targetZ = targetFlower.getZ() + 0.5;

        double distSq = bee.position().distanceToSqr(targetX, targetY, targetZ);
        if (distSq > 1.5) {
            bee.getNavigation().moveTo(targetX, targetY, targetZ, 0.8D);
        } else {
            bee.getNavigation().stop();
            currentPhase = Phase.EVALUATING;
            evaluationTicks = 20 + bee.getRandom().nextInt(20);
        }
    }

    private void handleEvaluating() {
        evaluationTicks--;

        if (evaluationTicks <= 0) {
            currentPhase = bee.getRandom().nextFloat() < 0.7F ? Phase.POLLINATING : Phase.REJECTED;
        }
    }

    private void handlePollinating() {
        bee.setDeltaMovement(0, 0, 0);
        double desiredY = targetFlower.getY() + 1.0;
        double bobbing = Math.sin(pollinateTicks / 10.0) * 0.02;
        bee.setPos(bee.getX(), desiredY + bobbing, bee.getZ());

        // Spin while pollinating (optional, remove if you hate it)
        bee.setYRot(bee.getYRot() + 5.0F);
        bee.setYBodyRot(bee.getYRot());
        bee.setYHeadRot(bee.getYRot());

        bee.addNectarProgress(0.015F);

        if (bee.tickCount % 20 == 0) {
            bee.playSound(SoundEvents.BEE_POLLINATE, 0.5F, 1.0F + bee.getRandom().nextFloat() * 0.2F);
        }

        if (pollinateTicks % 20 == 0 && bee.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FALLING_NECTAR,
                    bee.getX(), bee.getY() + 0.5, bee.getZ(),
                    5, 0.1, 0.1, 0.1, 0);
        }

        pollinateTicks++;

        if (bee.getNectarProgress() >= 1.0F) {
            completePollination();
        }

        if (targetFlower != null) {
            BlockState state = bee.level().getBlockState(targetFlower);
            if (state.is(ModBlockTagGenerator.VOID_CROP_GROWABLES)) {
                state.randomTick((ServerLevel) bee.level(), targetFlower, bee.level().random);
            }
        }
    }

    private void completePollination() {
        bee.setHasNectarFlag(true);
        bee.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
        cooldown = 200;
        currentPhase = Phase.REJECTED;
        bee.resetNectarProgress();

        // ✅ Only grow YOUR modded crops
        if (targetFlower != null) {
            BlockState state = bee.level().getBlockState(targetFlower);
            if (state.is(ModBlockTagGenerator.VOID_CROP_GROWABLES)) {
                state.randomTick((ServerLevel) bee.level(), targetFlower, bee.level().random);
            }
        }

        bee.clearFlowerPos();
        targetFlower = null;
    }

    private void rejectAndReset() {
        bee.resetNectarProgress();
        bee.markNectarFalse();
        currentPhase = Phase.REJECTED;
        bee.getNavigation().stop();
        bee.setDeltaMovement(0, 0, 0);
    }

    private BlockPos findNearbyEtherealFlower() {
        BlockPos origin = bee.blockPosition();
        List<BlockPos> found = new ArrayList<>();
        int range = 64;

        for (int y = -2; y <= 2; y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = origin.offset(x, y, z);
                    if (bee.isEtherealFlower(bee.level().getBlockState(pos))) {
                        found.add(pos.immutable());
                    }
                }
            }
        }

        if (!found.isEmpty()) {
            Collections.shuffle(found, new Random(bee.getRandom().nextLong()));
            return found.get(0);
        }
        return null;
    }
}
