package com.mc3699.codmod.item;

import com.mc3699.codmod.effect.EffectRegistration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class IntegrityCookieItem extends Item {
    public IntegrityCookieItem() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(-10)
                        .saturationModifier(0)
                        .effect(() -> new MobEffectInstance(EffectRegistration.HEART_CORRUPTION, 200, 5, false, true), 1.0f)
                        .alwaysEdible()
                        .build()
                ));
    }
}
