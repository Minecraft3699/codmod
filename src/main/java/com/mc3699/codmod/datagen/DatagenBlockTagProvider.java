package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DatagenBlockTagProvider extends BlockTagsProvider {
    public DatagenBlockTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            String modId,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CodBlocks.MOLTEN_COPPER.get())
                .add(CodBlocks.RADAR.get())
                .add(CodBlocks.UAV_CONTROLLER.get())
                .add(CodBlocks.STABLE_DISRUPTION.get())
                .add(CodBlocks.SERVER.get())
                .add(CodBlocks.CEILING_LIGHT.get())
                .add(CodBlocks.RED_CEILING_LIGHT.get())
                .add(CodBlocks.THRESHOLD_BACKPLANE.get())
                .add(CodBlocks.THRESHOLD_EMITTER.get())
                .add(CodBlocks.PUBLIC_STATIC_VOID_BLOCK.get())
                .add(CodBlocks.PUBLIC_STATIC_VOID_SLAB.get())
                .add(CodBlocks.PUBLIC_STATIC_VOID_STAIRS.get())
        ;
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(CodBlocks.UGLY_WALLPAPER.get())
                .add(CodBlocks.RED_UGLY_WALLPAPER.get())
        ;
        tag(BlockTags.WOOL)
                .add(CodBlocks.MOIST_CARPET.get())
                .add(CodBlocks.RED_MOIST_CARPET.get())
        ;

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(CodBlocks.COD_BLOCK.get());

        tag(BlockTags.STONE_ORE_REPLACEABLES)
                .add(CodBlocks.ENTROPY_BASALT.get())
                .add(CodBlocks.ENTROPY_SHALE.get());

        tag(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
                .add(CodBlocks.ENTROPY_BASALT.get());

        tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "entropy_planet_replaceable")))
                .add(CodBlocks.ENTROPY_BASALT.get())
                .add(CodBlocks.ENTROPY_SHALE.get());
    }

}
