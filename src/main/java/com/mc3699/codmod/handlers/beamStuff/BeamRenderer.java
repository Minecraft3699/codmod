package com.mc3699.codmod.handlers.beamStuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BeamRenderer {
    private static final ResourceLocation BEAM_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");

    public static void renderBeam(PoseStack poseStack, MultiBufferSource bufferSource, Vec3 start, Vec3 end, int color, float width, float partialTicks, long gameTime, int age) {
        Vec3 dir = end.subtract(start);
        float length = (float) dir.length();
        dir = dir.normalize();

        Vec3 up = new Vec3(0, 1, 0);
        if (Math.abs(dir.dot(up)) > 0.99) up = new Vec3(1, 0, 0);

        float scaleFactor = (width / 2f) * (1f + age * 0.1f);
        Vec3 right = dir.cross(up).normalize().scale(scaleFactor);
        Vec3 forward = dir.cross(right).normalize().scale(scaleFactor);

        Vec3 p1 = start.add(right).add(forward);
        Vec3 p2 = start.subtract(right).add(forward);
        Vec3 p3 = start.subtract(right).subtract(forward);
        Vec3 p4 = start.add(right).subtract(forward);

        Vec3 p5 = end.add(right).add(forward);
        Vec3 p6 = end.subtract(right).add(forward);
        Vec3 p7 = end.subtract(right).subtract(forward);
        Vec3 p8 = end.add(right).subtract(forward);

        float vOffset = ((gameTime % 10) + partialTicks) * 0.2f;

        PoseStack.Pose pose = poseStack.last();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.beaconBeam(BEAM_TEXTURE, false));

        renderQuad(consumer, pose, color, p1, p2, p6, p5, vOffset, length);
        renderQuad(consumer, pose, color, p2, p3, p7, p6, vOffset, length);
        renderQuad(consumer, pose, color, p3, p4, p8, p7, vOffset, length);
        renderQuad(consumer, pose, color, p4, p1, p5, p8, vOffset, length);
        renderQuad(consumer, pose, color, p5, p6, p7, p8, vOffset, length);
        renderQuad(consumer, pose, color, p4, p3, p2, p1, vOffset, length);
    }

    private static void renderQuad(VertexConsumer consumer, PoseStack.Pose pose, int color, Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, float vOffset, float height) {
        addVertex(consumer, pose, v1, color, 0, 0 + vOffset / height);
        addVertex(consumer, pose, v2, color, 1, 0 + vOffset / height);
        addVertex(consumer, pose, v3, color, 1, 1 + vOffset / height);
        addVertex(consumer, pose, v4, color, 0, 1 + vOffset / height);
    }

    private static void addVertex(VertexConsumer consumer, PoseStack.Pose pose, Vec3 pos, int color, float u, float v) {
        consumer.addVertex(pose, (float) pos.x, (float) pos.y, (float) pos.z).setColor(color).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(pose, 0f, 1f, 0f);
    }
}




