package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityModel;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityRenderer;
import com.mc3699.codmod.entity.vay.VayEntityRenderer;
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
        event.registerEntityRenderer(EntityRegistration.VAY.get(), VayEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(SwarmCodEntityModel.LAYER_LOCATION, SwarmCodEntityModel::createBodyLayer);
    }

}
