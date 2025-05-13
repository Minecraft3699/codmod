package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar("1.0");
        registrar.playToClient(
                CameraRotationPayload.TYPE,
                CameraRotationPayload.CODEC,
                CameraRotationPayload::handle
        );
    }

}
