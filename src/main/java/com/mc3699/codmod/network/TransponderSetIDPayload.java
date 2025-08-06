package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.handlers.BadSunClientEvents;
import com.mc3699.codmod.item.transponder.TransponderItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public record TransponderSetIDPayload(String id) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "transponder_set_id");
    public static final CustomPacketPayload.Type<TransponderSetIDPayload> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, TransponderSetIDPayload> CODEC = StreamCodec.of(
            (buf, pck) -> buf.writeUtf(pck.id),
            buf -> new TransponderSetIDPayload(buf.readUtf())
    );

    public static void handle(TransponderSetIDPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> TransponderItem.setID(context.player().getItemInHand(InteractionHand.MAIN_HAND), payload.id));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
