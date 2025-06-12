package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class IntegrityCookieItem extends Item {
    public IntegrityCookieItem() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(-10)
                        .saturationModifier(0)
                        .effect(() -> new MobEffectInstance(CodMobEffects.HEART_CORRUPTION, 200, 5, false, true), 1.0f)
                        .alwaysEdible()
                        .build()
                ));
    }
}
