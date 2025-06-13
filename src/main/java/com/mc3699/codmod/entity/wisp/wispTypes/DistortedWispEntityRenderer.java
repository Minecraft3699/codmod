package com.mc3699.codmod.entity.wisp.wispTypes;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.wisp.BaseWispEntity;
import com.mc3699.codmod.entity.wisp.BaseWispModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.WeakHashMap;

public class DistortedWispEntityRenderer extends MobRenderer<BaseWispEntity, BaseWispModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            Codmod.MOD_ID,
            "textures/entity/base_wisp.png"
    );
    private static final Map<BaseWispEntity, PointLight> entityPointLightMap = new WeakHashMap<>();

    public DistortedWispEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BaseWispModel(context.bakeLayer(BaseWispModel.LAYER_LOCATION)), 0.0f);
    }

    public static void cleanup() {
        entityPointLightMap.entrySet().removeIf(entry -> {
            if (entry.getKey().isRemoved()) {
                VeilRenderSystem.renderer().getLightRenderer().removeLight(entry.getValue());
                return true;
            }
            return false;
        });
    }

    @Override
    public void render(
            BaseWispEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {

        VertexConsumer consumer = buffer.getBuffer(RenderType.END_PORTAL);
        poseStack.pushPose();
        poseStack.translate(0, 2f, 0); // Same offset as before


        model.renderToBuffer(poseStack, consumer, 0x00FF00, 0x00FF00);
        model.setupAnim(entity, 0, 0, entity.tickCount, 0, 0);

        //poseStack.scale(1, 1, 1); // 1x1 quad
        //var matrix = poseStack.last().pose();
        //consumer.addVertex(matrix, -0.5f, 0.5f, 0).setColor(1f, 1f, 1f, 0.5f).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        //consumer.addVertex(matrix, 0.5f, 0.5f, 0).setColor(1f, 1f, 1f, 0.5f).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        //consumer.addVertex(matrix, 0.5f, -0.5f, 0).setColor(1f, 1f, 1f, 0.5f).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);
        //consumer.addVertex(matrix, -0.5f, -0.5f, 0).setColor(1f, 1f, 1f, 0.5f).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 0, 1);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(BaseWispEntity baseWispEntity) {
        return TEXTURE;
    }
}