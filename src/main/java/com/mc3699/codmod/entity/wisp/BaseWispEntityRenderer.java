package com.mc3699.codmod.entity.wisp;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.WeakHashMap;

public class BaseWispEntityRenderer extends MobRenderer<BaseWispEntity, BaseWispModel> {

    private static Map<BaseWispEntity, PointLight> entityPointLightMap = new WeakHashMap<>();
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/base_wisp.png");

    public BaseWispEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BaseWispModel(context.bakeLayer(BaseWispModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public void render(BaseWispEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {


        PointLight pointLight = entityPointLightMap.computeIfAbsent(entity, e -> {
           PointLight newLight = new PointLight();
            VeilRenderSystem.renderer().getLightRenderer().addLight(newLight);
            return newLight;
        });


        pointLight.setPosition(entity.getX(), entity.getY()+0.8, entity.getZ());
        pointLight.setRadius(10);
        int color = entity.getColor();
        pointLight.setColor(color);
        pointLight.setBrightness(3f);
        pointLight.markDirty();

        poseStack.pushPose();
        poseStack.translate(0,2f,0);
        model.setupAnim(entity, 0, 0, entity.tickCount + partialTicks, entityYaw, 0);
        //model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.BEACON_BEAM.apply(TEXTURE, true)), packedLight, OverlayTexture.NO_OVERLAY);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        RenderSystem.setShaderColor(
                ((color >> 16) & 0xFF) / 255.0F,
                ((color >> 8) & 0xFF) / 255.0F,
                (color & 0xFF) / 255.0F,
                1.0F
        );
        poseStack.popPose();
        RenderSystem.setShaderColor(1,1,1,1);
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
    public ResourceLocation getTextureLocation(BaseWispEntity baseWispEntity) {
        return TEXTURE;
    }
}
