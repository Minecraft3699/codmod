package com.mc3699.codmod.item.codCannon;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.MarksmanRevolverCoinEntity;
import com.mc3699.codmod.handlers.beamStuff.RenderBeamPayload;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class FireRailcannonPayload implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, FireRailcannonPayload> CODEC = StreamCodec.of((buf, payload) -> {
    }, buf -> new FireRailcannonPayload());
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "fire_railcannon");
    public static final Type<FireRailcannonPayload> TYPE = new Type<>(ID);

    public static void handle(FireRailcannonPayload payload, IPayloadContext context) {

        context.enqueueWork(() -> {

            if (context.player() instanceof ServerPlayer serverPlayer) {
                ServerLevel serverLevel = serverPlayer.serverLevel();

                ItemStack held = serverPlayer.getMainHandItem();
                if (!(held.getItem() instanceof MaliciousCodCannonItem cannon)) return;


                double maxDist = 128;
                Vec3 start = serverPlayer.getEyePosition().add(0, -0.25, 0);
                Vec3 end = start.add(serverPlayer.getLookAngle().scale(maxDist));


                ClipContext clip = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, serverPlayer);

                EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(serverLevel, serverPlayer, start, end, serverPlayer.getBoundingBox().expandTowards(serverPlayer.getLookAngle().scale(maxDist)).inflate(1.0), e -> e instanceof MarksmanRevolverCoinEntity || (e instanceof LivingEntity livingEntity && !livingEntity.isDeadOrDying()) && e != serverPlayer);


                HitResult hit = serverLevel.clip(clip);

                Vec3 hitPos = hit.getType() == HitResult.Type.MISS ? end : hit.getLocation();
                if (entityHit != null) {
                    if (entityHit.getEntity() instanceof MarksmanRevolverCoinEntity coinEntity) {
                        coinEntity.hit(serverPlayer);
                    }
                    entityHit.getEntity().hurt(serverLevel.damageSources().lightningBolt(), 100f);
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer, new RenderBeamPayload(start, entityHit.getLocation().add(0, entityHit.getEntity().getEyeHeight(), 0), 0.02f, 60, 0xFF0000));
                } else {
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer, new RenderBeamPayload(start, hitPos,0.02f, 60, 0xFF0000));

                }


            }

        });

    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
