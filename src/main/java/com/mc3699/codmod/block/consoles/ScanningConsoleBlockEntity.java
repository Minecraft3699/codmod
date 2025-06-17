package com.mc3699.codmod.block.consoles;

import com.mc3699.codmod.consoles.BaseConsoleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ScanningConsoleBlockEntity extends BaseConsoleBlockEntity {
    public ScanningConsoleBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState blockState
    ) {
        super(type, pos, blockState);
    }
}
