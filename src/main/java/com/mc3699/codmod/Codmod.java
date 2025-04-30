package com.mc3699.codmod;

import com.mc3699.codmod.block.BlockRegistration;
import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.event.CodEvent;
import com.mc3699.codmod.event.VayChat;
import com.mc3699.codmod.event.VayEvent;
import com.mc3699.codmod.item.ItemRegistration;
import com.mojang.logging.LogUtils;
import dev.wendigodrip.thebrokenscript.api.registry.RegistryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Cod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Codmod.MODID)
public class Codmod {
    public static final RegistryWrapper TBSREGISTER = new RegistryWrapper(Codmod.MODID);
    // Define mod id in a common place for everything to reference
    public static final String MODID = "codmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Codmod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Codmod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        EntityRegistration.register(modEventBus);
        BlockRegistration.register(modEventBus);
        ItemRegistration.register(modEventBus);

        TBSREGISTER.event("vay", VayEvent::new);
        TBSREGISTER.event("cod", CodEvent::new);
        TBSREGISTER.chatResponse("vay_response", VayChat::new);
        TBSREGISTER.setup(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
