package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.datagen.ModBlockTagGenerator;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EtherealBeeGrowCropGoal extends Goal {

    private final EtherealBeeEntity bee;
    private BlockPos targetCropPos;
    private int cooldownTicks = 0;

    public EtherealBeeGrowCropGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!bee.hasNectar()) return false; // Only when has nectar
        if (bee.getTarget() != null) return false;
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        BlockPos foundCrop = findNearbyVoidCrop();
        if (foundCrop != null) {
            targetCropPos = foundCrop;
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        if (targetCropPos != null) {
            bee.getNavigation().moveTo(targetCropPos.getX() + 0.5D, targetCropPos.getY() + 0.5D, targetCropPos.getZ() + 0.5D, 1.0D);
        }
    }

    @Override
    public void tick() {
        if (targetCropPos == null) return;

        // Visual cue: if ready to grow, subtle particles
        if (bee.level() instanceof ServerLevel serverLevel && bee.tickCount % 10 == 0) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                    bee.getX(), bee.getY() + 0.5D, bee.getZ(),
                    2, 0.1, 0.1, 0.1, 0.0);
        }

        if (bee.distanceToSqr(targetCropPos.getX() + 0.5D, targetCropPos.getY() + 0.5D, targetCropPos.getZ() + 0.5D) < 4.0D) {
            BlockState state = bee.level().getBlockState(targetCropPos);
            if (state.getBlock() instanceof BonemealableBlock crop && crop.isValidBonemealTarget(bee.level(), targetCropPos, state, false)) {
                crop.performBonemeal((ServerLevel) bee.level(), bee.getRandom(), targetCropPos, state);

                // After growing, bee loses nectar
                bee.markNectarFalse();

                // Cooldown after crop growth
                cooldownTicks = 200;
            }

            targetCropPos = null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetCropPos != null && bee.hasNectar();
    }

    private BlockPos findNearbyVoidCrop() {
        BlockPos beePos = bee.blockPosition();
        int range = 16;
        List<BlockPos> candidates = new ArrayList<>();

        for (BlockPos pos : BlockPos.betweenClosed(beePos.offset(-range, -2, -range), beePos.offset(range, 2, range))) {
            BlockState state = bee.level().getBlockState(pos);
            if (state.is(ModBlockTagGenerator.VOID_CROP_GROWABLES)) {
                if (state.getBlock() instanceof BonemealableBlock crop) {
                    if (crop.isValidBonemealTarget(bee.level(), pos, state, false)) {
                        candidates.add(pos.immutable());
                    }
                }
            }
        }

        if (!candidates.isEmpty()) {
            return candidates.get(bee.getRandom().nextInt(candidates.size()));
        }

        return null;
    }
}
