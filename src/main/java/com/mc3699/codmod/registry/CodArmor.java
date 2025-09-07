package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;

public class CodArmor {

    public static final ArmorMaterial NBC_ARMOR_MATERIAL = new ArmorMaterial(
            Map.of(
                    ArmorItem.Type.HELMET, 1,
                    ArmorItem.Type.CHESTPLATE, 2,
                    ArmorItem.Type.LEGGINGS, 2,
                    ArmorItem.Type.BOOTS, 1
            ),
            10,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "nbc"))),
            0,
            2
    );

    public static final ArmorMaterial SPACE_SUIT_ARMOR_MATERIAL = new ArmorMaterial(
            Map.of(
                    ArmorItem.Type.HELMET, 1,
                    ArmorItem.Type.CHESTPLATE, 5,
                    ArmorItem.Type.LEGGINGS, 3,
                    ArmorItem.Type.BOOTS, 1
            ),
            10,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(CodItems.SPACE_GRADE_FABRIC),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "space_suit"))),
            0,
            0
    );

}
