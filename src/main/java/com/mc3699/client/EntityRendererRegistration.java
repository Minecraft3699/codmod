package com.mc3699.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.entity.EntityRegistration;
import com.mc3699.entity.swarmCod.SwarmCodEntityModel;
import com.mc3699.entity.swarmCod.SwarmCodEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Codmod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EntityRendererRegistration {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(EntityRegistration.SWARM_COD.get(), SwarmCodEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(SwarmCodEntityModel.LAYER_LOCATION, SwarmCodEntityModel::createBodyLayer);
    }

}
