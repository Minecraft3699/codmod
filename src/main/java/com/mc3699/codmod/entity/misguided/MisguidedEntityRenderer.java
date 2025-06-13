package com.mc3699.codmod.entity.misguided;

import com.mc3699.codmod.Codmod;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MisguidedEntityRenderer extends MobRenderer<MisguidedEntity, PlayerModel<MisguidedEntity>> {
    public MisguidedEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(MisguidedEntity misguidedEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/entity/misguided.png");
    }
}