package com.mc3699.codmod.block.radar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RadarBlock extends Block implements EntityBlock {
    public RadarBlock() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RadarBlockEntity(blockPos,blockState);
    }
}
