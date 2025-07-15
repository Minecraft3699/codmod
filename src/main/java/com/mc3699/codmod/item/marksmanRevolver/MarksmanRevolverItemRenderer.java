package com.mc3699.codmod.item.marksmanRevolver;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MarksmanRevolverItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final MarksmanRevolverItemRenderer INSTANCE = new MarksmanRevolverItemRenderer();

    public MarksmanRevolverItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer renderer = minecraft.getItemRenderer();

        poseStack.pushPose();


        if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || displayContext == ItemDisplayContext.GROUND)
        {
            renderHand(poseStack, renderer, packedLight, buffer, true, true);
        }

        if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
        {
            render3P(poseStack, renderer, packedLight, buffer);
        }

        poseStack.popPose();
    }

    private void renderHand(PoseStack poseStack, ItemRenderer renderer, int packedLight, MultiBufferSource buffer, boolean topCod, boolean bottomCod)
    {
        poseStack.scale(2,2,2);
        poseStack.translate(0.35,0.24,0);
        poseStack.mulPose(Axis.XP.rotationDegrees(285));
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        poseStack.mulPose(Axis.ZP.rotationDegrees(280));
        renderer.renderStatic(
                new ItemStack(CodItems.MARKSMAN_REVOLVER_MODEL.get()),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                null,
                0
        );


        poseStack.scale(0.15f,0.145f,0.145f);
        poseStack.translate(0.6,-0.9,1.7);
        poseStack.mulPose(Axis.YP.rotationDegrees(43));
        poseStack.mulPose(Axis.ZN.rotationDegrees(-35));


        if(topCod)
        {
            renderer.renderStatic(
                    new ItemStack(Items.COD),
                    ItemDisplayContext.GROUND,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    null,
                    0
            );
        }



        poseStack.translate(0.2,-0.2,0);

        if(bottomCod)
        {
            renderer.renderStatic(
                    new ItemStack(Items.COD),
                    ItemDisplayContext.GROUND,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    null,
                    0
            );
        }

    }

    private void render3P(PoseStack poseStack, ItemRenderer renderer, int packedLight, MultiBufferSource buffer)
    {
        poseStack.scale(1.5f,1.5f,1.5f);
        poseStack.translate(0.4,0.3,0.3);
        poseStack.mulPose(Axis.XP.rotationDegrees(285));
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        poseStack.mulPose(Axis.ZP.rotationDegrees(280));
        renderer.renderStatic(
                new ItemStack(CodItems.MARKSMAN_REVOLVER_MODEL.get()),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                null,
                0
        );
    }
}
