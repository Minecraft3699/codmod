package com.mc3699.codmod.entity.parachuteChest;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ParachuteChestEntityRenderer extends EntityRenderer<ParachuteChestEntity> {

    private final ParachuteChestEntityModel model;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Codmod.MODID, "textures/entity/parachute_chest.png");

    public ParachuteChestEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ParachuteChestEntityModel(context.bakeLayer(ParachuteChestEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(ParachuteChestEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getTextureLocation(ParachuteChestEntity parachuteChestEntity) {
        return TEXTURE;
    }
}
