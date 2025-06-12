package com.mc3699.codmod.entity.firelight;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class FireLightEntityRenderer extends LivingEntityRenderer<FirelightEntity, PlayerModel<FirelightEntity>> {
    Random random = new Random();

    public FireLightEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(FirelightEntity darkenerEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/firelight.png");
    }

    @Override
    protected boolean shouldShowName(FirelightEntity entity) {
        return false;
    }

    @Override
    public void render(FirelightEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if(entity.tickCount % 2 == 0)
        {
            poseStack.pushPose();
            float glitchBounds = 0.25f;
            float randX = random.nextFloat(-glitchBounds, glitchBounds);
            float randY = random.nextFloat(-glitchBounds, glitchBounds);
            float randZ = random.nextFloat(-glitchBounds, glitchBounds);
            poseStack.translate(randX,randY,randZ);
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            poseStack.popPose();
        } else {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }

    }
}
