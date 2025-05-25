package com.mc3699.codmod.block;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlock;
import com.mc3699.codmod.item.ItemRegistration;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.server.command.ModIdArgument;

import java.util.function.Supplier;

public class BlockRegistration {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Codmod.MODID);

    public static final DeferredBlock<Block> MOLTEN_COPPER = BLOCKS.register(
            "molten_copper",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK))
    );

    public static final DeferredBlock<Block> DELL_SERVER  = BLOCKS.register(
            "dell_server",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS))
    );

    public static final DeferredBlock<Block> COD_BLOCK  = BLOCKS.register(
            "codblock",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );

    public static final DeferredBlock<MantleKeyBlock> MANTLE_KEY =
            BLOCKS.register("mantle_key", () -> new MantleKeyBlock(BlockBehaviour.Properties.of()));

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

}
