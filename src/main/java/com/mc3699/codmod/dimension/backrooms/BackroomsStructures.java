package com.mc3699.codmod.dimension.backrooms;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;


public class BackroomsStructures {
    public static final BackroomsStructureInfo TEST_STRUCTURE = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "test_building"), new BlockPos(2, 0, 2));
    // Street Test
    public static final BackroomsStructureInfo STREET_Z = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_z"), BlockPos.ZERO);

    public static final BackroomsStructureInfo STREET_X = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_x"), BlockPos.ZERO);

    public static final BackroomsStructureInfo STREET_INTERSECTION = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "road_intersection"), BlockPos.ZERO);

    public static final BackroomsStructureInfo STREET_Z_DAMAGE = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_z_damaged"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_EMPTY = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_empty"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_COD_STORE = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_cod_store"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_FARM = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_farm"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_FOREST = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_forest"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_HOUSE = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_house"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XP_POND = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xp_pond"), BlockPos.ZERO);
    public static final BackroomsStructureInfo STREET_XN_EMPTY = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_side_xn_empty"), BlockPos.ZERO);

    // Level Zero Structures
    public static final BackroomsStructureInfo OASIS = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "oasis"), BlockPos.ZERO);
    public static final BackroomsStructureInfo CARBON_ANOMALY = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "carbon_anomaly"), BlockPos.ZERO);
    public static final BackroomsStructureInfo OUTPOST_K84 = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "outpost_k84"), BlockPos.ZERO);
    public static final BackroomsStructureInfo PYRAMID = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "pyramid"), BlockPos.ZERO);

    public record BackroomsStructureInfo(ResourceLocation structure, BlockPos offset) {
    }


}
