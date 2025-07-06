package com.mc3699.codmod.backrooms_dimension;

import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

import java.util.Random;

public class BRGenUtil {

    public static Random random = new Random();

    public static void fillArea(
            ChunkAccess chunk,
            int startX,
            int startY,
            int startZ,
            int endX,
            int endY,
            int endZ,
            Block block
    ) {

        int minX = Math.min(startX, endX);
        int minY = Math.min(startY, endY);
        int minZ = Math.min(startZ, endZ);
        int maxX = Math.max(startX, endX);
        int maxY = Math.max(startY, endY);
        int maxZ = Math.max(startZ, endZ);

        BlockState blockState = block.defaultBlockState();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    chunk.setBlockState(pos, blockState, true);
                }
            }
        }
    }


    public static void fillLayer(ChunkAccess chunk, int layer, Block block) {
        fillArea(chunk, 0, layer, 0, 15, layer, 15, block);
        chunk.initializeLightSources();
    }


    public static void generateBasicWalls(ChunkAccess chunk, int floorLevel, int ceilingLevel, Block block) {
        int startX = random.nextInt(0, 15);
        int startZ = random.nextInt(0, 15);
        int length = random.nextInt(5, 15);
        int wallThickness = random.nextInt(0, 4);

        if (random.nextBoolean()) {
            fillArea(
                    chunk,
                    startX,
                    floorLevel + 1,
                    startZ,
                    startX + length,
                    ceilingLevel - 1,
                    startZ + wallThickness,
                    block
            );
        } else {
            fillArea(
                    chunk,
                    startX,
                    floorLevel + 1,
                    startZ,
                    startX + wallThickness,
                    ceilingLevel - 1,
                    startZ + length,
                    block
            );
        }

    }

    public static void generateLights(ChunkAccess chunk, int layer) {
        ChunkPos chunkPos = chunk.getPos();
        for (int x = 0; x < 15; x = x + 4) {
            for (int z = 0; z < 15; z = z + 4) {
                chunk.setBlockState(
                        chunkPos.getWorldPosition().offset(x, layer, z),
                        CodBlocks.CEILING_LIGHT.getDefaultState(),
                        true
                );
            }
        }
    }

    public static void generateBasicWallsWithExtension(
            ChunkAccess chunk,
            int floorLevel,
            int ceilingLevel,
            Block block,
            int extensionOffset,
            Block extensionBlock
    ) {
        int startX = random.nextInt(0, 15);
        int startZ = random.nextInt(0, 15);
        int length = random.nextInt(5, 15);
        int wallThickness = random.nextInt(0, 4);

        if (random.nextBoolean()) {
            fillArea(
                    chunk,
                    startX,
                    floorLevel + 1,
                    startZ,
                    startX + length,
                    ceilingLevel - 1,
                    startZ + wallThickness,
                    block
            );
            fillArea(
                    chunk,
                    startX,
                    ceilingLevel + 1,
                    startZ,
                    startX + length,
                    ceilingLevel + extensionOffset,
                    startZ + wallThickness,
                    extensionBlock
            );
        } else {
            fillArea(
                    chunk,
                    startX,
                    floorLevel + 1,
                    startZ,
                    startX + wallThickness,
                    ceilingLevel - 1,
                    startZ + length,
                    block
            );
            fillArea(
                    chunk,
                    startX,
                    ceilingLevel + 1,
                    startZ,
                    startX + wallThickness,
                    ceilingLevel + extensionOffset,
                    startZ + length,
                    extensionBlock
            );
        }

    }

    public static boolean isChunkInNoise(int chunkX, int chunkZ, PerlinNoise noise, double threshold) {
        double scale = 0.1;
        double noiseValue = noise.getValue(chunkX * scale, chunkZ * scale, 0.0);
        return noiseValue > threshold;
    }

    public static void fillWall(
            ChunkAccess chunk,
            int startX,
            int startZ,
            int endX,
            int endZ,
            int startLayer,
            int endLayer,
            Block block
    ) {

        for (int height = startLayer; height < endLayer + 1; height++) {
            for (int ix = startX; ix < endX; ix++) {
                for (int iz = startZ; iz < endZ; iz++) {
                    chunk.setBlockState(
                            chunk.getPos().getWorldPosition().offset(ix, height, iz),
                            block.defaultBlockState(),
                            true
                    );
                }
            }
        }
    }

}
