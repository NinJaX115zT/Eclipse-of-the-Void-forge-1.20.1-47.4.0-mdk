package net.lucarioninja.eclipseofthevoid.entity.custom.goals.beegoals;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealNestBlockEntity;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
        if (!bee.canEnterHive() || bee.isLingering()) return false;

        // Prefer owned nest first
        if (bee.hasPreferredHome() && !bee.preferredHomeIsHive()) {
            BlockPos preferred = bee.getPreferredHomePos();
            if (preferred != null && bee.level().getBlockState(preferred).getBlock() == ModBlocks.ETHEREAL_NEST.get()) {
                BlockEntity be = bee.level().getBlockEntity(preferred);
                if (be instanceof EtherealNestBlockEntity nest && nest.getBeeCount() < 5) {
                    this.targetNestPos = preferred;
                    return true;
                } else {
                    bee.clearPreferredHome();
                }
            } else {
                bee.clearPreferredHome();
            }
        }

        // Only sometimes look for a brand new nest
        if (bee.getRandom().nextFloat() < 0.85F) {
            return false;
        }

        BlockPos preferred = bee.getActiveHomePos();
        if (preferred != null && !bee.preferredHomeIsHive()) {
            BlockState state = bee.level().getBlockState(preferred);
            if (state.is(ModBlocks.ETHEREAL_NEST.get())) {
                this.targetNestPos = preferred;
                return true;
            }
        }

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

        double targetX = targetNestPos.getX() + 0.5D;
        double targetY = targetNestPos.getY() + 0.5D;
        double targetZ = targetNestPos.getZ() + 0.5D;

        double distSq = bee.distanceToSqr(targetX, targetY, targetZ);

        // Far away = keep pathfinding, do not teleport
        if (distSq > 4.0D) {
            if (bee.getNavigation().isDone()) {
                stuckTicks++;
            } else {
                stuckTicks = 0;
            }

            bee.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);

            // Only refresh path occasionally if needed
            if (stuckTicks > 60) {
                bee.getNavigation().moveTo(targetX, targetY, targetZ, 1.0D);
                stuckTicks = 0;
            }

            return;
        }

        // Close enough to try entering
        BlockEntity beEntity = bee.level().getBlockEntity(targetNestPos);
        if (beEntity instanceof EtherealNestBlockEntity nest) {
            boolean success = nest.tryAddBee(bee);
            if (success) {
                bee.setPreferredHome(targetNestPos, false); // false = nest
                bee.level().playSound(null, targetNestPos, SoundEvents.BEEHIVE_ENTER, bee.getSoundSource(), 1.0F, 1.0F);
                bee.resetNectarProgress();
                bee.markNectarFalse();
                bee.setDeltaMovement(0, 0, 0);
                bee.discard();
            } else {
                bee.setHivePos(null);
                stop();
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
        int radius = 20; // Search radius for nearby nests

        for (BlockPos pos : BlockPos.betweenClosed(beePos.offset(-radius, -16, -radius), beePos.offset(radius, 16, radius))) {
            BlockState state = bee.level().getBlockState(pos);
            if (state.getBlock() == ModBlocks.ETHEREAL_NEST.get()) {
                return pos.immutable();
            }
        }
        return null;
    }
}
