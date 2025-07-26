package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class WanderNearEtherealNestGoal extends Goal {
    private final EtherealBeeEntity bee;
    private BlockPos targetNest;

    public WanderNearEtherealNestGoal(EtherealBeeEntity bee) {
        this.bee = bee;
    }

    @Override
    public boolean canUse() {
        return bee.hasNectar() && bee.getHivePos() != null;
    }

    @Override
    public void start() {
        this.targetNest = bee.getHivePos();
    }

    @Override
    public void tick() {
        if (bee.getNavigation().isDone()) {
            BlockPos wanderTarget = targetNest.offset(
                    bee.getRandom().nextInt(5) - 2,
                    bee.getRandom().nextInt(3) - 1,
                    bee.getRandom().nextInt(5) - 2
            );
            bee.getNavigation().moveTo(wanderTarget.getX(), wanderTarget.getY(), wanderTarget.getZ(), 0.6D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return bee.hasNectar() && bee.getHivePos() != null;
    }

    @Override
    public void stop() {
        bee.getNavigation().stop();
    }
}

