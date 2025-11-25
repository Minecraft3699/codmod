package com.mc3699.codmod.dimension.city;

import com.mc3699.codmod.dimension.backrooms.BRGenUtil;
import com.mc3699.codmod.dimension.backrooms.BackroomsStructures;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class BuildingGen {

    private static final int streetSpacing = 8;

    public static void placeRoads(WorldGenRegion worldGenRegion, ChunkAccess chunkAccess) {
        BRGenUtil.fillLayer(chunkAccess, 30, Blocks.GRASS_BLOCK.defaultBlockState());
        if (chunkAccess.getPos().x % streetSpacing == 0 && chunkAccess.getPos().z % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_INTERSECTION, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
            return;
        }
        if (chunkAccess.getPos().x % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_Z, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
            return;
        }
        if (chunkAccess.getPos().z % streetSpacing == 0) {
            BRGenUtil.placeBackroomsStructure(BackroomsStructures.STREET_X, worldGenRegion, chunkAccess.getPos().getWorldPosition().above(30), worldGenRegion.getRandom());
        }
    }

    public static void placeBuildings(WorldGenRegion worldGenRegion, ChunkAccess chunkAccess) {

        int buildingHeight = 31 + worldGenRegion.getRandom().nextInt(24, 128);
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

        BlockState[] wallChoices = new BlockState[] {
                Blocks.STONE_BRICKS.defaultBlockState(),
                Blocks.DEEPSLATE_BRICKS.defaultBlockState(),
                Blocks.BRICKS.defaultBlockState(),
                Blocks.TUFF_BRICKS.defaultBlockState()
        };
        BlockState wallMaterial = wallChoices[worldGenRegion.getRandom().nextInt(wallChoices.length)];

        BlockState[] glassChoices = new BlockState[]{
                Blocks.WHITE_STAINED_GLASS.defaultBlockState(),
                Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState(),
                Blocks.GRAY_STAINED_GLASS.defaultBlockState(),
                Blocks.BLACK_STAINED_GLASS.defaultBlockState(),
                Blocks.GLASS.defaultBlockState()
        };
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

        if (worldGenRegion.getRandom().nextInt(4) == 0) {
            int spireX = 1 + worldGenRegion.getRandom().nextInt(14 - 1);
            int spireZ = 1 + worldGenRegion.getRandom().nextInt(14 - 1);
        }

        for (int y = startY; y <= endY; y += windowHeight + gapY) {

            int left = minX;
            int right = maxX - windowWidth + 1;

            while (left <= right) {

                int leftStart = left;
                int leftEnd = leftStart + windowWidth - 1;

                BRGenUtil.fillArea(chunkAccess, leftStart, y, 1, leftEnd, y + windowHeight - 1, 1, glassMaterial);
                BRGenUtil.fillArea(chunkAccess, leftStart, y, 14, leftEnd, y + windowHeight - 1, 14, glassMaterial);

                if (leftStart == right) break;

                int rightStart = right;
                int rightEnd = rightStart + windowWidth - 1;

                if (rightStart > leftEnd) {
                    BRGenUtil.fillArea(chunkAccess, rightStart, y, 1, rightEnd, y + windowHeight - 1, 1, glassMaterial);
                    BRGenUtil.fillArea(chunkAccess, rightStart, y, 14, rightEnd, y + windowHeight - 1, 14, glassMaterial);
                }

                left += windowWidth + gapX;
                right -= windowWidth + gapX;
            }
        }

        for (int y = startY; y <= endY; y += windowHeight + gapY) {

            int floorY = y - 1;

            BRGenUtil.fillArea(chunkAccess, minX, floorY - 1, minZ, maxX, floorY - 1, maxZ, Blocks.OAK_PLANKS.defaultBlockState());

            int left = minZ;
            int right = maxZ - windowWidth + 1;

            while (left <= right) {

                int leftStart = left;
                int leftEnd = leftStart + windowWidth - 1;

                BRGenUtil.fillArea(chunkAccess, 1, y, leftStart, 1, y + windowHeight - 1, leftEnd, glassMaterial);
                BRGenUtil.fillArea(chunkAccess, 14, y, leftStart, 14, y + windowHeight - 1, leftEnd, glassMaterial);

                if (leftStart == right) break;

                int rightStart = right;
                int rightEnd = rightStart + windowWidth - 1;

                if (rightStart > leftEnd) {
                    BRGenUtil.fillArea(chunkAccess, 1, y, rightStart, 1, y + windowHeight - 1, rightEnd, glassMaterial);
                    BRGenUtil.fillArea(chunkAccess, 14, y, rightStart, 14, y + windowHeight - 1, rightEnd, glassMaterial);
                }

                left += windowWidth + gapX;
                right -= windowWidth + gapX;
            }
        }
    }

}
