package com.mc3699.codmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class VoidBreakerItem extends SwordItem {
    public VoidBreakerItem(Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        return  damage*15;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(Component.literal("Made for Electronauticon").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Texture by Electronauticon").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        player.sendSystemMessage(Component.literal(entity.getType().toString()));

        if(entity.getType().toString().contains("thebrokenscript"))
        {
            entity.discard();
        }

        return super.onLeftClickEntity(stack, player, entity);
    }
}
