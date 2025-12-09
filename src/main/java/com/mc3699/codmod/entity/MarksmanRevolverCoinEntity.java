package com.mc3699.codmod.entity;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.handlers.CodScheduler;
import com.mc3699.codmod.handlers.beamStuff.RenderBeamPayload;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MarksmanRevolverCoinEntity extends ItemProjectileEntity {

    private static final EntityDataAccessor<Integer> RICO_COUNT = SynchedEntityData.defineId(MarksmanRevolverCoinEntity.class, EntityDataSerializers.INT);
    private static final int MAX_RICOCHETS = 6;
    private static final double SEARCH_RADIUS = 16.0;
    private static final double BASE_DAMAGE = 10.0;
    private transient Set<UUID> chainHitSet = null;

    public MarksmanRevolverCoinEntity(EntityType<? extends ItemProjectileEntity> entityType, Level level) {
        super(entityType, level, new ItemStack(Items.COD, 1), 0, 0, false);
    }

    public void hit(Player player) {
        if (!(level() instanceof ServerLevel serverLevel)) return;
        serverLevel.getServer().execute(() -> handleHit(serverLevel, player));
    }

    private void handleHit(ServerLevel serverLevel, Player player) {
        if (chainHitSet == null) {
            chainHitSet = new HashSet<>();
            if (player != null) chainHitSet.add(player.getUUID());
            chainHitSet.add(this.getUUID());
        }

        Entity nextTarget = getNextTarget(serverLevel, player, this);

        if (nextTarget != null) {
            if (nextTarget instanceof MarksmanRevolverCoinEntity coin) {
                int currentCount = Math.min(getEntityData().get(RICO_COUNT), MAX_RICOCHETS);
                int newCount = Math.min(currentCount + 1, MAX_RICOCHETS);

                PacketDistributor.sendToPlayersTrackingEntity(coin, new RenderBeamPayload(this.position(), coin.position().add(0, coin.getEyeHeight(), 0), 0.05f, 10, 0xFFFF00));

                propagateChainToCoin(coin, chainHitSet, newCount);

                CodScheduler.schedule(2, () -> {
                    serverLevel.getServer().execute(() -> coin.handleHit(serverLevel, player));
                });

            } else if (nextTarget instanceof LivingEntity) {
                int count = getEntityData().get(RICO_COUNT);
                if (count <= 0) count = 1;
                count = Math.min(count, MAX_RICOCHETS);

                for (int i = 0; i < count; i++) {
                    Entity target = getNextTarget(serverLevel, player, this);
                    if (target == null) break;

                    PacketDistributor.sendToPlayersTrackingEntity(target, new RenderBeamPayload(this.position(), target.position().add(0, target.getEyeHeight(), 0), 0.05f, 10, 0xFFFF00));
                    serverLevel.playSound(null, player.blockPosition(), CodSounds.SUPRESSED_GUNSHOT.value(), SoundSource.MASTER, 0.3f, 1);

                    if (target instanceof LivingEntity hitLiving) {
                        double dmg = BASE_DAMAGE * (1 + (double) getEntityData().get(RICO_COUNT));
                        hitLiving.hurt(serverLevel.damageSources().playerAttack(player), (float) dmg);
                        chainHitSet.add(hitLiving.getUUID());
                    }

                    chainHitSet.add(target.getUUID());
                }
            }
        }

        discard();
        serverLevel.playSound(null, blockPosition(), CodSounds.COIN.value(), SoundSource.MASTER, 2f, 1f);
        serverLevel.playSound(null, player.blockPosition(), CodSounds.SUPRESSED_GUNSHOT.value(), SoundSource.MASTER, 0.1f, 2);
    }

    private void propagateChainToCoin(MarksmanRevolverCoinEntity targetCoin, Set<UUID> currentChain, int newRicochetCount) {
        Set<UUID> copy = new HashSet<>(currentChain);
        copy.add(targetCoin.getUUID());
        targetCoin.chainHitSet = copy;
        targetCoin.getEntityData().set(RICO_COUNT, newRicochetCount);
    }

    private Entity getNextTarget(ServerLevel serverLevel, Player firingEntity, Entity origin) {
        AABB searchArea = new AABB(origin.blockPosition()).inflate(SEARCH_RADIUS);
        List<Entity> nearTargets = serverLevel.getEntities(this, searchArea, e -> {
            if (firingEntity != null && e.is(firingEntity)) return false;
            if (!e.isAlive()) return false;
            if (chainHitSet != null && chainHitSet.contains(e.getUUID())) return false;
            return e != this;
        });

        MarksmanRevolverCoinEntity nearestCoin = null;
        double closestCoinDist = Double.MAX_VALUE;
        LivingEntity nearestLiving = null;
        double closestLivingDist = Double.MAX_VALUE;

        for (Entity entity : nearTargets) {
            if (entity instanceof MarksmanRevolverCoinEntity coin) {
                int coinCount = coin.getEntityData().get(RICO_COUNT);
                if (coinCount < MAX_RICOCHETS) {
                    double dist = origin.position().distanceTo(coin.position());
                    if (dist < closestCoinDist) {
                        closestCoinDist = dist;
                        nearestCoin = coin;
                    }
                }
            } else if (entity instanceof LivingEntity living && !living.isDeadOrDying() && !living.isInvulnerable()) {
                HitResult result = serverLevel.clip(new ClipContext(origin.position(), living.getEyePosition(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                if (result.getType() == HitResult.Type.MISS) {
                    double dist = origin.position().distanceTo(living.getEyePosition());
                    if (dist < closestLivingDist) {
                        closestLivingDist = dist;
                        nearestLiving = living;
                    }
                }
            }
        }

        return nearestCoin != null ? nearestCoin : nearestLiving;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.06;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RICO_COUNT, 0);
        super.defineSynchedData(builder);
    }
}
