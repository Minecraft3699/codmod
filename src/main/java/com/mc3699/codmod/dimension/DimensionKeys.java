package com.mc3699.codmod.dimension;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;


public class DimensionKeys {
    public static final ResourceKey<Level> ENTROPY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "entropy"));
    public static final ResourceKey<Level> BOREAS = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "boreas"));

}
