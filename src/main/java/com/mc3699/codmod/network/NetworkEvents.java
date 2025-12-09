package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.codCannon.FireRailcannonPayload;
import com.mc3699.codmod.handlers.beamStuff.RenderBeamPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0");
        registrar.playToClient(
                CameraRotationPayload.TYPE,
                CameraRotationPayload.CODEC,
                CameraRotationPayload::handle
        );

        registrar.playToClient(
                FoliageColorPayload.TYPE,
                FoliageColorPayload.CODEC,
                FoliageColorPayload::handle
        );

        registrar.playToClient(
                ClientBadSunSyncPayload.TYPE,
                ClientBadSunSyncPayload.CODEC,
                ClientBadSunSyncPayload::handle
        );

        registrar.playToServer(
                TransponderSetIDPayload.TYPE,
                TransponderSetIDPayload.CODEC,
                TransponderSetIDPayload::handle
        );

        registrar.playToServer(
                DesignatorChannelPayload.TYPE,
                DesignatorChannelPayload.CODEC,
                DesignatorChannelPayload::handle
        );

        registrar.playToClient(
                OpenScreenPayload.TYPE,
                OpenScreenPayload.CODEC,
                OpenScreenPayload::handle
        );

        registrar.playToClient(
                OxygenClientPayload.TYPE,
                OxygenClientPayload.CODEC,
                OxygenClientPayload::handle
        );

        registrar.playToServer(
                FireRailcannonPayload.TYPE,
                FireRailcannonPayload.CODEC,
                FireRailcannonPayload::handle
        );

        registrar.playToClient(
                RenderBeamPayload.TYPE,
                RenderBeamPayload.CODEC,
                RenderBeamPayload::handle
        );

    }
}
