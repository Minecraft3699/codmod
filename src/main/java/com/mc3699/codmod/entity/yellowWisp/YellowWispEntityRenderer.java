package com.mc3699.codmod.entity.yellowWisp;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class YellowWispEntityRenderer extends MobRenderer<YellowWispEntity, YellowWispModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Codmod.MODID, "textures/entity/yellow_wisp.png");

    public YellowWispEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new YellowWispModel(context.bakeLayer(YellowWispModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public void render(YellowWispEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        poseStack.pushPose();
        poseStack.translate(0,0.8f,0);
        model.setupAnim(entity, 0, 0, entity.tickCount + partialTicks, entityYaw, 0);
        model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.BEACON_BEAM.apply(TEXTURE, true)), packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(YellowWispEntity yellowWispEntity) {
        return TEXTURE;
    }
}
