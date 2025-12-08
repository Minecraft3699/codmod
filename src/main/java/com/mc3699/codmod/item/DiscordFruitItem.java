package com.mc3699.codmod.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class DiscordFruitItem extends Item {
    Random random = new Random();


    public DiscordFruitItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
            int duration = 200 + random.nextInt(1600);
            int strength = random.nextInt(2);

            giveRandomEffect(player, duration, strength);
            player.getCooldowns().addCooldown(this,180);
        }


        return super.finishUsingItem(stack, level, livingEntity);
    }

    private void giveRandomEffect(Player player, int duration, int strength) {
        int STRENGTH = strength;
        int DURATION = duration;
        Holder<MobEffect>[] possibleEffects = new Holder[]{
                MobEffects.BAD_OMEN,
                MobEffects.JUMP,
                MobEffects.GLOWING,
                MobEffects.WIND_CHARGED,
                MobEffects.LEVITATION,
                MobEffects.REGENERATION,
                MobEffects.NIGHT_VISION,
                MobEffects.CONFUSION,
                MobEffects.SLOW_FALLING,

        };

        Holder<MobEffect> randomEffect = possibleEffects[player.getRandom().nextInt(possibleEffects.length)];
        if (randomEffect == MobEffects.BAD_OMEN) {
            duration = 12000;
            strength = 1 + random.nextInt(5);
            player.addEffect(new MobEffectInstance(randomEffect, duration, strength));
        } else {
            player.addEffect(new MobEffectInstance(randomEffect, duration, strength));
        }
    }
}