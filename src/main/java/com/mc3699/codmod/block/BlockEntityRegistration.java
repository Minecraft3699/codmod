package com.mc3699.codmod.block;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlock;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntityRegistration {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Codmod.MODID);

    public static final Supplier<BlockEntityType<MantleKeyBlockEntity>> MANTLE_KEY = BLOCK_ENTITIES.register(
            "mantle_key",
            () -> new BlockEntityType<>(
                    MantleKeyBlockEntity::new,
                    Set.of(BlockRegistration.MANTLE_KEY.get()),
                    null
            )
    );

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
