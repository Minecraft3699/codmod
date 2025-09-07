package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

public class NetheriteSpoonItem extends SwordItem {

    public NetheriteSpoonItem(Properties properties) {
        super(CodTiers.NETHERITE_SPOON, properties);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return damage * 9;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.UNBREAKING) |
                enchantment.is(Enchantments.SHARPNESS) |
                enchantment.is(Enchantments.LOOTING) |
                enchantment.is(Enchantments.MENDING) |
                enchantment.is(Enchantments.BANE_OF_ARTHROPODS);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Made for Jace").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Texture by Jace").withStyle(ChatFormatting.GRAY));
    }
}
