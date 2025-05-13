package com.mc3699.codmod.block.mantleKey;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Properties;
import java.util.Random;

public class MantleKeyBlockEntityRender implements BlockEntityRenderer<MantleKeyBlockEntity> {

    private final Random random = new Random();
    private final BlockEntityRendererProvider.Context context;

    public MantleKeyBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MantleKeyBlockEntity mantleKeyBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();

        Direction facing = mantleKeyBlockEntity.getBlockState().getValue(MantleKeyBlock.FACING);

        if (facing == Direction.NORTH) {
            poseStack.mulPose(Axis.YP.rotationDegrees(-90));
            poseStack.translate(0, 0, -1);
        }

        if(facing == Direction.SOUTH)
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
        }

        if(facing == Direction.EAST)
        {
            poseStack.translate(1,0,1);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        }


        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        int beamLen = mantleKeyBlockEntity.getBeamLength();
        renderBeam(poseStack, multiBufferSource, v, mantleKeyBlockEntity.getLevel().getGameTime(), beamLen);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(MantleKeyBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(MantleKeyBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(MantleKeyBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(
                pos.getX(), pos.getY(), pos.getZ(),
                pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f
        ).inflate(1000);
    }

    private void renderBeam(PoseStack poseStack, MultiBufferSource buffer, float tickDelta, long worldTime, int beamLength) {



        /*

        float time = worldTime + tickDelta * 3;
        float red = (float) Math.sin(time * 0.05) * 0.5f + 0.5f;
        float green = (float) Math.sin(time * 0.05 + 2.094f) * 0.5f + 0.5f;
        float blue = (float) Math.sin(time * 0.05 + 4.188f) * 0.5f + 0.5f;

        int r = (int) (red * 255);
        int g = (int) (green * 255);
        int b = (int) (blue * 255);
        int color = (r << 16) | (g << 8) | b;

         */

        BeaconRenderer.renderBeaconBeam(
                poseStack,
                buffer,
                BeaconRenderer.BEAM_LOCATION,
                tickDelta,
                1.0F,
                worldTime,
                0,
                beamLength,
                0x00FFFF,
                0.2F,
                0.25F
        );
    }
}
