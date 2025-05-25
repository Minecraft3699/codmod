package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.wisp.BaseWispEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT, modid = Codmod.MODID)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTickEnd(ClientTickEvent.Post event)
    {
        BaseWispEntityRenderer.cleanup();
    }

}
