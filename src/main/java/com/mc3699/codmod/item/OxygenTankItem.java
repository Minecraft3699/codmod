package com.mc3699.codmod.item;

import com.mc3699.codmod.dimension.DimensionKeys;
import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.registry.CodComponents;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class OxygenTankItem extends Item {
    public static final int MAX_OXYGEN = 1000;

    public OxygenTankItem(Properties properties) {
        super(properties);
    }

    public int getOxygen(ItemStack stack)
    {
        return stack.getOrDefault(CodComponents.OXYGEN, 0);
    }

    public void setOxygen(ItemStack stack, int value)
    {
        stack.set(CodComponents.OXYGEN, Math.min(value, MAX_OXYGEN));
    }

    public boolean consumeOxygen(ItemStack stack, int amount) {
        int current = getOxygen(stack);
        if (current >= amount) {
            setOxygen(stack, current - amount);
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Oxygen: "+getOxygen(stack)+"ml").withStyle(ChatFormatting.AQUA));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int oxygen = getOxygen(stack);
        return Math.round((13.0F * oxygen) / MAX_OXYGEN);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = (float) getOxygen(stack) / (float) MAX_OXYGEN;

        int red   = 0xFF0000;
        int blue  = 0x00FFFF;

        int r = (int)(((red >> 16) & 0xFF) * (1 - f) + ((blue >> 16) & 0xFF) * f);
        int g = (int)(((red >> 8) & 0xFF)  * (1 - f) + ((blue >> 8) & 0xFF)  * f);
        int b = (int)((red & 0xFF)         * (1 - f) + (blue & 0xFF)        * f);

        return (r << 16) | (g << 8) | b;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level instanceof ServerLevel serverLevel)
        {
            if(serverLevel.dimension() != DimensionKeys.ENTROPY)
            {

                ItemStack stack = player.getItemInHand(usedHand);
                if(stack.is(CodItems.OXYGEN_TANK))
                {
                    stack.set(CodComponents.OXYGEN, 1000);
                }

            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel serverLevel &&
                serverLevel.getBlockState(context.getClickedPos()).equals(CodBlocks.OXYGEN_DISTRIBUTOR.get().defaultBlockState()))
        {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            ItemStack stack = player.getItemInHand(context.getHand());
            stack.set(CodComponents.OXYGEN, 1000);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
