package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealNestBlock;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealNestBlockEntity;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;

public class EnterEtherealNestGoal extends Goal {

    private final EtherealBeeEntity bee;
    private BlockPos targetNestPos;
    private int stuckTicks = 0;

    public EnterEtherealNestGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!bee.canEnterHive()) return false;

        BlockPos nearestNest = findNearbyNest();
        if (nearestNest != null) {
            BlockEntity be = bee.level().getBlockEntity(nearestNest);
            if (be instanceof EtherealNestBlockEntity nest && nest.getBeeCount() < 5) {
                this.targetNestPos = nearestNest;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetNestPos != null && bee.hasNectar();
    }

    @Override
    public void start() {
        bee.getNavigation().moveTo(
                targetNestPos.getX() + 0.5,
                targetNestPos.getY() + 0.5,
                targetNestPos.getZ() + 0.5,
                1.0D
        );
    }

    @Override
    public void tick() {
        if (targetNestPos == null) return;

        double distSq = bee.distanceToSqr(targetNestPos.getX() + 0.5, targetNestPos.getY() + 0.5, targetNestPos.getZ() + 0.5);

        // Retry movement if stuck
        if (!bee.getNavigation().isInProgress()) {
            stuckTicks++;
            if (stuckTicks > 100) {
                bee.getNavigation().moveTo(
                        targetNestPos.getX() + 0.5,
                        targetNestPos.getY() + 0.5,
                        targetNestPos.getZ() + 0.5,
                        1.0D
                );
                stuckTicks = 0;
            }
        }

        // Within entrance radius?
        AABB nestArea = new AABB(targetNestPos).inflate(1.5D, 1.0D, 1.5D);
        if (nestArea.contains(bee.position())) {
            BlockEntity beEntity = bee.level().getBlockEntity(targetNestPos);
            if (beEntity instanceof EtherealNestBlockEntity nest) {
                boolean success = nest.tryAddBee(bee);
                if (success) {
                    bee.resetNectarProgress();
                    bee.markNectarFalse();
                    bee.onExitHive();
                    bee.playSound(SoundEvents.BEEHIVE_ENTER, 1.0F, 1.0F);
                    bee.setDeltaMovement(0, 0, 0);
                    bee.discard();
                } else {
                    // Abort — nest full
                    bee.setHivePos(null);
                    stop();
                }
            }
        }
    }

    @Override
    public void stop() {
        bee.getNavigation().stop();
        targetNestPos = null;
    }

    private BlockPos findNearbyNest() {
        BlockPos beePos = bee.blockPosition();
        int radius = 64;

        for (BlockPos pos : BlockPos.betweenClosed(beePos.offset(-radius, -2, -radius), beePos.offset(radius, 2, radius))) {
            BlockState state = bee.level().getBlockState(pos);
            if (state.getBlock() == ModBlocks.ETHEREAL_NEST.get()) {
                return pos.immutable();
            }
        }
        return null;
    }
}
