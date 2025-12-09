package com.mc3699.codmod.handlers.beamStuff;

import com.mc3699.codmod.Codmod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RenderBeamPayload(Vec3 start, Vec3 end, float width, int lifeTime, int color) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "render_beam");
    public static final Type<RenderBeamPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, RenderBeamPayload> CODEC = StreamCodec.of((buf, renderBeamPayload) -> {
        buf.writeVec3(renderBeamPayload.start);
        buf.writeVec3(renderBeamPayload.end);
        buf.writeFloat(renderBeamPayload.width);
        buf.writeInt(renderBeamPayload.lifeTime);
        buf.writeInt(renderBeamPayload.color);
    }, buf -> new RenderBeamPayload(buf.readVec3(), buf.readVec3(), buf.readFloat(), buf.readInt(), buf.readInt()));


    public static void handle(RenderBeamPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            BeamRenderHandler.spawnBeam(payload.start, payload.end, payload.width, payload.lifeTime, payload.color);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
