package com.mc3699.codmod.item.detector;

import com.mc3699.codmod.registry.CodItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetectorItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final DetectorItemRenderer INSTANCE = new DetectorItemRenderer();

    public DetectorItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }


    @Override
    public void renderByItem(
            ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight, int packedOverlay
    ) {
        poseStack.pushPose();


        if (displayContext == ItemDisplayContext.GUI) {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.scale(0.8f, 0.8f, 0.8f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        } else if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            poseStack.translate(0.325, 0.27, 0);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        } else if (displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            poseStack.translate(0.5, 0.1, 0.5);
            poseStack.mulPose(new Matrix4f().rotate((float) Math.toRadians(180), 0, 1, 0));
            poseStack.scale(2, 2, 2);
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.renderStatic(
                new ItemStack(CodItems.BROKEN_DETECTOR.get(), 1), displayContext, packedLight, packedOverlay,
                poseStack, buffer, null, 0
        );

        poseStack.translate(-0.7, 0.92, 0.075);
        poseStack.scale(0.005f, -0.005f, 0.005f);
        poseStack.translate(0, 20, 0);
        Font font = Minecraft.getInstance().font;
        Matrix4f matrix = poseStack.last().pose();

        Vec3 pos = Minecraft.getInstance().player.getPosition(0);

        font.drawInBatch(
                "X:" + ((int) Math.ceil(pos.x)), 60, 0, 0xFF0000, false, matrix, buffer,
                Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT
        );
        font.drawInBatch(
                "Z:" + ((int) Math.ceil(pos.z)), 60, 10, 0x00FF00, false, matrix, buffer,
                Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT
        );
        poseStack.popPose();
    }

}