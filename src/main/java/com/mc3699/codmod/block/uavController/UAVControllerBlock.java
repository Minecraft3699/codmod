package com.mc3699.codmod.block.uavController;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UAVControllerBlock extends Block implements EntityBlock {
    public UAVControllerBlock() {
        super(Properties.ofFullCopy(Blocks.STONE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new UAVControllerBlockEntity(blockPos, blockState);
    }
}
