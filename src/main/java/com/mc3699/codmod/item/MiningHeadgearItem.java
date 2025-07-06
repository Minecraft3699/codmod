package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import foundry.veil.api.client.render.light.AreaLight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;


public class MiningHeadgearItem extends Item {
    private AreaLight light;

    public MiningHeadgearItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return armorType.getIndex() == EquipmentSlot.HEAD.getIndex();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.BLOCK_BREAK_SPEED,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "mining_gear_bs"),
                                5, AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.HEAD
                )
                .add(
                        Attributes.MINING_EFFICIENCY,
                        new AttributeModifier(
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "mining_gear_me"),
                                5, AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.HEAD
                )
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if (slotId == EquipmentSlot.HEAD.getIndex() && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 10, 1, true, false, false));
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
