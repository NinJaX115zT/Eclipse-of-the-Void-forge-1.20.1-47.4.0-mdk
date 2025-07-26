package net.lucarioninja.eclipseofthevoid.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NebuliteFlowerBlock extends FlowerBlock {
    public NebuliteFlowerBlock(MobEffect effect, int duration, Properties properties) {
        super(effect, duration, properties);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState soil = worldIn.getBlockState(belowPos);
        return soil.is(Blocks.END_STONE) || soil.is(Blocks.GRASS_BLOCK) || soil.is(Blocks.DIRT) || soil.is(Blocks.FARMLAND);
    }
}
