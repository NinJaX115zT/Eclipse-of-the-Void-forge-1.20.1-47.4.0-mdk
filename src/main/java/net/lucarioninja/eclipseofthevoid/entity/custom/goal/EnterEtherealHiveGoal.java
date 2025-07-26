package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

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
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;

public class EnterEtherealHiveGoal extends Goal {

    private final EtherealBeeEntity bee;
    private BlockPos targetHivePos;
    private int stuckTicks = 0;


    public EnterEtherealHiveGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!bee.canEnterHive()) return false;

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

        double distSq = bee.distanceToSqr(targetHivePos.getX() + 0.5D, targetHivePos.getY() + 0.5D, targetHivePos.getZ() + 0.5D);

        // Check if close but not moving
        if (distSq < 64.0D) {
            if (!bee.getNavigation().isInProgress()) {
                stuckTicks++;
            } else {
                stuckTicks = 0;
            }

            if (stuckTicks > 80 && stuckTicks <= 200) {
                bee.teleportSmartlyNearBlock(targetHivePos);
            } else if (stuckTicks > 200) {
                bee.setPos(targetHivePos.getX() + 0.5D, targetHivePos.getY() + 0.5D, targetHivePos.getZ() + 0.5D);
                bee.getNavigation().stop();
                stuckTicks = 0;
            }
        } else {
            // Always pathfind toward the entrance if far away
            bee.getNavigation().moveTo(targetHivePos.getX() + 0.5D, targetHivePos.getY() + 0.5D, targetHivePos.getZ() + 0.5D, 1.0D);
            stuckTicks = 0;
        }

        // Check if inside area
        AABB hiveEntranceArea = new AABB(targetHivePos).inflate(1.5D, 1.0D, 1.5D);
        if (hiveEntranceArea.contains(bee.position())) {
            BlockEntity blockEntity = bee.level().getBlockEntity(targetHivePos);
            if (blockEntity instanceof EtherealHiveBlockEntity hive) {
                hive.addBee(bee);
                bee.resetNectarProgress();
                bee.markNectarFalse();
                bee.onExitHive();
                bee.playSound(SoundEvents.BEEHIVE_ENTER, 1.0F, 1.0F);
                bee.setDeltaMovement(0, 0, 0);
                bee.discard();
            }

            targetHivePos = null;
        }
    }


    @Override
    public void stop() {
        bee.getNavigation().stop();
        targetHivePos = null;
    }

    private BlockPos findNearbyHive() {
        BlockPos beePos = bee.blockPosition();
        int radius = 64; // increased search range (4 chunks)

        for (BlockPos pos : BlockPos.betweenClosed(beePos.offset(-radius, -2, -radius), beePos.offset(radius, 2, radius))) {
            BlockState state = bee.level().getBlockState(pos);
            if (state.getBlock() == ModBlocks.ETHEREAL_HIVE.get()) {
                return pos.immutable();
            }
        }

        return null;
    }

}
