package com.mc3699.codmod.backrooms_dimension.levels;

import com.mc3699.codmod.backrooms_dimension.BRGenUtil;
import com.mc3699.codmod.backrooms_dimension.BackroomsLevel;
import com.mc3699.codmod.backrooms_dimension.BackroomsStructures;
import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

import java.util.List;

public class BackroomsLevelZero extends BackroomsLevel {


    public static final PerlinNoise UnlitRoomNoise = PerlinNoise.create(RandomSource.create(), List.of(1,5,9));
    public static final PerlinNoise EmptyAreaNoise = PerlinNoise.create(RandomSource.create(), List.of(2,1,9));
    public static final PerlinNoise RedRoomNoise = PerlinNoise.create(RandomSource.create(), List.of(0,4,2));

    List<BackroomsStructures.BackroomsStructureInfo> STRUCTURE_POOL = List.of(
            BackroomsStructures.OASIS,
            BackroomsStructures.PYRAMID,
            BackroomsStructures.OUTPOST_K84,
            BackroomsStructures.CARBON_ANOMALY
    );

    @Override
    public int getEndLevel() {
        return 9;
    }

    @Override
    public void generate(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {

        BRGenUtil.fillLayer(chunkAccess, 0, Blocks.BEDROCK);
        if(BRGenUtil.isChunkInNoise(chunkAccess, RedRoomNoise, 0.3))
        {
            BRGenUtil.fillLayer(chunkAccess, getStartLevel(), CodBlocks.RED_MOIST_CARPET.get());
            BRGenUtil.fillLayer(chunkAccess, getEndLevel(), CodBlocks.RED_CEILING_TILE.get());
            if(!BRGenUtil.isChunkInNoise(chunkAccess, UnlitRoomNoise, 0.15))
            {
                BRGenUtil.generateLights(chunkAccess, getEndLevel(), true);
            }
            if(!BRGenUtil.isChunkInNoise(chunkAccess, EmptyAreaNoise, 0.2))
            {
                BRGenUtil.generateBasicWalls(chunkAccess, getStartLevel(), getEndLevel(), CodBlocks.RED_UGLY_WALLPAPER.get());
            }
        } else {
            BRGenUtil.fillLayer(chunkAccess, getStartLevel()+1, CodBlocks.MOIST_CARPET.get());
            BRGenUtil.fillLayer(chunkAccess, getEndLevel(), CodBlocks.CEILING_TILE.get());
            if(!BRGenUtil.isChunkInNoise(chunkAccess, UnlitRoomNoise, 0.15))
            {
                BRGenUtil.generateLights(chunkAccess, getEndLevel(), false);
            }
            if(!BRGenUtil.isChunkInNoise(chunkAccess, EmptyAreaNoise, 0.2))
            {
                BRGenUtil.generateBasicWalls(chunkAccess, getStartLevel()+1, getEndLevel(), CodBlocks.UGLY_WALLPAPER.get());
            }
        }

        if(worldGenRegion.getRandom().nextInt(0,300) == 1)
        {
            BackroomsStructures.BackroomsStructureInfo structureInfo = BRGenUtil.rollStructure(STRUCTURE_POOL);
            BRGenUtil.placeBackroomsStructure(structureInfo, worldGenRegion, chunkAccess.getPos().getWorldPosition().atY(getStartLevel()+2), worldGenRegion.getRandom());
        }


    }
}
