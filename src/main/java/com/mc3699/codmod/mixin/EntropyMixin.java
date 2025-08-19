package com.mc3699.codmod.mixin;

import com.mc3699.codmod.client.EntropyEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class EntropyMixin {
    @Shadow
    @Nullable
    private ClientLevel level;

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
            ci.cancel();
        }
    }
}
