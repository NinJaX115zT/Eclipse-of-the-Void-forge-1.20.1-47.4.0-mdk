package net.lucarioninja.eclipseofthevoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

public class VoidCapeRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation CAPE_TEXTURE = new ResourceLocation("eclipseofthevoid", "textures/entity/void_cape.png");
    private final ModelPart cape;

    public VoidCapeRenderer(PlayerRenderer renderer) {
        super(renderer);

        // Build the mesh for the cape
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("cape",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1),
                net.minecraft.client.model.geom.PartPose.offset(0.0F, 0.0F, 0.0F));

        LayerDefinition layerDefinition = LayerDefinition.create(meshDefinition, 64, 32);
        this.cape = layerDefinition.bakeRoot().getChild("cape");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!player.isInvisible()
                && player.getUUID().toString().equals("380df991-f603-344c-a090-369bad2a924a")
                && player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST).getItem() != net.minecraft.world.item.Items.ELYTRA) {

            poseStack.pushPose();

            double dx = player.xCloakO + (player.xCloak - player.xCloakO) * partialTick - (player.xo + (player.getX() - player.xo) * partialTick);
            double dy = player.yCloakO + (player.yCloak - player.yCloakO) * partialTick - (player.yo + (player.getY() - player.yo) * partialTick);
            double dz = player.zCloakO + (player.zCloak - player.zCloakO) * partialTick - (player.zo + (player.getZ() - player.zo) * partialTick);

            float bodyRot = player.yBodyRot;
            float sin = (float) Math.sin(bodyRot * Math.PI / 180.0F);
            float cos = (float) -Math.cos(bodyRot * Math.PI / 180.0F);

            float flap = (float) dy * 10.0F;
            flap = clamp(flap);

            float sway = (float) (dx * sin + dz * cos) * 100.0F;
            float shift = (float) (dx * cos - dz * sin) * 100.0F;

            if (sway < 0.0F) sway = 0.0F;

            float bob = interpolate(partialTick, player.oBob, player.bob);
            flap += (float) (Math.sin(interpolate(partialTick, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * bob);

            // Adjust cape position for crouching
            if (player.isCrouching()) {
                poseStack.translate(0.0D, 0.2D, 0.03D); // Raise and slightly push forward
                poseStack.mulPose(new Quaternionf(new AxisAngle4f((float) Math.toRadians(15), 1.0F, 0.0F, 0.0F))); // Tilt cape forward
            }

            poseStack.translate(0.0D, 0.0D, 0.125D);
            // Cape tilt (forward and backward flap)
            poseStack.mulPose(new Quaternionf(new AxisAngle4f((6.0F + sway / 2.0F + flap) * ((float)Math.PI / 180F), 1.0F, 0.0F, 0.0F)));
            // Cape twist (left and right sway)
            poseStack.mulPose(new Quaternionf(new AxisAngle4f((float)Math.PI, 0.0F, 1.0F, 0.0F)));

            this.cape.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(CAPE_TEXTURE)), packedLight,
                    LivingEntityRenderer.getOverlayCoords(player, 0.0F));

            poseStack.popPose();
        }
    }

    private float interpolate(float partialTick, float start, float end) {
        return start + (end - start) * partialTick;
    }

    private float clamp(float value) {
        return Math.max(-6.0F, Math.min(32.0F, value));
    }
}
