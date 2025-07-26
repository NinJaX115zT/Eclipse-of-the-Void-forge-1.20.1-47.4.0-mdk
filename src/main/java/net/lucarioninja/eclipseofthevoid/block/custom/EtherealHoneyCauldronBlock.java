package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class EtherealHoneyCauldronBlock extends CauldronBlock {
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 1, 3);


    public EtherealHoneyCauldronBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 3)); // start full
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (!level.isClientSide && item.is(Items.GLASS_BOTTLE)) {
            int currentLevel = state.getValue(LEVEL);

            if (!player.isCreative()) {
                item.shrink(1);
                ItemStack bottle = new ItemStack(ModItems.ETHEREAL_HONEY_BOTTLE.get());
                if (!player.getInventory().add(bottle)) player.drop(bottle, false);
            }

            if (currentLevel > 1) {
                level.setBlock(pos, state.setValue(LEVEL, currentLevel - 1), 3);
            } else {
                level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
            }

            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.2F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}

