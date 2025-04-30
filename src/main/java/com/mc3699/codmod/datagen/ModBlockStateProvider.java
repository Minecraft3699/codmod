package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockRegistration;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Codmod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cubeAll(BlockRegistration.MOLTEN_COPPER.get());
    }
}
