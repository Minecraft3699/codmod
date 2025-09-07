package com.mc3699.codmod.item.marksmanRevolver;

import com.mc3699.codmod.entity.MarksmanRevolverCoinEntity;
import com.mc3699.codmod.registry.CodEntities;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class MarksmanRevolverItem extends Item {
    public MarksmanRevolverItem(Properties properties) {
        super(properties);
    }


    //TODO FROM EYAE:PLEASE BALANCE THIS! ADD A COOLDOWN OR SM SHIT IDFK
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(entity instanceof Player player && player.level() instanceof ServerLevel serverLevel)
        {
            fireRaycast(serverLevel, player);
        }
        return false;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        Vec3 look = player.getLookAngle().scale(1.5);
        Vec3 coinSpawnPos = new Vec3(look.x, -0.5, look.z);

        if(player.level() instanceof ServerLevel serverLevel)
        {
            MarksmanRevolverCoinEntity coinEntity = new MarksmanRevolverCoinEntity(CodEntities.MARKSMAN_COIN_ENTITY.get(), serverLevel);
            coinEntity.setPos(player.getEyePosition().add(coinSpawnPos));
            Vec3 launcVec = player.getLookAngle().scale(0.3);
            coinEntity.setDeltaMovement(new Vec3(0,0.6,0).add(launcVec));
            coinEntity.setOwner(player);
            serverLevel.addFreshEntity(coinEntity);
            serverLevel.playSound(null, player.blockPosition(), CodSounds.COIN.value(), SoundSource.MASTER, 1, 1);
        }

        return super.use(level, player, usedHand);
    }

    private void fireRaycast(ServerLevel serverLevel, Player player)
    {
        double maxRange = 250;
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 angle = player.getLookAngle().scale(maxRange);
        Vec3 end = start.add(angle.scale(maxRange));

        BlockHitResult blockHitResult = serverLevel.clip(new ClipContext(
                start,
                angle,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        spawnParticleLine(serverLevel, start.add(0,-0.25f,0), angle.scale(10), ParticleTypes.ELECTRIC_SPARK, 100);

        AABB hitZone = player.getBoundingBox().expandTowards(angle.scale(maxRange));
        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(player, start, end, hitZone, (entity -> true), 1000000000);

        if (hitResult != null) {
            spawnParticleLine(serverLevel, start.add(0, -0.25f, 0), hitResult.getLocation(), ParticleTypes.ELECTRIC_SPARK, 100);


        if (hitResult.getEntity() instanceof MarksmanRevolverCoinEntity coinEntity) {
            coinEntity.hit(player);
        }


            serverLevel.sendParticles(ParticleTypes.CRIT,
                    (start.x + end.x) / 2,
                    (start.y + end.y) / 2,
                    (start.z + end.z) / 2,
                    10,
                    (end.x - start.x) / 2,
                    (end.y - start.y) / 2,
                    (end.z - start.z) / 2,
                    0.1
            );


            hitResult.getEntity().hurt(serverLevel.damageSources().playerAttack(player), 5);
        }
    }
}
