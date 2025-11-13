package com.mc3699.codmod.item;

import com.mc3699.codmod.item.blood_system.BloodPoweredTool;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class VOneShovelItem extends ShovelItem implements BloodPoweredTool {
    public VOneShovelItem(Properties p_40524_) {
        super(Tiers.IRON, p_40524_);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_SHOVEL) ? Tiers.NETHERITE.getSpeed() * 2 : super.getDestroySpeed(stack,state);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Material Sublimation System: OK").withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.literal("Who's the Earthmover now? Bitch!"));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BloodPoweredTool.super.isBarVisible(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BloodPoweredTool.super.getBarColor(stack);
    }

    @Override
    public SoundEvent getBreakingSound() {
        return BloodPoweredTool.super.getBreakingSound();
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BloodPoweredTool.super.postHurtEnemy(stack,target,attacker);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        return BloodPoweredTool.super.damageItem(stack,amount,entity,onBroken);
    }
}
