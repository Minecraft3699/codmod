package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CameraRotationPayload(float yaw, float pitch) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "camera_rotation");
    public static final Type<CameraRotationPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, CameraRotationPayload> CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeFloat(packet.yaw);
                buf.writeFloat(packet.pitch);
            },
            buf -> new CameraRotationPayload(buf.readFloat(), buf.readFloat())
    );

    public static void handle(CameraRotationPayload payload, IPayloadContext context) {
        //context.enqueueWork(() -> ClientCamControl.startRotation(payload.yaw, payload.pitch));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
