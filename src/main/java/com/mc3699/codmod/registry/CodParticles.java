package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CodParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Codmod.MOD_ID);

    public static final Supplier<SimpleParticleType> BLOOD_PARTICLE =
            PARTICLES.register("blood", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SPACE_TIME_PARTICLE =
            PARTICLES.register("space_time", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus)
    {
        PARTICLES.register(eventBus);
    }
}
