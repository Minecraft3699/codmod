package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodTurtleUpgrades;
import dan200.computercraft.api.client.turtle.RegisterTurtleModellersEvent;
import dan200.computercraft.api.client.turtle.TurtleUpgradeModeller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Codmod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class TurtleUpgradeRendererRegistration {

    @SubscribeEvent
    public static void registerRenderer(RegisterTurtleModellersEvent event)
    {
        event.register(CodTurtleUpgrades.ENVIRONMENT_SCANNER.get(), TurtleUpgradeModeller.flatItem());
    }

}
