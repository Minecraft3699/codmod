package com.mc3699.codmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatagenLootTableProvider extends LootTableProvider {
    public DatagenLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output, Set.of(), List.of(

                        new LootTableProvider.SubProviderEntry(
                                DatagenBlockLootProvider::new,
                                LootContextParamSets.BLOCK
                        )

                ), registries
        );
    }
}
