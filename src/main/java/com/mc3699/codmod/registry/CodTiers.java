package com.mc3699.codmod.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class CodTiers {

    public static final Tier SOUL_TAKER_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1500,
            0.0f, // not used
            11, // since it extends sword its +4 adding up to 15
            20,
            () -> Ingredient.of(Tags.Items.INGOTS_IRON)
    );

    public static final Tier CONTINUM_SLICER_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2000,
            0.0f, // not used
            10,
            20,
            () -> Ingredient.of(Tags.Items.GEMS_DIAMOND)
    );

}
