package com.mc3699.codmod.entity.vay;

import com.mc3699.codmod.Codmod;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class VayEntityRenderer extends LivingEntityRenderer<VayEntity, PlayerModel<VayEntity>> {
    public static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/vay.png");

    public VayEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(VayEntity vayEntity) {
        return TEXTURE;
    }
}
