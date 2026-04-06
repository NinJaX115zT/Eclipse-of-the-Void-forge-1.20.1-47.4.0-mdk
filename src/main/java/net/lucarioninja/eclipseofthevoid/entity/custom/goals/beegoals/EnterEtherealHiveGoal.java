package net.lucarioninja.eclipseofthevoid.entity.custom.goals.beegoals;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHiveBlock;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealHiveBlockEntity;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EnterEtherealHiveGoal extends Goal {

    private final EtherealBeeEntity bee;
    private BlockPos targetHivePos;
    private int stuckTicks = 0;
    private Vec3 lastBeePos = Vec3.ZERO;
    private int closeRangeStuckTicks = 0;


    public EnterEtherealHiveGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!bee.canEnterHive() || bee.isLingering()) return false;

        // Prefer owned hive first
        if (bee.hasPreferredHome() && bee.preferredHomeIsHive()) {
            BlockPos preferred = bee.getPreferredHomePos();
            if (preferred != null && bee.level().getBlockState(preferred).getBlock() == ModBlocks.ETHEREAL_HIVE.get()) {
                this.targetHivePos = preferred;
                return true;
            } else {
                bee.clearPreferredHome();
            }
        }

        // Only sometimes look for a new hive
        if (bee.getRandom().nextFloat() < 0.75F) {
            return false;
        }

        BlockPos preferred = bee.getActiveHomePos();
        if (preferred != null && bee.preferredHomeIsHive()) {
            BlockState state = bee.level().getBlockState(preferred);
            if (state.is(ModBlocks.ETHEREAL_HIVE.get())) {
                bee.setHivePos(preferred);
                this.targetHivePos = preferred;
                return true;
            }
        }

        BlockPos nearestHive = findNearbyHive();
        if (nearestHive != null) {
            this.targetHivePos = nearestHive;
            return true;
        }

        targetHivePos = null;
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetHivePos != null && bee.hasNectar();
    }

    @Override
    public void start() {
        lastBeePos = bee.position();
        closeRangeStuckTicks = 0;
        
        // Get hive facing to offset entrance position
        BlockState state = bee.level().getBlockState(targetHivePos);
        Direction facing = state.getOptionalValue(EtherealHiveBlock.FACING).orElse(Direction.NORTH);

        // Offset target position slightly in front of hive entrance
        double offsetX = facing.getStepX() * 0.5D;
        double offsetZ = facing.getStepZ() * 0.5D;

        bee.getNavigation().moveTo(
                targetHivePos.getX() + 0.5D + offsetX,
                targetHivePos.getY() + 0.5D,
                targetHivePos.getZ() + 0.5D + offsetZ,
                1.0D
        );
    }

    @Override
    public void tick() {
        if (targetHivePos == null) return;

        double targetX = targetHivePos.getX() + 0.5D;
        double targetY = targetHivePos.getY() + 0.5D;
        double targetZ = targetHivePos.getZ() + 0.5D;

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

        // Very close to hive - check if bee is actually making progress
        double movementSqr = bee.position().distanceToSqr(lastBeePos);
        lastBeePos = bee.position();
        
        if (movementSqr < 0.0001D) {
            closeRangeStuckTicks++;
            
            // If stuck for too long while very close, trigger teleport to alternate entrance
            if (closeRangeStuckTicks > 20) {
                bee.teleportToNearbyOpenAir();
                closeRangeStuckTicks = 0;
                stuckTicks = 0;
                return;
            }
        } else {
            closeRangeStuckTicks = 0;
        }

        // Close enough to try entering
        BlockEntity blockEntity = bee.level().getBlockEntity(targetHivePos);
        if (blockEntity instanceof EtherealHiveBlockEntity hive) {
            hive.addBee(bee);
            bee.setPreferredHome(targetHivePos, true);
            bee.resetNectarProgress();
            bee.markNectarFalse();
            bee.playSound(SoundEvents.BEEHIVE_ENTER, 1.0F, 1.0F);
            bee.setDeltaMovement(0, 0, 0);
            bee.discard();
        }

        targetHivePos = null;
    }


    @Override
    public void stop() {
        bee.getNavigation().stop();
        targetHivePos = null;
    }

    private BlockPos findNearbyHive() {
        BlockPos beePos = bee.blockPosition();
        int radius = 20; // Search radius for nearby hives

        for (BlockPos pos : BlockPos.betweenClosed(beePos.offset(-radius, -2, -radius), beePos.offset(radius, 2, radius))) {
            BlockState state = bee.level().getBlockState(pos);
            if (state.getBlock() == ModBlocks.ETHEREAL_HIVE.get()) {
                return pos.immutable();
            }
        }

        return null;
    }

}
