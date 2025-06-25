package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CodComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Codmod.MOD_ID);

    public static final Supplier<DataComponentType<String>> DISK_LABEL = COMPONENTS.register(
            "disk_label",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build()
    );

    public static final Supplier<DataComponentType<Long>> DISK_SIZE = COMPONENTS.register(
            "disk_size",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .build()
    );

    public static void register(IEventBus eventBus)
    {
        COMPONENTS.register(eventBus);
    }

}
