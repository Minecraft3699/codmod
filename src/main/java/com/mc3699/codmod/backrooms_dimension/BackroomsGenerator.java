package com.mc3699.codmod.backrooms_dimension;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.wendigodrip.thebrokenscript.TheBrokenScript;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class BackroomsGenerator extends ChunkGenerator {



    public static final MapCodec<BackroomsGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource)
            ).apply(instance, BackroomsGenerator::new)
    );



    public BackroomsGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }



    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
        // nuh uh
    }

    List<BackroomsStructures.BackroomsStructureInfo> ROAD_XP_STRUCTURES = List.of(
      BackroomsStructures.STREET_XP_COD_STORE,
      BackroomsStructures.STREET_XP_FARM,
      BackroomsStructures.STREET_XP_FOREST,
      BackroomsStructures.STREET_XP_POND,
      BackroomsStructures.STREET_XP_HOUSE
    );

    private BackroomsStructures.BackroomsStructureInfo rollStructure(List<BackroomsStructures.BackroomsStructureInfo> possibleStructures, WorldGenRegion genRegion)
    {
        int index = genRegion.getRandom().nextInt(0, possibleStructures.size());
        return possibleStructures.get(index);
    }

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {
        BRGenUtil.fillLayer(chunkAccess, 0, Blocks.BEDROCK);
        BRGenUtil.fillLayer(chunkAccess, 1, CodBlocks.MOIST_CARPET.get());


        boolean walls = true;
        boolean lights = true;
        if(chunkAccess.getPos().x % 100 == 0)
        {
            placeBackroomsStructure(BackroomsStructures.STREET_Z, worldGenRegion, chunkAccess.getPos().getWorldPosition().atY(1), worldGenRegion.getRandom());
            walls = false;
            lights = false;
        }

        if(chunkAccess.getPos().x % 100 == 1)
        {
            if(worldGenRegion.getRandom().nextInt(0,4) == 0)
            {
                BackroomsStructures.BackroomsStructureInfo structureInfo = rollStructure(ROAD_XP_STRUCTURES, worldGenRegion);
                placeBackroomsStructure(structureInfo, worldGenRegion, chunkAccess.getPos().getWorldPosition().atY(1), worldGenRegion.getRandom());
            } else {
                placeBackroomsStructure(BackroomsStructures.STREET_XP_EMPTY, worldGenRegion, chunkAccess.getPos().getWorldPosition().atY(1), worldGenRegion.getRandom());
            }
            walls = false;
            lights = false;
        }

        if(chunkAccess.getPos().x % 100 == 99)
        {
            placeBackroomsStructure(BackroomsStructures.STREET_XN_EMPTY, worldGenRegion, chunkAccess.getPos().getWorldPosition().atY(1), worldGenRegion.getRandom());
            walls = false;
            lights = false;
        }

        //placeNbtStructure(worldGenRegion, chunkAccess, chunkAccess.getPos().getWorldPosition().atY(2), worldGenRegion.getRandom());
        BRGenUtil.fillLayer(chunkAccess, 9, CodBlocks.CEILING_TILE.get());


        if(walls)
        {
            BRGenUtil.generateBasicWalls(chunkAccess, 1, 9, CodBlocks.UGLY_WALLPAPER.get());
        }

        if(lights)
        {
            BRGenUtil.generateLights(chunkAccess, 9);
        }
    }

    private void placeBackroomsStructure(BackroomsStructures.BackroomsStructureInfo structureInfo, WorldGenRegion world, BlockPos startPos, RandomSource random) {
        StructureTemplateManager templateManager = world.getLevel().getStructureManager();
        Optional<StructureTemplate> structureTemplate = templateManager.get(structureInfo.structure());

        if (structureTemplate.isPresent()) {
            StructureTemplate template = structureTemplate.get();
            StructurePlaceSettings placeSettings = new StructurePlaceSettings()
                    .setRandom(random)
                    .setIgnoreEntities(true);
            template.placeInWorld(world, startPos, startPos.offset(structureInfo.offset()), placeSettings, random, 2);
        }
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
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
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
    public int getBaseHeight(int i, int i1, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
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
