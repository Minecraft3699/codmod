package com.mc3699.codmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RoundMealItem extends Item {
    public RoundMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("ROUND MEAL").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("MACARONI AND BEEF").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("Protein BLASTED!").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal("SERVES 3 MEN").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("HOW TO DO IT").withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.literal("1. Boil in bag").withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.literal("2. Open and Enjoy").withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("How dare you hoard this").withStyle(ChatFormatting.DARK_PURPLE));
        tooltipComponents.add(Component.literal("for yourself >:(").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        return new FoodProperties.Builder()
                .saturationModifier(2000f)
                .nutrition(1997)
                .alwaysEdible()
                .effect(new MobEffectInstance(MobEffects.SATURATION, 600, 255), 1)
                .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 255), 1)
                .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 255), 1)
                .build();
    }
}
