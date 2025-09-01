package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.phys.Vec3;
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

    public static final Supplier<DataComponentType<String>> TRANSPONDER_ID = COMPONENTS.register(
            "transponder_id",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build()
    );

    public static final Supplier<DataComponentType<BlockPos>> BLOCK_POSITION = COMPONENTS.register(
            "position",
            () -> DataComponentType.<BlockPos>builder()
                    .persistent(BlockPos.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> CHANNEL = COMPONENTS.register(
            "channel",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> OXYGEN = COMPONENTS.register(
            "stored_oxygen",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> SAUSAGE_COLOR = COMPONENTS.register(
            "sausage_color",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
                    .build()
    );

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }

}
