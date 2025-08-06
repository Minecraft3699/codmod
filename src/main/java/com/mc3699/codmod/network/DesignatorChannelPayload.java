package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.designator.DesignatorItem;
import com.mc3699.codmod.item.transponder.TransponderItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DesignatorChannelPayload(int channel) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "designator_channel");
    public static final Type<DesignatorChannelPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, DesignatorChannelPayload> CODEC = StreamCodec.of(
            (buf, pck) -> buf.writeInt(pck.channel),
            buf -> new DesignatorChannelPayload(buf.readInt())
    );

    public static void handle(DesignatorChannelPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> DesignatorItem.setChannel(context.player().getItemInHand(context.player().getUsedItemHand()), payload.channel));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
