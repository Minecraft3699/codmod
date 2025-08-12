package com.mc3699.codmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import java.util.function.Consumer;

public class CodPickaxeItem extends PickaxeItem {
    public CodPickaxeItem(Properties properties) {
        super(Tiers.NETHERITE,properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("I am not responsible for this one -MC3699 (Made by Lumi)"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }


}
