package com.mc3699.codmod.entity.codmissile;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CodMissileEntityRenderer extends EntityRenderer<CodMissileEntity> {
    private final CodMissileModel model;
    public CodMissileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CodMissileModel(context.bakeLayer(CodMissileModel.LAYER_LOCATION));
    }

    @Override
    public void render(CodMissileEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0,3.7,0);

        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
        poseStack.translate(x - entity.getX(), y - entity.getY(), z - entity.getZ());
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));


        poseStack.scale(2.5f,2.5f,2.5f);

        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity))), packedLight,OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CodMissileEntity codMissileEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/codmissile.png");
    }
}
