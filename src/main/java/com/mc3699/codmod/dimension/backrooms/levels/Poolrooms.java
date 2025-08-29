package com.mc3699.codmod.dimension.backrooms.levels;

import com.mc3699.codmod.dimension.backrooms.BRGenUtil;
import com.mc3699.codmod.dimension.backrooms.BackroomsLevel;
import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

import java.util.List;

public class Poolrooms extends BackroomsLevel {

    @Override
    public int getStartLevel() {
        return 10;
    }

    @Override
    public int getEndLevel() {
        return 22;
    }

    public static final PerlinNoise FilledNoise = PerlinNoise.create(RandomSource.create(), List.of(1,5,9,9,38,1,2,43));
    public static final PerlinNoise LargeWallNoise = PerlinNoise.create(RandomSource.create(), List.of(33,1,38,29,19,21));
    public static final PerlinNoise PlatformNoise = PerlinNoise.create(RandomSource.create(), List.of(32,69,420,69,69,128,69,420));

    @Override
    public void generate(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
        BRGenUtil.fillLayer(chunkAccess, getStartLevel(), CodBlocks.CERAMIC_TILE.get().defaultBlockState());

        BRGenUtil.fillLayer(chunkAccess, getStartLevel()+3, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15));
        BRGenUtil.fillLayer(chunkAccess, getEndLevel()-3, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15));
        BRGenUtil.fillLayer(chunkAccess, getStartLevel()+1, Blocks.WATER.defaultBlockState());
        BRGenUtil.fillLayer(chunkAccess, getEndLevel(), CodBlocks.CERAMIC_TILE.getDefaultState());

        if(BRGenUtil.isChunkInNoise(chunkAccess, FilledNoise, 0.05))
        {
            BRGenUtil.fillArea(chunkAccess, 0, getStartLevel(), 0, 15, getEndLevel(), 15, CodBlocks.CERAMIC_TILE.getDefaultState());
        } else if (BRGenUtil.isChunkInNoise(chunkAccess, LargeWallNoise, 0.06)) {
            BRGenUtil.generateBasicWalls(chunkAccess, getStartLevel(), getEndLevel(), CodBlocks.CERAMIC_TILE.getDefaultState());
        }

        if(BRGenUtil.isChunkInNoise(chunkAccess, PlatformNoise, 0.02))
        {

        }


    }


}
