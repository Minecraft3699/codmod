package com.mc3699.codmod.block.teleporter;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TeleporterBlockEntity extends BlockEntity {
    public TeleporterBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.TELEPORTER.get(), pos, blockState);
    }
}
