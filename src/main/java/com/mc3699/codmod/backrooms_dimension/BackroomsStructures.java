package com.mc3699.codmod.backrooms_dimension;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;


public class BackroomsStructures {
    public static final BackroomsStructureInfo TEST_STRUCTURE = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "test_building"), new BlockPos(2, 0, 2));
    public static final BackroomsStructureInfo STREET_Z = new BackroomsStructureInfo(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "street_z"), BlockPos.ZERO);
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

    public record BackroomsStructureInfo(ResourceLocation structure, BlockPos offset) {
    }


}
