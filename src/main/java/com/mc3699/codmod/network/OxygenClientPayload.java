package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.ClientOxygen;
import com.mc3699.codmod.client.ClientScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OxygenClientPayload(int oxygenSaturation) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "oxygen_info");
    public static final Type<OxygenClientPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OxygenClientPayload> CODEC = StreamCodec.of(
            (buf, pck) ->
            {
                buf.writeInt(pck.oxygenSaturation);
            },
            buf -> new OxygenClientPayload(buf.readInt())
    );

    public static void handle(OxygenClientPayload payload, IPayloadContext context) {

        context.enqueueWork(() -> {
            ClientOxygen.oxygenSaturation = payload.oxygenSaturation;
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
