package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.EnderCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

import static com.mc3699.codmod.registry.CodBlocks.ENDER_CROP;

public class DatagenBlockStateProvider extends BlockStateProvider {
    public DatagenBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Codmod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeCrop((CropBlock) ENDER_CROP.get(), EnderCropBlock.AGE, "ender_crop_stage", "ender_crop_stage");





    }

    public void makeCrop(CropBlock block, IntegerProperty ageProperty, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, ageProperty, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, IntegerProperty ageProperty, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(
                modelName + state.getValue(ageProperty),
                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/" + textureName + state.getValue(ageProperty))
        ).renderType("cutout"));

        return models;
    }




}
