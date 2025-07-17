package com.mc3699.codmod.backrooms_dimension.levels;

import com.mc3699.codmod.backrooms_dimension.BRGenUtil;
import com.mc3699.codmod.backrooms_dimension.BackroomsLevel;
import com.mc3699.codmod.registry.CodBlocks;
import foundry.veil.api.client.render.light.Light;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;

public class Poolrooms extends BackroomsLevel {

    @Override
    public int getStartLevel() {
        return 10;
    }

    @Override
    public int getEndLevel() {
        return 22;
    }

    @Override
    public void generate(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
        BRGenUtil.fillLayer(chunkAccess, getStartLevel(), CodBlocks.CERAMIC_TILE.get().defaultBlockState());

        BRGenUtil.fillLayer(chunkAccess, getStartLevel()+3, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15));
        BRGenUtil.fillLayer(chunkAccess, getEndLevel()-3, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15));



        BRGenUtil.fillLayer(chunkAccess, getStartLevel()+1, Blocks.WATER.defaultBlockState());
        BRGenUtil.fillLayer(chunkAccess, getEndLevel(), CodBlocks.CERAMIC_TILE.getDefaultState());
    }


}
