package com.mc3699.codmod.datagen;

import com.mc3699.codmod.registry.CodItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class DatagenItemTagProvider extends ItemTagsProvider {
    public DatagenItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("supplementaries","cookies")))
                .add(CodItems.INTEGRITY_COOKIE.get());

    }
}
