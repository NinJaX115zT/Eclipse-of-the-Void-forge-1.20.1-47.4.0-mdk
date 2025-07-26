package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
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
        return !bee.hasNectar() && bee.getTarget() == null && !bee.getNavigation().isInProgress();
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

        if (stuckTicks > 40) {
            Vec3 forward = bee.getLookAngle().normalize();
            bee.setPos(bee.getX() + forward.x * 2.0D, bee.getY(), bee.getZ() + forward.z * 2.0D);
            bee.getNavigation().stop();
            stuckTicks = 0;
        }
    }

    private Vec3 findWanderPos() {
        Vec3 forward = bee.getLookAngle();
        Vec3 pos = AirAndWaterRandomPos.getPos(bee, 8, 4, -2, forward.x, forward.z, Math.PI / 2D);

        if (pos != null) return pos;

        return AirAndWaterRandomPos.getPos(bee, 8, 4, -2,
                bee.getRandom().nextDouble() * 2 - 1,
                bee.getRandom().nextDouble() * 2 - 1,
                Math.PI / 2D
        );
    }
}
