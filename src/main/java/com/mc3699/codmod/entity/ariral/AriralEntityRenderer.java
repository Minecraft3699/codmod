package com.mc3699.codmod.entity.ariral;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.VeilRenderer;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AriralEntityRenderer extends LivingEntityRenderer<AriralEntity, PlayerModel<AriralEntity>> {
    public AriralEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0);
    }

    @Override
    public void render(
            AriralEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        poseStack.pushPose();
        poseStack.translate(0, 4.5, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.scale(2f, 3f, 2f);

        float limbSwing = entity.walkAnimation.position(partialTicks);
        float limbSwingAmount = entity.walkAnimation.speed(partialTicks);
        VertexConsumer transparentBuffer = buffer.getBuffer(RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(
                Codmod.MOD_ID,
                "textures/entity/ariral.png"
        )));


        RenderSystem.setShaderColor(1,1,1,0.1f);
        model.setupAnim(entity, limbSwing, limbSwingAmount, entity.tickCount, entityYaw, entity.yHeadRot);
        model.renderToBuffer(poseStack, transparentBuffer, packedLight, OverlayTexture.NO_OVERLAY);
        RenderSystem.setShaderColor(1,1,1,1);
        poseStack.popPose();
    }

    @Override
    protected boolean shouldShowName(AriralEntity entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(AriralEntity ariralEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/ariral.png");
    }
}
