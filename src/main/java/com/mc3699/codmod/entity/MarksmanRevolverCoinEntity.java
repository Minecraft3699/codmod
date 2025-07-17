package com.mc3699.codmod.entity;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MarksmanRevolverCoinEntity extends ItemProjectileEntity {

    private static final EntityDataAccessor<Integer> RICO_COUNT = SynchedEntityData.defineId(
            MarksmanRevolverCoinEntity.class,
            EntityDataSerializers.INT
    );

    public MarksmanRevolverCoinEntity(EntityType<? extends ItemProjectileEntity> entityType, Level level) {
        super(entityType, level, new ItemStack(Items.COD, 1), 0, 0, false);
    }

    public void hit(Player player)
    {
        if(level() instanceof ServerLevel serverLevel)
        {
            LivingEntity nearest = getNearest(serverLevel, player);
            if(nearest != null)
            {
                spawnParticleLine(serverLevel, position(), nearest.getEyePosition(), ParticleTypes.ELECTRIC_SPARK, 100);
                nearest.hurt(serverLevel.damageSources().playerAttack(player), 20);
            }
            discard();
        }
    }

    public static void spawnParticleLine(ServerLevel level, Vec3 from, Vec3 to, ParticleOptions particle, int steps) {
        Vec3 delta = to.subtract(from);
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Vec3 point = from.add(delta.scale(t));
            level.sendParticles(
                    particle,
                    point.x, point.y, point.z,
                    1,
                    0, 0, 0,
                    0
            );
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RICO_COUNT, 0);
        super.defineSynchedData(builder);
    }

    private LivingEntity getNearest(ServerLevel serverLevel, Player firingEntity)
    {
        AABB searchArea = new AABB(blockPosition()).inflate(16);
        List<Entity> nearTargets = serverLevel.getEntities(this, searchArea, entity -> !entity.is(firingEntity) && entity.isAlive());
        LivingEntity nearest = null;
        double closestDist = Double.MAX_VALUE;


        for(Entity target : nearTargets)
        {

            if(target instanceof MarksmanRevolverCoinEntity coinEntity)
            {

                int nextRicoCount = coinEntity.getEntityData().get(RICO_COUNT);
                int ricoCount = getEntityData().get(RICO_COUNT);
                if(nextRicoCount == 0)
                {
                    coinEntity.getEntityData().set(RICO_COUNT, 1);
                    spawnParticleLine(serverLevel, position(), coinEntity.getPosition(0F), ParticleTypes.ELECTRIC_SPARK, 100);
                    coinEntity.hit(firingEntity);
                    serverLevel.playSound(null, blockPosition(), CodSounds.COIN.value(), SoundSource.MASTER, 2, 1);
                }
            }


            if(target instanceof LivingEntity livingTarget)
            {
                HitResult result = serverLevel.clip(new ClipContext(
                        position(), target.getEyePosition(),
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        this
                ));

                if (result.getType() == HitResult.Type.MISS) {
                    double dist = position().distanceTo(target.getEyePosition());
                    if (dist < closestDist) {
                        closestDist = dist;
                        nearest = livingTarget;
                    }
                }
            }
        }

        return nearest;
    }
}
