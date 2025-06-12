package com.mc3699.codmod.entity.uav;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.ItemRegistration;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class UAVEntityRenderer extends EntityRenderer<UAVEntity> {
    private final ItemRenderer itemRenderer;
    public UAVEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public boolean shouldRender(UAVEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(UAVEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemStack stackItem = new ItemStack(ItemRegistration.UAV.get());
        poseStack.pushPose();
        poseStack.scale(3, 3, 3);

        poseStack.mulPose(Axis.YP.rotationDegrees((entity.getYRot()*-1)-90));
        BakedModel model = itemRenderer.getModel(stackItem, entity.level(), null, 0);
        itemRenderer.render(stackItem, ItemDisplayContext.GROUND, false, poseStack, bufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(UAVEntity uavEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MODID, "textures/item/uav.png");
    }
}
