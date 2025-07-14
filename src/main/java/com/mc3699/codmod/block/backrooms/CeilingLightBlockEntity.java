package com.mc3699.codmod.block.backrooms;

import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CeilingLightBlockEntity extends BlockEntity {
    public CeilingLightBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.CEILING_LIGHT.get(), pos, blockState);
    }
}
