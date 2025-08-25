package com.mc3699.codmod.block.oxygenDistributor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.codehaus.plexus.util.dag.Vertex;
import org.joml.Matrix4f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class OxygenDistributorBlockEntityRenderer implements BlockEntityRenderer<OxygenDistributorBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public OxygenDistributorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }


    @Override
    public void render(OxygenDistributorBlockEntity distributorBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.debugLineStrip(1));
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        RenderSystem.disableCull();
        int range = distributorBlockEntity.shouldRender() ? distributorBlockEntity.getEffectiveArea(): 0;
        poseStack.scale(range,range,range);
        drawSphere(vertexConsumer, poseStack, i, 0,255,255,20);
        poseStack.popPose();
        RenderSystem.enableCull();
    }

    @Override
    public boolean shouldRenderOffScreen(OxygenDistributorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(OxygenDistributorBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 500;
    }

    public static void addVertex(VertexConsumer builder, PoseStack poseStack, int combinedLight, float x, float y, float z, float nx, float ny, float nz, int red, int green, int blue, int alpha)
    {
        Matrix4f matrix = poseStack.last().pose();
        builder.addVertex(matrix, x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(0.0f, 0.0f)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(combinedLight, combinedLight)
                .setNormal(nx, ny, nz);
    }

    public static void drawSphere(VertexConsumer vertexConsumer, PoseStack poseStack, int combinedLight, int red, int green, int blue, int alpha) {
        int numStacks = 10;
        int numSectors = 20;
        float pi = (float)Math.PI;
        float twoPi = 2 * pi;

        for(int i = 0; i < numStacks; i++) {
            float phi_i = (i / (float)numStacks) * pi;
            float phi_i1 = ((i+1) / (float)numStacks) * pi;
            for(int j = 0; j < numSectors; j++) {
                float theta_j = (j / (float)numSectors) * twoPi;
                float theta_j1 = ((j+1) % numSectors / (float)numSectors) * twoPi;

                float x_A = (float) (sin(phi_i) * cos(theta_j));
                float y_A = (float) (sin(phi_i) * sin(theta_j));
                float z_A = (float) (cos(phi_i));
                float nz_A = (float) cos(phi_i);

                float x_B = (float) (sin(phi_i1) * cos(theta_j));
                float y_B = (float) (sin(phi_i1) * sin(theta_j));
                float z_B = (float) (cos(phi_i1));
                float nz_B = (float) cos(phi_i1);

                float x_C = (float) (sin(phi_i1) * cos(theta_j1));
                float y_C = (float) (sin(phi_i1) * sin(theta_j1));
                float z_C = (float) (cos(phi_i1));
                float nz_C = (float) cos(phi_i1);

                float x_D = (float) (sin(phi_i) * cos(theta_j1));
                float y_D = (float) (sin(phi_i) * sin(theta_j1));
                float z_D = (float) (cos(phi_i));
                float nz_D = (float) cos(phi_i);

                addVertex(vertexConsumer, poseStack, combinedLight, x_A, y_A, z_A, x_A, y_A, nz_A, red, green, blue, alpha);
                addVertex(vertexConsumer, poseStack, combinedLight, x_B, y_B, z_B, x_B, y_B, nz_B, red, green, blue, alpha);
                addVertex(vertexConsumer, poseStack, combinedLight, x_C, y_C, z_C, x_C, y_C, nz_C, red, green, blue, alpha);
                addVertex(vertexConsumer, poseStack, combinedLight, x_D, y_D, z_D, x_D, y_D, nz_D, red, green, blue, alpha);
            }
        }
    }

    @Override
    public AABB getRenderBoundingBox(OxygenDistributorBlockEntity blockEntity) {
        return AABB.ofSize(blockEntity.getBlockPos().getCenter(), 500,500,500);
    }
}
