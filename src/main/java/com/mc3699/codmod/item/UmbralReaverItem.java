package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;


public class UmbralReaverItem extends Item {
    private static final int TELEPORT_COOLDOWN = 200;

    public UmbralReaverItem(Properties properties) {
        super(properties
                .stacksTo(1)
                .durability(2031)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "sword_damage"),
                                10.0,
                                AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                        )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "sword_speed"),
                                -3.4,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.MAX_HEALTH,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "sword_health"),
                                -6.0,
                                AttributeModifier.Operation.ADD_VALUE
                                ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if(!level.isClientSide()) {
            ThrownEnderpearl pearl = new ThrownEnderpearl(level, player);
            if(!player.isShiftKeyDown()) {
                pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 1.0f);

                CompoundTag tag = pearl.getPersistentData();
                tag.putBoolean("fromReaver", true);

                level.addFreshEntity(pearl);

                player.getCooldowns().addCooldown(this, TELEPORT_COOLDOWN);
            } else {
                pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3.5f, 1.0f);

                pearl.setNoGravity(true);
                CompoundTag tag = pearl.getPersistentData();
                tag.putBoolean("fromReaver", true);

                level.addFreshEntity(pearl);

                player.getCooldowns().addCooldown(this, TELEPORT_COOLDOWN);
            }



        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }



    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.DIAMOND);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("This is going to hurt, really badly").withStyle(ChatFormatting.RED));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.UNBREAKING) || enchantment.is(Enchantments.MENDING) ||
                enchantment.is(Enchantments.SHARPNESS) || enchantment.is(Enchantments.LOOTING) ||
                enchantment.is(Enchantments.SMITE) || enchantment.is(Enchantments.BANE_OF_ARTHROPODS) ||
                enchantment.is(Enchantments.FIRE_ASPECT) || enchantment.is(Enchantments.KNOCKBACK);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 240, 0));
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}

