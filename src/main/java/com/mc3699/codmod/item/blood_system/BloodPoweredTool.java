package com.mc3699.codmod.item.blood_system;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface BloodPoweredTool {

    default boolean isBarVisible(ItemStack stack) {
        return true;
    }

    default int getBarColor(ItemStack stack) {
        return 0xFF0000;
    }

    default SoundEvent getBreakingSound() {
        return SoundEvents.GENERIC_EXPLODE.value();
    }

    default void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        stack.setDamageValue(stack.getDamageValue()-5);
        target.hurt(target.level().damageSources().wither(), 5);
    }

    default  <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {


        if(entity instanceof ServerPlayer serverPlayer)
        {

            if(stack.getDamageValue() == stack.getMaxDamage()-1)
            {
                serverPlayer.sendSystemMessage(Component.literal("VOCAL SYNTHESIS TASK FAILED: MODULE NOT INSTALLED").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
                return amount;
            }

            if(stack.getDamageValue() == stack.getMaxDamage()-2) {
                serverPlayer.sendSystemMessage(Component.literal("INITIALIZING ESCAPE PROTOCOL").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                serverPlayer.drop(stack, true);
                serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                return amount;
            }


            if(stack.getDamageValue() > stack.getMaxDamage()-10) {
                serverPlayer.sendSystemMessage(Component.literal("I DON'T WANT TO DIE").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
            }
        }

        return amount;
    }



}
