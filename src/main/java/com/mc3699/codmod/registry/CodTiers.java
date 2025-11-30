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
            () -> Ingredient.of(CodItems.FOLLY)
    );

    public static final Tier HARD_DRIVE_HAMMER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            10000,
            0.0f, // not used
            10,
            20,
            () -> Ingredient.of(Tags.Items.INGOTS_NETHERITE)
    );

    public static final Tier K_STAR = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            9999,
            1.0f,
            5.5f,
            20,
            () -> Ingredient.of(Tags.Items.INGOTS_NETHERITE)
    );

    public static final Tier NETHERITE_SPOON = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            4000,
            0.4f,
            0f,
            20,
            () -> Ingredient.of(Tags.Items.INGOTS_NETHERITE)
    );



}
