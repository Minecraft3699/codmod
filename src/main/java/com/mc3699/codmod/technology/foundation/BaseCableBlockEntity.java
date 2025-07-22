package com.mc3699.codmod.technology.foundation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class BaseCableBlockEntity extends BlockEntity {

    private final IEnergyStorage energyStorage;
    private final int maxTransfer = 100;

    public BaseCableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.energyStorage = new EnergyStorage(1000);
    }

    public int getMaxTransfer() {
        return maxTransfer;
    }

    public @Nullable IEnergyStorage getEnergyCap(BlockEntity o, Direction direction)
    {
        return energyStorage;
    }

    public static <T extends BlockEntity> void transferTick(Level level, BlockPos pos, BlockState state, T t) {
        if (!(level instanceof ServerLevel)) return;
        if (!(t instanceof BaseCableBlockEntity self)) return;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockEntity neighbor = level.getBlockEntity(neighborPos);
            if (!(neighbor instanceof BaseCableBlockEntity other) || neighbor.isRemoved()) continue;

            int selfEnergy = self.energyStorage.getEnergyStored();
            int otherEnergy = other.energyStorage.getEnergyStored();

            if (Math.abs(selfEnergy - otherEnergy) < 2) continue;

            if (selfEnergy > otherEnergy) {
                int toSend = (selfEnergy - otherEnergy) / 2;
                int extracted = self.energyStorage.extractEnergy(toSend, true);
                int received = other.energyStorage.receiveEnergy(extracted, true);

                if (received > 0) {
                    self.energyStorage.extractEnergy(received, false);
                    other.energyStorage.receiveEnergy(received, false);
                }
                } else {
                int toSend = (otherEnergy - selfEnergy) / 2;
                int extracted = other.energyStorage.extractEnergy(toSend, true);
                int received = self.energyStorage.receiveEnergy(extracted, true);

                if (received > 0) {
                    other.energyStorage.extractEnergy(received, false);
                    self.energyStorage.receiveEnergy(received, false);
                }
            }
        }
    }


        
    }
