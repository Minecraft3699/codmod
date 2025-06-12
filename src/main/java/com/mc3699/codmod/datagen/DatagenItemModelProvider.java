package com.mc3699.codmod.datagen;

import com.mc3699.codmod.block.BlockRegistration;
import com.mc3699.codmod.item.ItemRegistration;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DatagenItemModelProvider extends ItemModelProvider {
    public DatagenItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemRegistration.GIANNI.get());
        simpleBlockItem(BlockRegistration.RADAR.get());
        simpleBlockItem(BlockRegistration.MOLTEN_COPPER.get());
        simpleBlockItem(BlockRegistration.UAV_CONTROLLER.get());
    }
}
