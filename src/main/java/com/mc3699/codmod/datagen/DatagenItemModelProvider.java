package com.mc3699.codmod.datagen;

import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DatagenItemModelProvider extends ItemModelProvider {
    public DatagenItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(CodItems.GIANNI.get());
        simpleBlockItem(CodBlocks.RADAR.get());
        simpleBlockItem(CodBlocks.STABLE_DISRUPTION.get());
        simpleBlockItem(CodBlocks.MOLTEN_COPPER.get());
        simpleBlockItem(CodBlocks.UAV_CONTROLLER.get());
    }
}
