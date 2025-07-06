package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.backrooms_dimension.BackroomsGenerator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodGenerators {

    private static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, Codmod.MOD_ID);

    public static void register(IEventBus eventBus) {
        CHUNK_GENERATORS.register("backrooms_generator", () -> BackroomsGenerator.CODEC);
        CHUNK_GENERATORS.register(eventBus);
    }
}