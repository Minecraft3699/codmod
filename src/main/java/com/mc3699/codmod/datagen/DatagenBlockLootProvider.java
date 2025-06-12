package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DatagenBlockLootProvider extends BlockLootSubProvider {

    protected DatagenBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistration.MOLTEN_COPPER.get());
        dropOther(BlockRegistration.UAV_CONTROLLER.get(), Items.COD);
        dropOther(BlockRegistration.RADAR.get(), Items.COD);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = List.of(
            BlockRegistration.MOLTEN_COPPER.get(),
            BlockRegistration.UAV_CONTROLLER.get(),
            BlockRegistration.RADAR.get()
        );
        return blocks;
    }

}
