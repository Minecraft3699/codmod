package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public record ImageFileRequestPayload(List<UUID> players, String filePath, int x, int y, int scaleX, int scaleY, int duration) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "request_image");
    public static final CustomPacketPayload.Type<ImageFileRequestPayload> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ImageFileRequestPayload> CODEC = StreamCodec.of(
            (buf, pck) -> {

                buf.writeCollection(pck.players, (b, uuid) -> {
                    b.writeUUID(uuid);
                });
                buf.writeUtf(pck.filePath);
                buf.writeInt(pck.x);
                buf.writeInt(pck.y);
                buf.writeInt(pck.scaleX);
                buf.writeInt(pck.scaleY);
                buf.writeInt(pck.duration);
            },
            buf -> {
                List<UUID> players = buf.readList(b -> b.readUUID());
                return new ImageFileRequestPayload(players, buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
            }
    );

    public static void handle(ImageFileRequestPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            File file = new File(payload.filePath);

            if (!file.exists() || !file.isFile()) {
                return;
            }

            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                PacketDistributor.sendToServer(new ImageRelayPayload(payload, bytes));
            } catch (IOException e) {
                // idfc if you fail
            }


        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
