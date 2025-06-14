package com.mc3699.codmod.datagen;

import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.registry.CodItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
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

        // VOTV Foods
        basicItem(CodItems.BANANA.get());
        basicItem(CodItems.BAGUETTE.get());
        basicItem(CodItems.BUN.get());
        basicItem(CodItems.CAKE.get());
        basicItem(CodItems.CHEESE.get());
        basicItem(CodItems.CHOCOLATE.get());
        basicItem(CodItems.CHICKEN_NUGGET.get());
        basicItem(CodItems.CUCUMBER.get());
        basicItem(CodItems.FOOD_BOX.get());
        basicItem(CodItems.LEMON.get());
        basicItem(CodItems.MANGO.get());
        basicItem(CodItems.MRE.get());
        basicItem(CodItems.ORANGE.get());
        basicItem(CodItems.PIZZA.get());
        basicItem(CodItems.TACO.get());
        basicItem(CodItems.TOMATO.get());
    }
}
