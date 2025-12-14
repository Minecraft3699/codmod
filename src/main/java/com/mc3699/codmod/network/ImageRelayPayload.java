package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record ImageRelayPayload(ImageFileRequestPayload payload, byte[] image) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "relay_image");
    public static final Type<ImageRelayPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ImageRelayPayload> CODEC = StreamCodec.of(

            (buf, p) -> {
                ImageFileRequestPayload.CODEC.encode(buf, p.payload());
                buf.writeByteArray(p.image);
            },

            buf -> {
                ImageFileRequestPayload inner = ImageFileRequestPayload.CODEC.decode(buf);
                return new ImageRelayPayload(inner, buf.readByteArray());
            });

    public static void handle(ImageRelayPayload payload, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer sender = (ServerPlayer) ctx.player();
            var server = sender.server;

            var data = payload.payload();

            for (UUID uuid : data.players()) {
                ServerPlayer target = server.getPlayerList().getPlayer(uuid);
                if (target != null) {
                    PacketDistributor.sendToPlayer(target, new DisplayImagePayload(payload.image, data.x(), data.y(), data.scaleX(), data.scaleY(), data.duration()));
                }
            }
        });
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
