package net.lucarioninja.eclipseofthevoid.item.custom;

import net.lucarioninja.eclipseofthevoid.entity.client.EtherealChestItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class EtherealChestItem extends BlockItem {

    public EtherealChestItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new EtherealChestItemRenderer();
            }
        });
    }
}