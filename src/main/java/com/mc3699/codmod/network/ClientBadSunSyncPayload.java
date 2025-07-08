package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.handlers.BadSunClientEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientBadSunSyncPayload(boolean isBadSun) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "client_badsun_sync");
    public static final CustomPacketPayload.Type<ClientBadSunSyncPayload> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, ClientBadSunSyncPayload> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeBoolean(packet.isBadSun),
            buf -> new ClientBadSunSyncPayload(buf.readBoolean())
    );

    public static void handle(ClientBadSunSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> BadSunClientEvents.setBadSunDay(payload.isBadSun()));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
