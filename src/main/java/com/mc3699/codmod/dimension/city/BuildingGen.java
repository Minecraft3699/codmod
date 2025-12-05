package com.mc3699.codmod.dimension.city;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.dimension.backrooms.BRGenUtil;
import com.mc3699.codmod.dimension.backrooms.BackroomsStructures;
import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

import static com.mc3699.codmod.dimension.backrooms.BRGenUtil.random;

public class BuildingGen {


    private static ResourceLocation floorStructure(String name) {
        return ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID,name);
    }

    private static final int streetSpacing = 16;

    private static final List<ResourceLocation> FLOOR_POOL = List.of(
            floorStructure("floor_corrupt"),
            floorStructure("floor_storage_a1"),
            floorStructure("floor_temptation")
    );

    public static void placeChest(WorldGenLevel serverLevel, int x, int y, int z, ResourceKey<LootTable> lootTable) {
        BlockPos pos = new BlockPos(x, y, z);
        serverLevel.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        if (blockEntity instanceof ChestBlockEntity chest) {
            chest.setLootTable(lootTable, random.nextLong());
        }
    }

    public static void placeRoads(WorldGenRegion worldGenRegion, ChunkAccess chunkAccess) {
        BRGenUtil.fillLayer(chunkAccess, 30, Blocks.GRASS_BLOCK.defaultBlockState());
        BRGenUtil.fillLayer(chunkAccess, 29, Blocks.BEDROCK.defaultBlockState());

        int cx = chunkAccess.getPos().x;
        int cz = chunkAccess.getPos().z;

        if (cx % streetSpacing == 0 && cz % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_INTERSECTION, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
            return;
        }
        if (cx % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_Z, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
            return;
        }
        if (cz % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_X, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
        }
    }

    public static void placeBuildings(WorldGenLevel worldGenRegion, ChunkAccess chunkAccess) {

        int buildingHeight = 31 + worldGenRegion.getRandom().nextInt(24, 128);

        if (worldGenRegion.getRandom().nextInt(0, 1000) == 1) {
            buildingHeight = 31 + worldGenRegion.getRandom().nextInt(128, 500);
        }

        int windowWidth = 2;
        int windowHeight = 2;
        int gapX = 1;
        int gapY = 3;
        int startY = 33;
        int endY = buildingHeight - 2;

        if (chunkAccess.getPos().x % streetSpacing == 0 && chunkAccess.getPos().z % streetSpacing == 0) return;
        if (chunkAccess.getPos().x % streetSpacing == 0) return;
        if (chunkAccess.getPos().z % streetSpacing == 0) return;
        if (worldGenRegion.getRandom().nextInt(1, 5) == 3) return;

        BlockState[] wallChoices = new BlockState[]{Blocks.STONE_BRICKS.defaultBlockState(), Blocks.DEEPSLATE_BRICKS.defaultBlockState(), Blocks.BRICKS.defaultBlockState(), Blocks.TUFF_BRICKS.defaultBlockState(),};
        BlockState wallMaterial = wallChoices[worldGenRegion.getRandom().nextInt(wallChoices.length)];

        BlockState[] glassChoices = new BlockState[]{Blocks.WHITE_STAINED_GLASS.defaultBlockState(), Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState(), Blocks.GRAY_STAINED_GLASS.defaultBlockState(), Blocks.BLACK_STAINED_GLASS.defaultBlockState(), Blocks.GLASS.defaultBlockState(),};
        BlockState glassMaterial = glassChoices[worldGenRegion.getRandom().nextInt(glassChoices.length)];

        BRGenUtil.fillArea(chunkAccess, 1, 31, 1, 14, buildingHeight, 1, wallMaterial);
        BRGenUtil.fillArea(chunkAccess, 1, 31, 14, 14, buildingHeight, 14, wallMaterial);
        BRGenUtil.fillArea(chunkAccess, 1, 31, 1, 1, buildingHeight, 14, wallMaterial);
        BRGenUtil.fillArea(chunkAccess, 14, 31, 1, 14, buildingHeight, 14, wallMaterial);

        int minX = 2;
        int maxX = 13;
        int minZ = 2;
        int maxZ = 13;

        int roofY = buildingHeight + 1;
        BRGenUtil.fillArea(chunkAccess, 1, roofY, 1, 14, roofY, 14, wallMaterial);

        generateWindows(chunkAccess, glassMaterial, minX, maxX, minZ, maxZ, startY, endY, windowWidth, windowHeight, gapX, gapY);
        generateFloors(chunkAccess, minX, maxX, minZ, maxZ, startY, endY, windowWidth, windowHeight, gapY, worldGenRegion);
    }

    private static void generateWindows(ChunkAccess chunkAccess, BlockState glassMaterial, int minX, int maxX, int minZ, int maxZ, int startY, int endY, int windowWidth, int windowHeight, int gapX, int gapY) {
        for (int y = startY; y <= endY; y += windowHeight + gapY) {

            int leftX = minX;
            int rightX = maxX - windowWidth + 1;
            while (leftX <= rightX) {
                int leftStart = leftX;
                int leftEnd = leftStart + windowWidth - 1;
                BRGenUtil.fillArea(chunkAccess, leftStart, y, minZ - 1, leftEnd, y + windowHeight - 1, minZ - 1, glassMaterial);
                BRGenUtil.fillArea(chunkAccess, leftStart, y, maxZ + 1, leftEnd, y + windowHeight - 1, maxZ + 1, glassMaterial);
                if (leftStart == rightX) break;
                int rightStart = rightX;
                int rightEnd = rightStart + windowWidth - 1;
                if (rightStart > leftEnd) {
                    BRGenUtil.fillArea(chunkAccess, rightStart, y, minZ - 1, rightEnd, y + windowHeight - 1, minZ - 1, glassMaterial);
                    BRGenUtil.fillArea(chunkAccess, rightStart, y, maxZ + 1, rightEnd, y + windowHeight - 1, maxZ + 1, glassMaterial);
                }
                leftX += windowWidth + gapX;
                rightX -= windowWidth + gapX;
            }

            int leftZ = minZ;
            int rightZ = maxZ - windowWidth + 1;
            while (leftZ <= rightZ) {
                int leftStart = leftZ;
                int leftEnd = leftStart + windowWidth - 1;
                BRGenUtil.fillArea(chunkAccess, minX - 1, y, leftStart, minX - 1, y + windowHeight - 1, leftEnd, glassMaterial);
                BRGenUtil.fillArea(chunkAccess, maxX + 1, y, leftStart, maxX + 1, y + windowHeight - 1, leftEnd, glassMaterial);
                if (leftStart == rightZ) break;
                int rightStart = rightZ;
                int rightEnd = rightStart + windowWidth - 1;
                if (rightStart > leftEnd) {
                    BRGenUtil.fillArea(chunkAccess, minX - 1, y, rightStart, minX - 1, y + windowHeight - 1, rightEnd, glassMaterial);
                    BRGenUtil.fillArea(chunkAccess, maxX + 1, y, rightStart, maxX + 1, y + windowHeight - 1, rightEnd, glassMaterial);
                }
                leftZ += windowWidth + gapX;
                rightZ -= windowWidth + gapX;
            }
        }
    }

    private static void generateFloors(ChunkAccess chunkAccess, int minX, int maxX, int minZ, int maxZ, int startY, int endY, int windowWidth, int windowHeight, int gapY, WorldGenLevel region) {
        int hallwayWidth = 2;
        int hallwayXMin = (minX + maxX) / 2 - hallwayWidth / 2;
        int hallwayXMax = (minX + maxX) / 2 + hallwayWidth / 2;
        int hallwayZMin = (minZ + maxZ) / 2 - hallwayWidth / 2;
        int hallwayZMax = (minZ + maxZ) / 2 + hallwayWidth / 2;

        for (int y = startY; y <= endY; y += windowHeight + gapY) {
            int floorY = y - 1;
            BRGenUtil.fillArea(chunkAccess, minX, floorY - 1, minZ, maxX, floorY - 1, maxZ, Blocks.OAK_PLANKS.defaultBlockState());


            for (int i = 0; i <= 8; i++) {
                BRGenUtil.generateBuildingInteriorWalls(chunkAccess, floorY - 1, floorY + 4, CodBlocks.UGLY_WALLPAPER.getDefaultState());
                BRGenUtil.fillArea(chunkAccess, hallwayXMin, floorY, hallwayZMin, hallwayXMax, floorY + 3, hallwayZMax, Blocks.AIR.defaultBlockState());
            }


            if (random.nextInt(1, 20) == 1) {
                BlockPos chunkOrigin = chunkAccess.getPos().getWorldPosition();
                BlockPos worldStart = chunkOrigin.offset(minX, floorY - 1, minZ);

                ResourceLocation randomFloor = FLOOR_POOL.get(random.nextInt(0, FLOOR_POOL.size()));
                BRGenUtil.placeBuildingFloor(randomFloor, region, worldStart);
            }
            BlockState ladder = Blocks.LADDER.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
            BRGenUtil.fillArea(chunkAccess, minX + 5, floorY - 1, minZ, minX + 6, floorY + 3, minZ, ladder);
        }
    }
}
