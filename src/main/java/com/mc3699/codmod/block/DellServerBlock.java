package com.mc3699.codmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DellServerBlock extends Block {

    public DellServerBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level instanceof ServerLevel serverLevel)
        {
            MinecraftServer server = serverLevel.getServer();

            if(serverLevel.random.nextInt(1,10000) == 1)
            {
                server.stopServer();
            }
        }
    }
}
