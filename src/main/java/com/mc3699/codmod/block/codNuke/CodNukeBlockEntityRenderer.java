package com.mc3699.codmod.block.codNuke;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import java.awt.*;


public class CodNukeBlockEntityRenderer implements BlockEntityRenderer<CodNukeBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public CodNukeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }


    @Override
    public void render(CodNukeBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        Font font = Minecraft.getInstance().font;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90));
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.translate(0.35,-0.425,-0.01);
        float tScale = 0.02f;
        poseStack.scale(tScale,tScale,tScale);


        int remaining = blockEntity.getRemaining();
        String time = String.format("%02d:%02d", remaining / 60, remaining % 60);
        int textWidth = Minecraft.getInstance().font.width(time);

        font.drawInBatch(time, (float) -textWidth /2,0, 0xFF0000, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, 0xFF00FF);
        poseStack.popPose();
    }
}
