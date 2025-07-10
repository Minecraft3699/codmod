package com.mc3699.codmod.backrooms_dimension;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;

public abstract class BackroomsLevel {

    public void generate(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {

    }

    public int getStartLevel(){
     return 0;
    }

    public int getEndLevel()
    {
        return 1;
    }
}
