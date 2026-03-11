package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class EtherealChestBlock extends ChestBlock {
    public EtherealChestBlock(Properties properties) {
        super(properties, () -> ModBlockEntities.ETHEREAL_CHEST.get());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.ETHEREAL_CHEST.get().create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        // Use ENTITYBLOCK_ANIMATED for chest-style models/animations
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return super.combine(state, level, pos, false).apply(new DoubleBlockCombiner.Combiner<ChestBlockEntity, MenuProvider>() {
            public MenuProvider acceptDouble(ChestBlockEntity first, ChestBlockEntity second) {
                // This returns the "Large" name when two chests are combined
                return new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("container.eclipseofthevoid.ethereal_chest_large");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                        // Your container creation logic here
                        return ChestMenu.sixRows(id, inv, new CompoundContainer(first, second));
                    }
                };
            }

            public MenuProvider acceptSingle(ChestBlockEntity single) {
                return single;
            }

            public MenuProvider acceptNone() {
                return null;
            }
        });
    }
}
