package com.mc3699.codmod.mixin;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.dimension.DimensionKeys;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class PlanetRenderMixins {
    @Shadow
    @Nullable
    private ClientLevel level;

    @Shadow
    protected abstract void createStars();

    @Shadow
    protected abstract void createDarkSky();

    @Shadow
    protected abstract void renderEndSky(PoseStack poseStack);

    @Shadow
    public abstract void renderSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup);

    @Shadow
    protected abstract void renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ);

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void stopRendering(PoseStack poseStack, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (level != null && level.dimension() == DimensionKeys.ENTROPY) {
            ci.cancel();
        }
    }


    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void noSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        if (level != null && level.dimension() == DimensionKeys.ENTROPY) {
            PoseStack poseStack = new PoseStack();
            this.renderEndSky(poseStack);

            poseStack.mulPose(frustumMatrix);
            Tesselator tesselator = Tesselator.getInstance();

            float celestialAngle = level.getTimeOfDay(partialTick) * 360.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(celestialAngle));
            poseStack.mulPose(Axis.YP.rotationDegrees(90));

            float f12 = 500.0F;
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/environment/blue_sun.png"));
            BufferBuilder bufferbuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder1.addVertex(matrix4f, -f12, 500.0F, -f12).setUv(0.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 500.0F, -f12).setUv(1.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 500.0F, f12).setUv(1.0F, 1.0F);
            bufferbuilder1.addVertex(matrix4f, -f12, 500.0F, f12).setUv(0.0F, 1.0F);
            BufferUploader.drawWithShader(bufferbuilder1.buildOrThrow());
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();


            ci.cancel();
        }

        if (level != null && level.dimension() == DimensionKeys.BOREAS) {
            PoseStack poseStack = new PoseStack();

            poseStack.mulPose(frustumMatrix);
            Tesselator tesselator = Tesselator.getInstance();

            float celestialAngle = level.getTimeOfDay(partialTick) * 360.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(celestialAngle));
            poseStack.mulPose(Axis.YP.rotationDegrees(90));

            float f12 = 10.0F;
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.depthMask(false);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/environment/blue_sun.png"));
            BufferBuilder bufferbuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder1.addVertex(matrix4f, -f12, 100.0F, -f12).setUv(0.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 100.0F, -f12).setUv(1.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 100.0F, f12).setUv(1.0F, 1.0F);
            bufferbuilder1.addVertex(matrix4f, -f12, 100.0F, f12).setUv(0.0F, 1.0F);
            BufferUploader.drawWithShader(bufferbuilder1.buildOrThrow());
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();


            ci.cancel();
        }
    }
}
