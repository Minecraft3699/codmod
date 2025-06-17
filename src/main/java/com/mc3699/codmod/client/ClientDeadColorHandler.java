package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.colors.ColorManager;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientDeadColorHandler {

    @SubscribeEvent
    public static void registerColorHandler(RegisterColorHandlersEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register(
                (state, world, pos, tintIndex) ->
                        state.getBlock() instanceof LeavesBlock ?
                        ColorManager.getFoliageColor(state, world, pos, tintIndex) :
                        ColorManager.getGrassColor(state, world, pos, tintIndex),
                Blocks.GRASS_BLOCK, Blocks.GRASS_BLOCK, Blocks.FERN,
                Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LEAVES,
                Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES,
                Blocks.TALL_GRASS, Blocks.SHORT_GRASS
        );
    }

}
