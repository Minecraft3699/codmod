package com.mc3699.codmod.entity.gianni;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class GianniEntityRenderer extends EntityRenderer<GianniEntity> {
    private final ItemRenderer itemRenderer;
    private final Random random = new Random();

    public GianniEntityRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(GianniEntity gianniEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/item/gianni.png");
    }


    @Override
    public void render(
            GianniEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        poseStack.pushPose();
        float glitchBounds = 0.05f;
        poseStack.translate(
                random.nextFloat(-glitchBounds, glitchBounds),
                1.5,
                0 + random.nextFloat(-glitchBounds, glitchBounds)
        );
        poseStack.translate(0, -0.5f, 0);
        poseStack.scale(6, 6, 6);
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());

        ItemStack stackItem = new ItemStack(CodItems.GIANNI.get());
        BakedModel model = itemRenderer.getModel(stackItem, entity.level(), null, 0);
        itemRenderer.render(
                stackItem,
                ItemDisplayContext.GROUND,
                false,
                poseStack,
                bufferSource,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                model
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
