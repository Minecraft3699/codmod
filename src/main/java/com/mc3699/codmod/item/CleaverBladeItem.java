package com.mc3699.codmod.item;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.io.Console;
import java.util.List;
import java.util.WeakHashMap;

public class CleaverBladeItem extends SwordItem {

    private static final WeakHashMap<java.util.UUID, Boolean> LAST_BOOST = new WeakHashMap<>();
    private static final WeakHashMap<java.util.UUID, Float> LAST_HEALTH = new WeakHashMap<>();

    private static final WeakHashMap<java.util.UUID, Integer> CRIT_HEAL_TIMER = new WeakHashMap<>();
    private static final WeakHashMap<java.util.UUID, Integer> CRIT_COOLDOWN = new WeakHashMap<>();

    private static final int CRIT_HEAL_DURATION = 40;
    private static final float TOTAL_HEAL = 4.0f;
    private static final int HEAL_COOLDOWN_TICKS = 80;

    private static final double SWEEP_RADIUS = 2.0D;
    private static final double PLAYER_MAX_DISTANCE = 4.5D;

    public CleaverBladeItem(Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(SwordItem.createAttributes(Tiers.NETHERITE, 7f, -3.3f)));
    }

    @Override
    public int getEnchantmentValue() {
        return 24;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (!(entity instanceof Player player)) return;
        if (level.isClientSide()) return;

        boolean holding = player.getMainHandItem() == stack;

        int timer = CRIT_HEAL_TIMER.getOrDefault(player.getUUID(), 0);
        int cooldown = CRIT_COOLDOWN.getOrDefault(player.getUUID(), 0);

        if (cooldown > 0) {
            CRIT_COOLDOWN.put(player.getUUID(), cooldown - 1);
        }

        if (timer > 0) {
            float healPerTick = TOTAL_HEAL / CRIT_HEAL_DURATION;
            player.setHealth(Math.min(player.getHealth() + healPerTick, player.getMaxHealth()));
            timer--;
            if (timer <= 0) CRIT_HEAL_TIMER.remove(player.getUUID());
            else CRIT_HEAL_TIMER.put(player.getUUID(), timer);
        }



        if (holding) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2, true, false));
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.SHARPNESS) || enchantment.is(Enchantments.MENDING) ||
                enchantment.is(Enchantments.BREACH) || enchantment.is(Enchantments.FIRE_ASPECT) ||
                enchantment.is(Enchantments.SWEEPING_EDGE) || enchantment.is(Enchantments.UNBREAKING);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && !player.level().isClientSide()) {
            boolean didHit = player.getPersistentData().getBoolean("cleaver_hit");
            if (!didHit) {
                float drain = 2.0F;
                player.causeFoodExhaustion(drain);
            }
            player.getPersistentData().putBoolean("cleaver_hit", false);
        }
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            player.getPersistentData().putBoolean("cleaver_hit", true);
            boolean fullyCharged = player.getAttackStrengthScale(0.5F) > 0.9F;
            boolean isFalling = player.fallDistance > 0.0F;
            boolean canCrit =
                    !player.onGround() &&
                            !player.isInWater() &&
                            !player.isSprinting() &&
                            !player.hasEffect(MobEffects.BLINDNESS);

            boolean isCrit = fullyCharged && isFalling && canCrit;

            int cooldown = CRIT_COOLDOWN.getOrDefault(player.getUUID(), 0);

            if (isCrit && cooldown <= 0) {
                CRIT_HEAL_TIMER.put(player.getUUID(), CRIT_HEAL_DURATION);
                CRIT_COOLDOWN.put(player.getUUID(), HEAL_COOLDOWN_TICKS);

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 0.8F, 0.5F);

                if (player.level() instanceof ServerLevel server) {

                    server.sendParticles(
                            ParticleTypes.CRIT,
                            target.getX(),
                            target.getY() + target.getBbHeight() * 0.5,
                            target.getZ(),
                            10,
                            0.1, 0.1, 0.1, 0.01
                    );

                    server.sendParticles(
                            ParticleTypes.POOF,
                            target.getX(),
                            target.getY() + target.getBbHeight() * 0.5,
                            target.getZ(),
                            6,
                            0.2, 0.2, 0.2, 0.04
                    );
                }
            }

            player.sweepAttack();
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);

            double dx = -Mth.sin(player.getYRot() * (float)Math.PI / 180F) * 1.25; // moved outward
            double dz =  Mth.cos(player.getYRot() * (float)Math.PI / 180F) * 1.25;

            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.SWEEP_ATTACK,
                        player.getX() + dx,
                        player.getY(0.5D),
                        player.getZ() + dz,
                        0, 0, 0, 0, 1.0
                );
            }

            float sweepDamage = (float)(1.0F + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F);

            for (LivingEntity aoe : player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    target.getBoundingBox().inflate(SWEEP_RADIUS),
                    e -> e != player && e != target && player.distanceTo(e) < PLAYER_MAX_DISTANCE
            )) {
                aoe.hurt(player.damageSources().playerAttack(player), sweepDamage);
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Your heart, your weapon"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
