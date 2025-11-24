package com.mc3699.codmod.item.starstruckSpear;

import com.mc3699.codmod.registry.CodTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;


public class StarstruckSpearItem extends AxeItem {

    public StarstruckSpearItem(Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(AxeItem
                        .createAttributes(Tiers.NETHERITE, 4f, -2.9f))
                .durability(2250)
                .rarity(Rarity.RARE));
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Reach for the stars!").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.GOLD_INGOT);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.SHARPNESS) | enchantment.is(Enchantments.UNBREAKING) | enchantment.is(Enchantments.FIRE_ASPECT) |
                enchantment.is(Enchantments.LOOTING) | enchantment.is(Enchantments.KNOCKBACK) | enchantment.is(Enchantments.BREACH) |
                enchantment.is(Enchantments.SMITE) | enchantment.is(Enchantments.IMPALING) | enchantment.is(Enchantments.BANE_OF_ARTHROPODS);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (!level.isClientSide() && livingEntity instanceof ServerPlayer player) {

            int chargeTime = this.getUseDuration(stack, livingEntity) - timeCharged;
            StarstruckDash.performDash(player, chargeTime);
            player.getCooldowns().addCooldown(this, 60);
            return;
        }

        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }
    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_TRIDENT_ACTIONS.contains(itemAbility);
    }
}
