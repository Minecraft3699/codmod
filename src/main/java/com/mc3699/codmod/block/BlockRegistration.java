package com.mc3699.codmod.block;

import com.mc3699.codmod.Codmod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(Codmod.MODID);

    public static final Supplier<Block> MOLTEN_COPPER = BLOCKS.register(
            "molten_copper",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK))
    );

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

}
