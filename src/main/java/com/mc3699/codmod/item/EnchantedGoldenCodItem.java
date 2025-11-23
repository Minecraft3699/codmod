package com.mc3699.codmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class EnchantedGoldenCodItem extends Item {
    public EnchantedGoldenCodItem(Properties properties) {
        super(properties);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
    @Override
    public FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity) {
        return new FoodProperties.Builder()
                .nutrition(8)
                .saturationModifier(4f)
                .alwaysEdible()
                .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1800, 1), 1)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2700, 1), 1)
                .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800,0), 1)
                .effect(new MobEffectInstance(MobEffects.HEAL, 200, 0), 1)
                .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1800, 2), 1)
                .build();
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}



