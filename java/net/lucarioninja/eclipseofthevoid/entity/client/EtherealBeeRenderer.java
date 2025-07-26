package net.lucarioninja.eclipseofthevoid.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class EtherealBeeRenderer extends MobRenderer<EtherealBeeEntity, BeeModel<EtherealBeeEntity>> {

    public EtherealBeeRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeModel<>(context.bakeLayer(ModelLayers.BEE)), 0.4f);

        // Add emissive glow layer
        this.addLayer(new EtherealBeeEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EtherealBeeEntity bee) {
        boolean hasNectar = bee.hasNectar(); // ← must use the overridden method
        if (bee.isAngry() && hasNectar) {
            return new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_angry_nectar.png");
        } else if (bee.isAngry()) {
            return new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_angry.png");
        } else if (hasNectar) {
            return new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_nectar.png");
        } else {
            return new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee.png");
        }
    }

    @Override
    protected int getBlockLightLevel(EtherealBeeEntity bee, BlockPos pos) {
        return bee.isBaby() ? 15 : super.getBlockLightLevel(bee, pos);
    }

    @Override
    public void render(EtherealBeeEntity bee, float yaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {

        if (bee.isBaby()) {
            poseStack.scale(0.6f, 0.6f, 0.6f);
        } else {
            poseStack.scale(1.6f, 1.6f, 1.6f); // adult scale-up
        }

        super.render(bee, yaw, partialTicks, poseStack, buffer, packedLight);
    }
}
