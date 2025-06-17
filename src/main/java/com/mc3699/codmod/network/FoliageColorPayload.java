package com.mc3699.codmod.network;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.colors.ColorManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FoliageColorPayload(int color, boolean reset) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "foliage_color");
    public static final Type<FoliageColorPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, FoliageColorPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            FoliageColorPayload::color,
            ByteBufCodecs.BOOL,
            FoliageColorPayload::reset,
            FoliageColorPayload::new
    );


    static void handle(FoliageColorPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (payload.reset()) {
                ColorManager.resetFoliageColor();
                ColorManager.resetGrassColor();
            } else {
                ColorManager.setFoliageColor(payload.color());
                ColorManager.setGrassColor(payload.color());
            }

            Minecraft.getInstance().execute(() -> Minecraft.getInstance().levelRenderer.allChanged());
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
