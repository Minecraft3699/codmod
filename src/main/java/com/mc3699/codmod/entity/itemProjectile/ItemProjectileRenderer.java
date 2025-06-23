package com.mc3699.codmod.entity.itemProjectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class ItemProjectileRenderer extends EntityRenderer<ItemProjectileEntity> {
    private ItemRenderer itemRenderer;

    public ItemProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        Vec3 velocity = entity.getDeltaMovement();
        float yaw = (float) (Mth.atan2(velocity.x, velocity.z) * (180.0 / Math.PI));
        float pitch = (float) (Mth.atan2(velocity.y, velocity.horizontalDistance()) * (180.0 / Math.PI));
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
        poseStack.scale(1f, 1f, 1f);
        poseStack.translate(0.0, 0.2, 0.0);
        ItemStack item = entity.getCarriedItem().isEmpty() ? new ItemStack(Items.COD) : entity.getCarriedItem();
        itemRenderer.renderStatic(item, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ItemProjectileEntity itemProjectileEntity) {
        return null;
    }
}
