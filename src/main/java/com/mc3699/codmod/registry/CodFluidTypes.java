package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CodFluidTypes {

    public static final DeferredRegister<FluidType> FLUIDS = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Codmod.MOD_ID);


    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }

}
