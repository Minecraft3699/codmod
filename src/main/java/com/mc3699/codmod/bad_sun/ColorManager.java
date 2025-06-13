package com.mc3699.codmod.bad_sun;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class ColorManager {
    private static int foliageColor = 0x48B518; // Fallback color
    private static int grassColor = 0x91BD59;  // Fallback color
    private static boolean useCustomFoliage = false;
    private static boolean useCustomGrass = false;

    public static void setFoliageColor(int color) {
        foliageColor = color;
        useCustomFoliage = true;
    }

    public static void setGrassColor(int color) {
        grassColor = color;
        useCustomGrass = true;
    }

    public static int getFoliageColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        if (useCustomFoliage || world == null || pos == null) {
            return foliageColor;
        }
        return BiomeColors.getAverageFoliageColor(world, pos); // Vanilla biome-based color
    }

    public static int getGrassColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        if (useCustomGrass || world == null || pos == null) {
            return grassColor;
        }
        return BiomeColors.getAverageGrassColor(world, pos); // Vanilla biome-based color
    }

    public static void resetFoliageColor() {
        useCustomFoliage = false;
    }

    public static void resetGrassColor() {
        useCustomGrass = false;
    }
}
