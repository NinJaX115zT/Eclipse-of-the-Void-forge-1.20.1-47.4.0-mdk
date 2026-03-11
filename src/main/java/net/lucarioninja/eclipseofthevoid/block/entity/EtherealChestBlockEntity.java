package net.lucarioninja.eclipseofthevoid.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EtherealChestBlockEntity extends ChestBlockEntity {
    public EtherealChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ETHEREAL_CHEST.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.ETHEREAL_CHEST.get();
    }

    @Override
    protected Component getDefaultName() {
        // This handles the name for a single chest
        return Component.translatable("container.eclipseofthevoid.ethereal_chest");
    }
}
