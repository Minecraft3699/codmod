package com.mc3699.codmod.item;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.VeilRenderer;
import foundry.veil.api.client.render.light.AreaLight;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class MiningHeadgearItem extends Item {
    private AreaLight light;

    public MiningHeadgearItem(Properties properties) {
        super(properties);
        light = new AreaLight();
        light.setAngle(30);
        light.setDistance(30);
        light.setColor(0xFF0000);
        light.setBrightness(10);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return armorType.getIndex() == EquipmentSlot.HEAD.getIndex();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if(slotId == EquipmentSlot.HEAD.getIndex())
        {
            if(level.isClientSide)
            {

            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
