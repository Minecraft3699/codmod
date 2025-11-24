package com.mc3699.codmod.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class CodFoods {

    public static final FoodProperties BURGER =
            new FoodProperties(
                    8, 16, false,
                    1f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties BANANA =
            new FoodProperties(
                    2, 4, false,
                    0.8f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties BAGUETTE =
            new FoodProperties(
                    6, 12, false,
                    1.5f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties BUN =
            new FoodProperties(
                    4, 6, false,
                    0.5f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties CAKE =
            new FoodProperties(
                    10, 20, false,
                    3f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties NULLCAKE1 =
            new FoodProperties(
                    -110, -10, false,
                    3f, Optional.of(ItemStack.EMPTY), List.of()

            );

    public static final FoodProperties CHEESE =
            new FoodProperties(
                    5, 4, false,
                    0.5f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties CHICKEN_NUGGET =
            new FoodProperties(
                    2, 5, false,
                    0.25f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties CHOCOLATE =
            new FoodProperties(
                    2, 8, false,
                    0.8f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties CUCUMBER = // I hate them too
            new FoodProperties(
                    3, 1, false,
                    2f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties FOOD_BOX =
            new FoodProperties(
                    8, 12, false,
                    1.5f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties LEMON =
            new FoodProperties(
                    2, 2, false,
                    1f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties MANGO = // EAT THE MANGO
            new FoodProperties(
                    4, 1, false,
                    0.8f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties MRE =
            new FoodProperties(
                    20, 40, false,
                    3f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties ORANGE =
            new FoodProperties(
                    2, 3, false,
                    1f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties PIZZA =
            new FoodProperties(
                    10, 16, false,
                    3.5f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties TACO =
            new FoodProperties(
                    6, 6, false,
                    1f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties TOMATO =
            new FoodProperties(
                    4, 4, false,
                    0.6f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties SLIME_CAKE =
            new FoodProperties(
                    40, 40, true,
                    1.0f, Optional.of(ItemStack.EMPTY), List.of()
            );

    public static final FoodProperties CHOCOLATE_CAKE =
            new FoodProperties(
                    40, 40, true,
                    2.0f, Optional.of(ItemStack.EMPTY), List.of()
            );



    public static final FoodProperties ROUND_MEAL =
            new FoodProperties.Builder()
                    .nutrition(1997)
                    .saturationModifier(2000f)
                    .alwaysEdible()
                    .effect(new MobEffectInstance(MobEffects.SATURATION, 600, 255), 1)
                    .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 255), 1)
                    .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 255), 1)
                    .build();

    public static final FoodProperties GOLDEN_COD =
            new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(3)
                    .alwaysEdible()
                    .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1800, 0), 1)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2700, 0), 1)
                    .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800,0), 1)
                    .build();

    public static final FoodProperties ENCHANTED_GOLDEN_COD =
            new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(3)
                    .alwaysEdible()
                    .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1800, 1), 1)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2700, 1), 1)
                    .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800,0), 1)
                    .effect(new MobEffectInstance(MobEffects.HEAL, 200, 0), 1)
                    .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1800, 2), 1)
                    .build();
}
