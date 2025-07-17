package com.mc3699.codmod.backrooms_dimension;

import com.mc3699.codmod.backrooms_dimension.levels.BackroomsLevelZero;
import com.mc3699.codmod.backrooms_dimension.levels.Poolrooms;
import com.mc3699.codmod.registry.CodBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BackroomsGenerator extends ChunkGenerator {
    public static final MapCodec<BackroomsGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource))
            .apply(instance, BackroomsGenerator::new));

    public BackroomsGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(
            WorldGenRegion worldGenRegion,
            long l,
            RandomState randomState,
            BiomeManager biomeManager,
            StructureManager structureManager,
            ChunkAccess chunkAccess,
            GenerationStep.Carving carving
    ) {
        // nuh uh
    }

    BackroomsLevelZero levelZero = new BackroomsLevelZero();
    Poolrooms poolrooms = new Poolrooms();


    @Override
    public void buildSurface(
            WorldGenRegion worldGenRegion,
            StructureManager structureManager,
            RandomState randomState,
            ChunkAccess chunkAccess
    ) {

        levelZero.generate(worldGenRegion, structureManager, randomState, chunkAccess);
        poolrooms.generate(worldGenRegion, structureManager, randomState, chunkAccess);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {
        // nuh uh
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(
            Blender blender,
            RandomState randomState,
            StructureManager structureManager,
            ChunkAccess chunkAccess
    ) {
        return CompletableFuture.completedFuture(chunkAccess);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(
            int i,
            int i1,
            Heightmap.Types types,
            LevelHeightAccessor levelHeightAccessor,
            RandomState randomState
    ) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int i, int i1, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(getMinY(), new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        // nuh uh
    }
}
