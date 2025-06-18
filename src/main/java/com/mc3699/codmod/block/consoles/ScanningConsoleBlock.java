package com.mc3699.codmod.block.consoles;

import com.mc3699.codmod.consoles.BaseConsoleBlock;
import com.mc3699.codmod.registry.CodBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ScanningConsoleBlock extends BaseConsoleBlock {
    public ScanningConsoleBlock(Properties properties) {
        super(properties.noOcclusion().lightLevel((state)->5));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScanningConsoleBlockEntity(CodBlockEntities.SCANNING_CONSOLE.get(), blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(-1,0,0,1,1,1);
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.box(-1,0,0,1,1,1);
    }
}
