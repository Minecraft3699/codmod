package com.mc3699.codmod.datagen;

import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Set;

public class DatagenBlockLootProvider extends BlockLootSubProvider {

    protected DatagenBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(CodBlocks.MOLTEN_COPPER.get());
        dropSelf(CodBlocks.STABLE_DISRUPTION.get());
        dropSelf(CodBlocks.SERVER.get());
        dropOther(CodBlocks.UAV_CONTROLLER.get(), Items.COD);
        dropOther(CodBlocks.RADAR.get(), Items.COD);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = List.of(
                CodBlocks.MOLTEN_COPPER.get(),
                CodBlocks.UAV_CONTROLLER.get(),
                CodBlocks.RADAR.get(),
                CodBlocks.STABLE_DISRUPTION.get(),
                CodBlocks.SERVER.get()
        );
        return blocks;
    }

}
