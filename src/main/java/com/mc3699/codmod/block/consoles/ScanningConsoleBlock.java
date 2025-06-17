package com.mc3699.codmod.block.consoles;

import com.mc3699.codmod.consoles.BaseConsoleBlock;
import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ScanningConsoleBlock extends BaseConsoleBlock {
    public ScanningConsoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScanningConsoleBlockEntity(CodBlockEntities.SCANNING_CONSOLE.get(), blockPos, blockState);
    }
}
