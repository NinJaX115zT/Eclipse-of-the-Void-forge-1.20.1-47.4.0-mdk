package net.lucarioninja.eclipseofthevoid.worldgen.tree.decorator;

import com.mojang.serialization.Codec;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealNestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;

public class EtherealNestDecorator extends TreeDecorator {
    public static final Codec<EtherealNestDecorator> CODEC =
            Codec.FLOAT.fieldOf("probability").xmap(EtherealNestDecorator::new, d -> d.probability).codec();

    private final float probability;

    public EtherealNestDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.ETHEREAL_NEST_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        if (random.nextFloat() >= this.probability) {
            return;
        }

        List<BlockPos> logs = context.logs();
        List<BlockPos> leaves = context.leaves();

        if (logs.isEmpty() || leaves.isEmpty()) {
            return;
        }

        int minY = logs.get(0).getY() + 2;
        int maxY = logs.get(logs.size() - 1).getY() - 1;

        List<BlockPos> candidates = new ArrayList<>();

        for (BlockPos logPos : logs) {
            if (logPos.getY() < minY || logPos.getY() > maxY) continue;

            for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
                BlockPos nestPos = logPos.relative(dir);

                if (context.isAir(nestPos)) {
                    candidates.add(nestPos);
                }
            }
        }

        if (candidates.isEmpty()) {
            return;
        }

        BlockPos chosen = candidates.get(random.nextInt(candidates.size()));

        BlockState nestState = ModBlocks.ETHEREAL_NEST.get()
                .defaultBlockState()
                .setValue(EtherealNestBlock.FACING, Direction.NORTH)
                .setValue(EtherealNestBlock.GENERATED, true);

        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
            BlockPos supportPos = chosen.relative(dir.getOpposite());
            if (logs.contains(supportPos)) {
                nestState = nestState.setValue(HorizontalDirectionalBlock.FACING, dir.getOpposite());
                break;
            }
        }

        context.setBlock(chosen, nestState);
    }
}
