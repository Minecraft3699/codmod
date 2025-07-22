package com.mc3699.codmod.technology.lowVoltageCable;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.technology.foundation.BaseCableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LowVoltageCableBlockEntity extends BaseCableBlockEntity {
    public LowVoltageCableBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.LOW_VOLTAGE_CABLE.get(), pos, blockState);
    }

    @Override
    public int getMaxTransfer() {
        return 20;
    }
}
