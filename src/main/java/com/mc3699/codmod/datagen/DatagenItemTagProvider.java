package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class DatagenItemTagProvider extends ItemTagsProvider {
    public DatagenItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    public static final TagKey<Item> SPACE_SUIT_VALID = ItemTags.create(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID,"space_suit_valid"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("supplementaries","cookies")))
                .add(CodItems.INTEGRITY_COOKIE.get());
        tag(SPACE_SUIT_VALID)
                .add(CodItems.SPACE_CHESTPLATE.get())
                .add(CodItems.SPACE_LEGGINGS.get())
                .add(CodItems.SPACE_BOOTS.get())
                .add(CodItems.SPACE_HELMET.get());
    }
}
