package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockEntityRegistration;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntityRender;
import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityModel;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityRenderer;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntityRenderer;
import com.mc3699.codmod.entity.vay.VayEntityRenderer;
import com.mc3699.codmod.entity.yellowWisp.YellowWispEntityRenderer;
import com.mc3699.codmod.entity.yellowWisp.YellowWispModel;
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
        event.registerEntityRenderer(EntityRegistration.TRIAL_TRADER.get(), TrialTraderEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.YELLOW_WISP.get(), YellowWispEntityRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityRegistration.MANTLE_KEY.get(), MantleKeyBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(SwarmCodEntityModel.LAYER_LOCATION, SwarmCodEntityModel::createBodyLayer);
        event.registerLayerDefinition(YellowWispModel.LAYER_LOCATION, YellowWispModel::createBodyLayer);
    }

}
