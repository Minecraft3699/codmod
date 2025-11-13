package com.mc3699.codmod.item;

import com.mc3699.codmod.item.blood_system.BloodPoweredTool;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.print.DocFlavor;
import java.util.List;
import java.util.function.Consumer;

public class VOnePickaxeItem extends PickaxeItem implements BloodPoweredTool {
    public VOnePickaxeItem(Properties p_42964_) {
        super(Tiers.IRON, p_42964_);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return Tiers.NETHERITE.getSpeed() * 2;
        return super.getDestroySpeed(stack,state);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Impact Amplification System: OK").withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.literal("Splay the ore of these blocks' profane form across your inventory!"));
        tooltipComponents.add(Component.literal("Thy ore shall glisten before the crushers of man!"));
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return damage * 4f;
    }

    @Override
    public SoundEvent getBreakingSound() {
        return BloodPoweredTool.super.getBreakingSound();
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BloodPoweredTool.super.getBarColor(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BloodPoweredTool.super.isBarVisible(stack);
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
