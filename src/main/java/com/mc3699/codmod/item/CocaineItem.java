package com.mc3699.codmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CocaineItem extends Item {
    public CocaineItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Haha get it, it's the hit 1983 movie 'Cocaine' and not actually cocaine"));
        tooltipComponents.add(Component.literal("This is one of the WORST movie's ever! It's worse than the actual drug").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("I was gonna add the whole movie to codmod but that would make it far too large"));
        tooltipComponents.add(Component.literal("Can't remember who wanted cocaine, but you have it now!").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
