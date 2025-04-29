package com.mc3699.entity.swarmCod;

import com.mc3699.codmod.Codmod;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SwarmCodEntityRenderer extends MobRenderer<SwarmCodEntity, SwarmCodEntityModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Codmod.MODID, "textures/entity/swarm_cod.png");

    public SwarmCodEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SwarmCodEntityModel(context.bakeLayer(SwarmCodEntityModel.LAYER_LOCATION)), 0.01f);
    }

    @Override
    public ResourceLocation getTextureLocation(SwarmCodEntity swarmCodEntity) {
        return TEXTURE;
    }

}
