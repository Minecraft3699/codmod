package com.mc3699.codmod.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import com.mc3699.codmod.Codmod;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CodVariables {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Codmod.MOD_ID);
    public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        Codmod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
    }

    @EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player)
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
            PlayerVariables clone = new PlayerVariables();
            if (!event.isWasDeath()) {
                clone.hookx = original.hookx;
                clone.hooky = original.hooky;
                clone.hookz = original.hookz;
                clone.isGrappling = original.isGrappling;
                clone.grabbed = original.grabbed;
                clone.grabx = original.grabx;
                clone.graby = original.graby;
                clone.grabz = original.grabz;
                clone.godhasleftus = original.godhasleftus;
                clone.whereisgod = original.whereisgod;
            }
            event.getEntity().setData(PLAYER_VARIABLES, clone);
        }
    }

    public static class PlayerVariables implements INBTSerializable<CompoundTag> {
        public double hookx = 0;
        public double hooky = 0;
        public double hookz = 0;
        public boolean isGrappling = false;
        public boolean grabbed = false;
        public double grabx = 0;
        public double graby = 0;
        public double grabz = 0;
        public String godhasleftus = "\"\"";
        public String whereisgod = "\"\"";

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("hookx", hookx);
            nbt.putDouble("hooky", hooky);
            nbt.putDouble("hookz", hookz);
            nbt.putBoolean("isGrappling", isGrappling);
            nbt.putBoolean("grabbed", grabbed);
            nbt.putDouble("grabx", grabx);
            nbt.putDouble("graby", graby);
            nbt.putDouble("grabz", grabz);
            nbt.putString("godhasleftus", godhasleftus);
            nbt.putString("whereisgod", whereisgod);
            return nbt;
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
            hookx = nbt.getDouble("hookx");
            hooky = nbt.getDouble("hooky");
            hookz = nbt.getDouble("hookz");
            isGrappling = nbt.getBoolean("isGrappling");
            grabbed = nbt.getBoolean("grabbed");
            grabx = nbt.getDouble("grabx");
            graby = nbt.getDouble("graby");
            grabz = nbt.getDouble("grabz");
            godhasleftus = nbt.getString("godhasleftus");
            whereisgod = nbt.getString("whereisgod");
        }

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
        }
    }

    public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
        public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "player_variables_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
                .of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
                    PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
                    message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
                    return message;
                });

        @Override
        public Type<PlayerVariablesSyncMessage> type() {
            return TYPE;
        }

        public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
            if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
                context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
                    context.connection().disconnect(Component.literal(e.getMessage()));
                    return null;
                });
            }
        }
    }
}