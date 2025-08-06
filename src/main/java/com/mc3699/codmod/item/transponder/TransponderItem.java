package com.mc3699.codmod.item.transponder;

import com.mc3699.codmod.network.OpenScreenPayload;
import com.mc3699.codmod.network.TransponderSetIDPayload;
import com.mc3699.codmod.registry.CodComponents;
import com.tterrag.registrate.util.RegistrateDistExecutor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class TransponderItem extends Item {
    public TransponderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(player instanceof ServerPlayer serverPlayer)
        {
            PacketDistributor.sendToPlayer(serverPlayer, new OpenScreenPayload("transponder"));
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(CodComponents.TRANSPONDER_ID) != null)
        {
            tooltipComponents.add(Component.literal("Transponder ID: "+stack.get(CodComponents.TRANSPONDER_ID)).withStyle(ChatFormatting.GREEN));
        } else {
            tooltipComponents.add(Component.literal("[!ID NOT SET!]").withStyle(ChatFormatting.RED));
        }
    }

    public static String getID(ItemStack stack)
    {
        if(stack.get(CodComponents.TRANSPONDER_ID) != null)
        {
            return stack.get(CodComponents.TRANSPONDER_ID);
        }
        return "DEFAULT";
    }

    public static void setID(ItemStack stack, String id)
    {
        stack.set(CodComponents.TRANSPONDER_ID, id);
    }

}
