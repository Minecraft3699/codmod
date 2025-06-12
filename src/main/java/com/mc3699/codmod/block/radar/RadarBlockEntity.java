package com.mc3699.codmod.block.radar;

import com.mc3699.codmod.block.BlockEntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RadarBlockEntity extends BlockEntity {
    public RadarBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistration.RADAR.get(), pos, blockState);
    }
}
