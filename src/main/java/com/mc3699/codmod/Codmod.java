package com.mc3699.codmod;

import com.mc3699.codmod.event.*;
import com.mc3699.codmod.registry.*;
import com.mojang.logging.LogUtils;
import dev.wendigodrip.thebrokenscript.api.queue.WorkQueue;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@Mod(Codmod.MOD_ID)
public class Codmod {
    public static final String MOD_ID = "codmod";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final WorkQueue SERVER_QUEUE = new WorkQueue(SidedThreadGroups.SERVER);

    public Codmod(IEventBus modEventBus, ModContainer modContainer) {
        CodRegistrate.INSTANCE.registerEventListeners(modEventBus);

        CodRegistrate.INSTANCE.event("vay_event", VayEvent::new).register();
        CodRegistrate.INSTANCE.event("cod_event", CodEvent::new).register();
        CodRegistrate.INSTANCE.event("darkener_event", DarkenerEvent::new).register();
        CodRegistrate.INSTANCE.event("firelight_event", FireLightEvent::new).register();
        CodRegistrate.INSTANCE.event("bad_sun", BadSunEvent::new).register();
        CodRegistrate.INSTANCE.chatResponse("vay_response", VayChat::new).register();

        NeoForge.EVENT_BUS.register(this);

        CodEntities.register();
        CodBlocks.register(modEventBus);
        CodItems.register(modEventBus);
        CodBlockEntities.register(modEventBus);
        CodMobEffects.register(modEventBus);
        CodSounds.register();
        CodCreativeTabs.register(modEventBus);
        CodLang.register();
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        SERVER_QUEUE.tick();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info(
                "Starting Codmod, I like cod cod cod cod cod cod cod cod COD MOD IS LOADING!!! THIS IS CODMOD!!! THIS LINE IS COD MOD!!! COD MOD LOADING RIGHT NOW!!!!!!! COD MOD YEAHHHHHHHHHHHHHHHHH");
    }
}
