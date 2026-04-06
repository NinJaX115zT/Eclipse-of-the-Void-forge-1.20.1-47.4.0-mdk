package net.lucarioninja.eclipseofthevoid.entity.custom.goals.beegoals;

import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class EtherealBeeWanderGoal extends Goal {

    private final EtherealBeeEntity bee;
    private int stuckTicks = 0;
    private Vec3 lastPos = Vec3.ZERO;

    public EtherealBeeWanderGoal(EtherealBeeEntity bee) {
        this.bee = bee;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return bee.getTarget() == null
                && !bee.getNavigation().isInProgress()
                && (!bee.hasNectar() || bee.isLingering());
    }

    @Override
    public boolean canContinueToUse() {
        return bee.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        Vec3 target = findWanderPos();
        if (target != null) {
            bee.getNavigation().moveTo(target.x, target.y, target.z, 0.9D);
            lastPos = bee.position();
            stuckTicks = 0;
        }
    }

    @Override
    public void tick() {
        if (bee.position().distanceToSqr(lastPos) < 0.01D) {
            stuckTicks++;
        } else {
            stuckTicks = 0;
            lastPos = bee.position();
        }

        if (stuckTicks > 120) {
            Vec3 safePos = findNearbySafeAirPos();
            if (safePos != null) {
                bee.setPos(safePos.x, safePos.y, safePos.z);
            }

            bee.getNavigation().stop();
            stuckTicks = 0;
        }
    }

    private Vec3 findWanderPos() {
        BlockPos homePos = bee.getActiveHomePos();

        if (homePos != null) {
            for (int i = 0; i < 15; i++) {
                int xOffset = bee.getRandom().nextInt(129) - 64;
                int yOffset = bee.getRandom().nextInt(25) - 12;
                int zOffset = bee.getRandom().nextInt(129) - 64;

                BlockPos candidate = homePos.offset(xOffset, yOffset, zOffset);

                // Don't wander too far below home
                if (candidate.getY() < homePos.getY() - 6) {
                    continue;
                }

                if (isAirSpace(candidate)) {
                    return new Vec3(
                            candidate.getX() + 0.5D,
                            candidate.getY() + 0.5D,
                            candidate.getZ() + 0.5D
                    );
                }
            }
        }

        // fallback if no home or no valid home-based position found
        for (int i = 0; i < 10; i++) {
            int xOffset = bee.getRandom().nextInt(17) - 8;
            int yOffset = bee.getRandom().nextInt(9) - 4;
            int zOffset = bee.getRandom().nextInt(17) - 8;

            BlockPos candidate = BlockPos.containing(
                    bee.getX() + xOffset,
                    bee.getY() + yOffset,
                    bee.getZ() + zOffset
            );

            if (isAirSpace(candidate)) {
                return new Vec3(
                        candidate.getX() + 0.5D,
                        candidate.getY() + 0.5D,
                        candidate.getZ() + 0.5D
                );
            }
        }

        return null;
    }

    private Vec3 findNearbySafeAirPos() {
        BlockPos origin = bee.blockPosition();

        for (int radius = 1; radius <= 4; radius++) {
            for (int x = -radius; x <= radius; x++) {
                for (int y = -2; y <= 2; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos candidate = origin.offset(x, y, z);

                        if (isAirSpace(candidate)) {
                            return new Vec3(
                                    candidate.getX() + 0.5D,
                                    candidate.getY() + 0.5D,
                                    candidate.getZ() + 0.5D
                            );
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isAirSpace(BlockPos pos) {
        return bee.level().getBlockState(pos).isAir();
    }
}