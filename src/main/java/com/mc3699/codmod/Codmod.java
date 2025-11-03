package com.mc3699.codmod;

import com.mc3699.codmod.commands.ColorCommand;
import com.mc3699.codmod.commands.StartBadSunCommand;
import com.mc3699.codmod.commands.StopBadSunCommand;
import com.mc3699.codmod.commands.TempBanCommand;
import com.mc3699.codmod.data.CodData;
import com.mc3699.codmod.event.*;
import com.mc3699.codmod.network.APIServer;
import com.mc3699.codmod.registry.*;
import com.mc3699.codmod.responses.VayChat;
import com.mojang.logging.LogUtils;
import dev.wendigodrip.thebrokenscript.api.queue.WorkQueue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mc3699.codmod.network.CodVariables.ATTACHMENT_TYPES;

@Mod(Codmod.MOD_ID)
public class Codmod {
    public static final String MOD_ID = "codmod";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final WorkQueue SERVER_QUEUE = new WorkQueue();

    public Codmod(IEventBus modEventBus, ModContainer modContainer) {

        CodEntities.register();
        CodBlocks.register(modEventBus);
        CodItems.register(modEventBus);
        CodBlockEntities.register(modEventBus);
        CodMobEffects.register(modEventBus);
        CodSounds.register();
        CodCreativeTabs.register(modEventBus);
        CodLang.register();
        CodPeripheralUpgradeTypes.register(modEventBus);
        CodGenerators.register(modEventBus);
        CodTurtleUpgrades.register(modEventBus);
        CodRecipeSerializers.register(modEventBus);
        CodFluidTypes.register(modEventBus);
        CodFluids.register(modEventBus);
        CodParticles.register(modEventBus);

        CodComponents.register(modEventBus);
        CodRegistrate.INSTANCE.registerEventListeners(modEventBus);
        CodRegistrate.INSTANCE.event("vay_event", VayEvent::new).register();
        CodRegistrate.INSTANCE.event("cod_event", CodEvent::new).register();
        CodRegistrate.INSTANCE.event("darkener_event", DarkenerEvent::new).register();
        CodRegistrate.INSTANCE.event("firelight_event", FireLightEvent::new).register();
        CodRegistrate.INSTANCE.event("bad_sun", BadSunEvent::new).register();
        CodRegistrate.INSTANCE.event("john_geometry", JohnGeometryEvent::new).register();
        CodRegistrate.INSTANCE.event("salmon_event", SalmonEvent::new).register();
        CodRegistrate.INSTANCE.chatResponse("vay_response", VayChat::new).register();

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::registerNetworking);
        ATTACHMENT_TYPES.register(modEventBus);


    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        SERVER_QUEUE.tick();
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        ColorCommand.register(event.getDispatcher());
        TempBanCommand.register(event.getDispatcher());
        StopBadSunCommand.register(event.getDispatcher());
        StartBadSunCommand.register(event.getDispatcher());
    }

    APIServer banAPIServer = new APIServer();

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info(
                "Starting Codmod, I like cod cod cod cod cod cod cod cod COD MOD IS LOADING!!! THIS IS CODMOD!!! THIS LINE IS COD MOD!!! COD MOD LOADING RIGHT NOW!!!!!!! COD MOD YEAHHHHHHHHHHHHHHHHH"
        );

        LOGGER.info("Starting API...");

        try {
            banAPIServer.startServer(event.getServer());
        } catch (IOException e) {
            LOGGER.info("Failed to API");
        }
    }

    public static boolean hasOperatorPermission(Player player) { //This can be used to check if a player has perms to use items and should be added to all staff items like im about to do to the codrod
        if (player instanceof FakePlayer) {
            return true;
        }
        return player.hasPermissions(2);
    }

    @SubscribeEvent
    public void onServerStop(ServerStoppingEvent event)
    {
        banAPIServer.stopServer();
    }

    @SubscribeEvent
    public void onLoadLevel(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        level.getServer().overworld().getDataStorage().computeIfAbsent(CodData.FACTORY, "cod");
    }
    private static boolean networkingRegistered = false;
    private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

    private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) { }

    public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
        if (networkingRegistered)
            throw new IllegalStateException("Cannot register new network messages after networking has been registered");
        MESSAGES.put(id, new NetworkMessage<>(reader, handler));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
        networkingRegistered = true;
    }
}
