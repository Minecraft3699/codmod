package com.mc3699.codmod.entity.trialTrader;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class TrialTraderEntityRenderer extends LivingEntityRenderer<TrialTraderEntity, PlayerModel<TrialTraderEntity>> {
    public TrialTraderEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(TrialTraderEntity trialTraderEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/trial_trader_passive.png");
    }

    @Override
    public void render(TrialTraderEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected boolean shouldShowName(TrialTraderEntity entity) {
        return false;
    }
}
