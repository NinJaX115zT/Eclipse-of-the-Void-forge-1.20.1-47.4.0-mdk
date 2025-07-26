package net.lucarioninja.eclipseofthevoid.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EtherealBeeEmissiveLayer extends RenderLayer<EtherealBeeEntity, BeeModel<EtherealBeeEntity>> {

    public EtherealBeeEmissiveLayer(RenderLayerParent<EtherealBeeEntity, BeeModel<EtherealBeeEntity>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, EtherealBeeEntity bee,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                       float netHeadYaw, float headPitch) {

        // Select texture based on bee state
        ResourceLocation glowTexture;
        boolean hasNectar = bee.hasNectar();
        boolean isAngry = bee.isAngry();

        if (isAngry && hasNectar) {
            glowTexture = new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_angry_nectar_glow.png");
        } else if (isAngry) {
            glowTexture = new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_angry_glow.png");
        } else if (hasNectar) {
            glowTexture = new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_nectar_glow.png");
        } else {
            glowTexture = new ResourceLocation(EclipseOfTheVoid.MOD_ID, "textures/entity/ethereal_bee/ethereal_bee_glow.png");
        }

        // Pulsating brightness effect
        float time = (bee.tickCount + partialTicks) / 10.0F;
        float brightness = 0.3F + 0.2F * (float)Math.sin(time);

        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(glowTexture));

        // Render the glow layer with dynamic brightness
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY,
                brightness, brightness * 0.5F, brightness, 1.0F);
    }
}
