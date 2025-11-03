package com.mc3699.codmod.block.graphicsMonitor;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class GraphicsMonitorBlockEntityRenderer implements BlockEntityRenderer<GraphicsMonitorBlockEntity> {
    private final DynamicTexture texture;
    private final ResourceLocation textureLocation;
    private final int fullbright = 0xF000F0;


    public GraphicsMonitorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        texture = new DynamicTexture(new NativeImage(1024, 1024, true));
        textureLocation = Minecraft.getInstance().getTextureManager()
                .register("graphics_monitor", texture);
    }

    @Override
    public void render(GraphicsMonitorBlockEntity be, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {

        if (be.isDirty()) {
            NativeImage src = be.getActiveFramebuffer();
            NativeImage dst = texture.getPixels();

            dst.copyFrom(src);
            texture.upload();

            be.clearDirty();
        }

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, -0.001);

        RenderType renderType = RenderType.entityCutout(textureLocation);
        VertexConsumer vc = buffer.getBuffer(renderType);
        Matrix4f matrix = poseStack.last().pose();

        vc.addVertex(matrix, -0.5f,  0.5f, 0)
                .setUv(0, 0).setUv1(fullbright,fullbright)
                .setColor(1, 1, 1, 1)
                .setLight(light).setNormal(0, 0, 1);

        vc.addVertex(matrix,  0.5f,  0.5f, 0)
                .setUv(1, 0).setUv1(fullbright,fullbright)
                .setColor(1, 1, 1, 1)
                .setLight(light).setNormal(0, 0, 1);

        vc.addVertex(matrix,  0.5f, -0.5f, 0)
                .setUv(1, 1).setUv1(fullbright,fullbright)
                .setColor(1, 1, 1, 1)
                .setLight(light).setNormal(0, 0, 1);

        vc.addVertex(matrix, -0.5f, -0.5f, 0)
                .setUv(0, 1).setUv1(fullbright,fullbright)
                .setColor(1, 1, 1, 1)
                .setLight(light).setNormal(0, 0, 1);

        poseStack.popPose();
    }
}
