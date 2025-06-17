package com.mc3699.codmod.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public class CircuitsBaneItem extends SwordItem {
    public CircuitsBaneItem() {
        super(Tiers.NETHERITE, new Properties().durability(256).rarity(Rarity.EPIC));
    }
}
