package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class EtherealHoneyBlock extends HoneyBlock {

    public EtherealHoneyBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        // Don't apply fall damage at all
        entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        if (!entity.isSuppressingBounce()) {
            double bounce = entity.getDeltaMovement().y() * -0.9D;
            entity.setDeltaMovement(entity.getDeltaMovement().x(), bounce, entity.getDeltaMovement().z());
        } else {
            super.updateEntityAfterFallOn(level, entity);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            double x = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D);
            double y = (double)pos.getY() + 0.1D;
            double z = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0D, 0.8D, 1.0D)); // sticky drag like honey
        }
        super.stepOn(level, pos, state, entity);
    }

    // Override to allow jumping (unlike vanilla honey block)
    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
}
