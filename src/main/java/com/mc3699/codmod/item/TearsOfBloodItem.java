package com.mc3699.codmod.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;

public class TearsOfBloodItem extends SwordItem {
    private static final int COOLDOWN_TICKS = 50;
    private static final double SLASH_RANGE = 4.0;
    private static final double SLASH_WIDTH = 2.5;
    private static final int WITHER_DURATION = 100;
    private static final int WITHER_AMPLIFIER = 1;

    public TearsOfBloodItem(Item.Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(SwordItem.createAttributes(Tiers.NETHERITE, 4, -2.0F)));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(itemStack);
        }

        performSlashAttack(level, player);

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);

        return InteractionResultHolder.success(itemStack);
    }

    private void performSlashAttack(Level level, Player player) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerPos = player.position().add(0, player.getEyeHeight() * 0.5, 0);

        Vec3 slashEnd = playerPos.add(lookVec.scale(SLASH_RANGE));

        AABB slashBox = new AABB(playerPos, slashEnd).inflate(SLASH_WIDTH);

        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                slashBox,
                entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : entities) {
            Vec3 toTarget = target.position().subtract(playerPos).normalize();
            double dotProduct = lookVec.dot(toTarget);

            if (dotProduct > 0.3) {
                double distance = playerPos.distanceTo(target.position());

                if (distance <= SLASH_RANGE) {
                    target.hurt(level.damageSources().playerAttack(player), 8.0F);

                    target.addEffect(new MobEffectInstance(
                            MobEffects.WITHER,
                            WITHER_DURATION,
                            WITHER_AMPLIFIER
                    ));

                    Vec3 knockback = lookVec.scale(0.5);
                    target.push(knockback.x, 0.2, knockback.z);
                }
            }
        }

        if (level instanceof ServerLevel serverLevel) {
            spawnSlashParticles(serverLevel, playerPos, lookVec);
        }
    }

    private void spawnSlashParticles(ServerLevel level, Vec3 startPos, Vec3 direction) {
        Vec3 rightVec = new Vec3(-direction.z, 0, direction.x).normalize();

        int particleCount = 25;
        for (int i = 0; i < particleCount; i++) {
            double arcProgress = (i / (double) (particleCount - 1)) - 0.5;
            double angle = arcProgress * Math.PI * 0.8;

            double distance = SLASH_RANGE * 0.75;
            Vec3 offset = direction.scale(Math.cos(angle) * distance)
                    .add(rightVec.scale(Math.sin(angle) * distance));

            Vec3 particlePos = startPos.add(offset);

            level.sendParticles(
                    ParticleTypes.INSTANT_EFFECT,
                    particlePos.x, particlePos.y, particlePos.z,
                    2,
                    0.1, 0.1, 0.1,
                    0.0
            );
        }
    }
}