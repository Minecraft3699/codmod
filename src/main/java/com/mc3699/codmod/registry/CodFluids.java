package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.tterrag.registrate.builders.FluidBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CodFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Codmod.MOD_ID);


    public static final BaseFlowingFluid.Properties BLOOD_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    CodFluidTypes.BLOOD,
                    CodFluids.BLOOD,
                    CodFluids.BLOOD_FLOWING
            );

    public static final Supplier<Fluid> BLOOD = FLUIDS.register("blood", () ->
            new BaseFlowingFluid.Source(BLOOD_PROPERTIES));

    public static final Supplier<Fluid> BLOOD_FLOWING = FLUIDS.register("blood_flowing", () ->
            new BaseFlowingFluid.Source(BLOOD_PROPERTIES));

    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}
