package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class SoulTakerItem extends SwordItem {
    public SoulTakerItem(Properties properties) {
        super(CodTiers.SOUL_TAKER_TIER, properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("Made for QXP").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Model by QXP").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return damage * 10;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }


    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_SPEED, new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(
                                        Codmod.MOD_ID,
                                        "soultaker_speed"
                                ), -2.8, AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.MAINHAND
                )
                .build();
    }
}
