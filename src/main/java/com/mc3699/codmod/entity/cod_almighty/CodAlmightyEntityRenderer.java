package com.mc3699.codmod.entity.cod_almighty;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;



public class CodAlmightyEntityRenderer extends MobRenderer<CodAlmightyEntity, CodAlmightyModel<CodAlmightyEntity>> {


    public CodAlmightyEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CodAlmightyModel<>(context.bakeLayer(CodAlmightyModel.LAYER_LOCATION)), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(CodAlmightyEntity codAlmightyEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/cod_almighty.png");
    }

    @Override
    public void render(CodAlmightyEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(12,12,12);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void setupRotations(CodAlmightyEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {

        //super.setupRotations(entity,poseStack,bob,yBodyRot,partialTick,scale);

        float smoothYaw = Mth.lerp(partialTick, entity.prevAimYaw, entity.aimYaw);
        float smoothPitch = Mth.lerp(partialTick, entity.prevAimPitch, entity.aimPitch);

        poseStack.mulPose(Axis.YP.rotationDegrees(-smoothYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-smoothPitch));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

    }
}
