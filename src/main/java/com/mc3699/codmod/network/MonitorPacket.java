package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.nio.charset.StandardCharsets;

public record MonitorPacket(String username, int[] pixelData, int width, int height) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "client_monitor");
    public static final Type<MonitorPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MonitorPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeUtf(packet.username);
                buf.writeVarIntArray(packet.pixelData);
                buf.writeInt(packet.width);
                buf.writeInt(packet.height);
            },
            buf -> new MonitorPacket(buf.readUtf(), buf.readVarIntArray(), buf.readInt(), buf.readInt())
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    static void handle(MonitorPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> MonitorSystem.storeScreenshot(packet.username, packet.pixelData, packet.width, packet.height));
    }
}
