package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.image.ImageDisplayHandler;
import com.mc3699.codmod.client.image.ImageStuff;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public record DisplayImagePayload(byte[] image, int x, int y, int scaleX, int scaleY, int duration) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "display_image");
    public static final Type<DisplayImagePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, DisplayImagePayload> CODEC = StreamCodec.of(
            (buf, pck) -> {
                buf.writeByteArray(pck.image);
                buf.writeInt(pck.x);
                buf.writeInt(pck.y);
                buf.writeInt(pck.scaleX);
                buf.writeInt(pck.scaleY);
                buf.writeInt(pck.duration);
            },
            buf -> new DisplayImagePayload(buf.readByteArray(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt())
    );

    public static void handle(DisplayImagePayload payload, IPayloadContext context)
    {
        context.enqueueWork(() -> {
            try {
                ResourceLocation newImage = ImageStuff.registerImage(payload.image, UUID.randomUUID().toString());
                ImageDisplayHandler.addImage(newImage, payload.x, payload.y,payload.scaleX, payload.scaleY, payload.duration);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
