package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.ClientScreenHandler;
import com.mc3699.codmod.item.designator.DesignatorItem;
import com.mc3699.codmod.item.designator.DesignatorScreen;
import com.mc3699.codmod.item.transponder.TransponderIDScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenScreenPayload(String screen) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "open_screen");
    public static final Type<OpenScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenScreenPayload> CODEC = StreamCodec.of(
            (buf, pck) -> buf.writeUtf(pck.screen()),
            buf -> new OpenScreenPayload(buf.readUtf())
    );

    public static void handle(OpenScreenPayload payload, IPayloadContext context) {

        context.enqueueWork(() -> {
            ClientScreenHandler.openScreen(payload.screen());
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
