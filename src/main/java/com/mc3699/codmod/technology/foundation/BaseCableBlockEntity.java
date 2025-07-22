package com.mc3699.codmod.technology.foundation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class BaseCableBlockEntity extends BlockEntity {

    private final IEnergyStorage energyStorage;

    public BaseCableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.energyStorage = new EnergyStorage(100);
    }

    public @Nullable IEnergyStorage getEnergyCap(BlockEntity o, Direction direction)
    {
        return energyStorage;
    }

    public static <T extends BlockEntity> void transferTick(Level level, BlockPos blockPos, BlockState blockState, T t) {



    }
        
    }
