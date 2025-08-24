package com.mc3699.codmod.mixin;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.EntropyEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class EntropyMixin {
    @Shadow
    @Nullable
    private ClientLevel level;

    @Shadow
    public abstract boolean isSectionCompiled(BlockPos pos);

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void stopRendering(PoseStack poseStack, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci)
    {
        if(level != null &&level.dimension() == EntropyEvents.ENTROPY_KEY)
        {
            ci.cancel();
        }
    }

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void noSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci)
    {
        if(level != null && level.dimension() == EntropyEvents.ENTROPY_KEY)
        {
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(frustumMatrix);
            Tesselator tesselator = Tesselator.getInstance();
            float f12 = 80.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/environment/blue_sun.png"));
            BufferBuilder bufferbuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder1.addVertex(matrix4f, -f12, 100.0F, -f12).setUv(0.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 100.0F, -f12).setUv(1.0F, 0.0F);
            bufferbuilder1.addVertex(matrix4f, f12, 100.0F, f12).setUv(1.0F, 1.0F);
            bufferbuilder1.addVertex(matrix4f, -f12, 100.0F, f12).setUv(0.0F, 1.0F);
            BufferUploader.drawWithShader(bufferbuilder1.buildOrThrow());
        }
    }
}
