package net.lucarioninja.eclipseofthevoid.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class EtherealChestItemRenderer extends BlockEntityWithoutLevelRenderer {

    public EtherealChestItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                             MultiBufferSource buffer, int light, int overlay) {

        EtherealChestBlockEntity chest = new EtherealChestBlockEntity(
                BlockPos.ZERO,
                ModBlocks.ETHEREAL_CHEST.get().defaultBlockState()
        );

        Minecraft.getInstance().getBlockEntityRenderDispatcher()
                .renderItem(chest, poseStack, buffer, light, overlay);
    }
}