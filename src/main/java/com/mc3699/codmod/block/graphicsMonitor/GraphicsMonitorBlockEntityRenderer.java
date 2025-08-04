package com.mc3699.codmod.block.graphicsMonitor;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.awt.image.BufferedImage;

public class GraphicsMonitorBlockEntityRenderer implements BlockEntityRenderer<GraphicsMonitorBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public GraphicsMonitorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(GraphicsMonitorBlockEntity graphicsMonitorBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.translate(0.5,0.5,-0.001);

        BufferedImage frameBuffer = graphicsMonitorBlockEntity.getActiveFramebuffer();
        DynamicTexture texture = new DynamicTexture(new NativeImage(1024,1024,true));
        NativeImage nativeImage = texture.getPixels();

        for (int x = 0; x < 1024; x++) {
            for (int y = 0; y < 1024; y++) {
                nativeImage.setPixelRGBA(x, y, frameBuffer.getRGB(x, y));
            }
        }

        texture.upload();

        ResourceLocation textureLocation = Minecraft.getInstance().getTextureManager().register("graphics_monitor", texture);

        RenderType renderType = RenderType.entityCutout(textureLocation);
        VertexConsumer consumer = multiBufferSource.getBuffer(renderType);
        Matrix4f matrix = poseStack.last().pose();

        consumer.addVertex(matrix, -0.5f, 0.5f, 0).setColor(1,1,1,1).setUv1(0,0).setUv(0,0).setUv2(i, i1).setNormal(0,0,1);
        consumer.addVertex(matrix, 0.5f, 0.5f, 0).setColor(1,1,1,1).setUv1(0,0).setUv(0,0).setUv2(i, i1).setNormal(0,0,1);
        consumer.addVertex(matrix, 0.5f, -0.5f, 0).setColor(1,1,1,1).setUv1(0,0).setUv(0,0).setUv2(i, i1).setNormal(0,0,1);
        consumer.addVertex(matrix, -0.5f, -0.5f, 0).setColor(1,1,1,1).setUv1(0,0).setUv(0,0).setUv2(i, i1).setNormal(0,0,1);

        poseStack.popPose();
        texture.close();
    }
}
