package com.mc3699.codmod.item.marksmanRevolver;

import com.mc3699.codmod.entity.MarksmanRevolverCoinEntity;
import com.mc3699.codmod.handlers.CodScheduler;
import com.mc3699.codmod.handlers.beamStuff.RenderBeamPayload;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class MarksmanRevolverItem extends SwordItem {

    public MarksmanRevolverItem(Properties properties) {
        super(Tiers.WOOD, properties);
    }

    //TODO FROM EYAE:PLEASE BALANCE THIS! ADD A COOLDOWN OR SM SHIT IDFK
    //TODO FROM MC3699: PLEASE DONT PUT TODOs ON ADMIN ONLY ITEMS THAT DO NOT NEED THEM
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            fireRaycast(serverLevel, player);
            serverLevel.playSound(null, player.blockPosition(), CodSounds.SUPRESSED_GUNSHOT.value(), SoundSource.MASTER, 0.5f, 1.5f);
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        Vec3 look = player.getLookAngle().scale(2);
        Vec3 coinSpawnPos = new Vec3(look.x, -0.5, look.z);

        if (player.level() instanceof ServerLevel serverLevel) {
            // multi-fire logic soon(tm)
            for(int i = 0; i < 1; i++) {
                CodScheduler.schedule(i, () -> {
                    MarksmanRevolverCoinEntity coinEntity = new MarksmanRevolverCoinEntity(CodEntities.MARKSMAN_COIN_ENTITY.get(), serverLevel);
                    coinEntity.setPos(player.getEyePosition().add(coinSpawnPos));
                    Vec3 forwardImpulse = player.getLookAngle().scale(0.3);
                    Vec3 upwardImpulse = new Vec3(0, 0.6, 0);
                    Vec3 inheritedVelocity = player.getDeltaMovement();
                    coinEntity.setDeltaMovement(inheritedVelocity.add(forwardImpulse).add(upwardImpulse));
                    coinEntity.setOwner(player);
                    serverLevel.addFreshEntity(coinEntity);
                    serverLevel.playSound(null, player.blockPosition(), CodSounds.COIN.value(), SoundSource.MASTER, 1, 1);
                });
            }
        }

        return super.use(level, player, usedHand);
    }

    private void fireRaycast(ServerLevel serverLevel, Player player) {
        double maxRange = 250;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(maxRange));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        AABB aabb = player.getBoundingBox().expandTowards(look.scale(maxRange)).inflate(1.0);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(player, start, end, aabb, e -> e != player, maxRange * maxRange);

        Vec3 finalHitPos;
        boolean hitEntity = false;

        if (entityHit != null) {
            double entityDist = entityHit.getLocation().distanceToSqr(start);
            double blockDist = blockHit.getLocation().distanceToSqr(start);

            if (entityDist < blockDist) {
                hitEntity = true;
                finalHitPos = entityHit.getLocation();
            } else {
                finalHitPos = blockHit.getLocation();
            }
        } else {
            finalHitPos = blockHit.getLocation();
        }

        PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new RenderBeamPayload(start.add(0, -0.25, 0), finalHitPos, 0.02f, 20, 0xFFFF00));

        if (hitEntity && entityHit != null) {
            if (entityHit.getEntity() instanceof MarksmanRevolverCoinEntity coin) {
                coin.hit(player);
            }
            entityHit.getEntity().hurt(serverLevel.damageSources().playerAttack(player), 10);
        }
    }

}
