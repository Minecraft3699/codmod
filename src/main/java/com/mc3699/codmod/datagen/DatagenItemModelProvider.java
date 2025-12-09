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
        basicItem(CodItems.INERT_DUST.get());
        basicItem(CodItems.INERT_SEEDS.get());
        basicItem(CodItems.DISCORD_FRUIT.get());
        handheldItem(CodItems.CIRCUITS_BANE.get());

        simpleBlockItem(CodBlocks.RADAR.get());
        simpleBlockItem(CodBlocks.STABLE_DISRUPTION.get());
        simpleBlockItem(CodBlocks.MOLTEN_COPPER.get());
        simpleBlockItem(CodBlocks.UAV_CONTROLLER.get());
        simpleBlockItem(CodBlocks.SERVER.get());
        simpleBlockItem(CodBlocks.JOHN_GEOMETRY.get());


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

        //existingFileHelper.trackGenerated(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/item/maxwell"), PackType.CLIENT_RESOURCES, ".png", "textures");

        /*
        getBuilder("maxwell")
                .customLoader(ObjModelBuilder::begin)
                .modelLocation(ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "models/item/maxwell.obj"))
                .automaticCulling(false)
                .flipV(true)
                .end()
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/item/maxwell"))
                .transforms()
                .transform(ItemDisplayContext.GUI).translation(10,0,0).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).translation(0,8,4.75f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).translation(-8.25f,7.75f,-1.75f).rotation(0,-10,0).end()
                .transform(ItemDisplayContext.GROUND).translation(0,5.5f,0).end()
                .end();
         */
    }
}
