package net.lucarioninja.eclipseofthevoid.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealChestBlockEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class EtherealChestRenderer implements BlockEntityRenderer<EtherealChestBlockEntity> {

    public static final Material ETHEREAL_CHEST_SINGLE =
            new Material(Sheets.CHEST_SHEET, new ResourceLocation(EclipseOfTheVoid.MOD_ID, "entity/chest/ethereal_chest"));
    public static final Material ETHEREAL_CHEST_LEFT =
            new Material(Sheets.CHEST_SHEET, new ResourceLocation(EclipseOfTheVoid.MOD_ID, "entity/chest/ethereal_chest_left"));
    public static final Material ETHEREAL_CHEST_RIGHT =
            new Material(Sheets.CHEST_SHEET, new ResourceLocation(EclipseOfTheVoid.MOD_ID, "entity/chest/ethereal_chest_right"));

    private final ModelPart singleLid;
    private final ModelPart singleBottom;
    private final ModelPart singleLock;

    private final ModelPart leftLid;
    private final ModelPart leftBottom;
    private final ModelPart leftLock;

    private final ModelPart rightLid;
    private final ModelPart rightBottom;
    private final ModelPart rightLock;

    public EtherealChestRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart single = context.bakeLayer(ModelLayers.CHEST);
        this.singleBottom = single.getChild("bottom");
        this.singleLid = single.getChild("lid");
        this.singleLock = single.getChild("lock");

        ModelPart left = context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
        this.leftBottom = left.getChild("bottom");
        this.leftLid = left.getChild("lid");
        this.leftLock = left.getChild("lock");

        ModelPart right = context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
        this.rightBottom = right.getChild("bottom");
        this.rightLid = right.getChild("lid");
        this.rightLock = right.getChild("lock");
    }

    @Override
    public void render(EtherealChestBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        BlockState blockstate = blockEntity.getBlockState();
        ChestType chestType = blockstate.getValue(ChestBlock.TYPE);

        Material material;
        ModelPart lid;
        ModelPart bottom;
        ModelPart lock;

        switch (chestType) {
            case LEFT -> {
                material = ETHEREAL_CHEST_LEFT;
                lid = this.leftLid;
                bottom = this.leftBottom;
                lock = this.leftLock;
            }
            case RIGHT -> {
                material = ETHEREAL_CHEST_RIGHT;
                lid = this.rightLid;
                bottom = this.rightBottom;
                lock = this.rightLock;
            }
            default -> {
                material = ETHEREAL_CHEST_SINGLE;
                lid = this.singleLid;
                bottom = this.singleBottom;
                lock = this.singleLock;
            }
        }

        poseStack.pushPose();

        Direction direction = blockstate.getValue(ChestBlock.FACING);
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-direction.toYRot()));
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        float openness = blockEntity.getOpenNess(partialTick);
        openness = 1.0F - openness;
        openness = 1.0F - openness * openness * openness;

        lid.xRot = -(openness * ((float)Math.PI / 2F));
        lock.xRot = lid.xRot;

        VertexConsumer vertexconsumer = material.buffer(bufferSource, net.minecraft.client.renderer.RenderType::entityCutout);

        lid.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        lock.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        bottom.render(poseStack, vertexconsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }
}