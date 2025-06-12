package com.mc3699.codmod.entity.friendlyFace;

import com.mc3699.codmod.Codmod;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FriendlyFaceEntityRenderer extends MobRenderer<FriendlyFaceEntity, PlayerModel<FriendlyFaceEntity>> {
    public FriendlyFaceEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0);
    }

    @Override
    public ResourceLocation getTextureLocation(FriendlyFaceEntity friendlyFaceEntity) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID,"textures/entity/friendlyface.png");
    }
}
