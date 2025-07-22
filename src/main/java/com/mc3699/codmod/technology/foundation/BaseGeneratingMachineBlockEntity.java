package com.mc3699.codmod.technology.foundation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BaseGeneratingMachineBlockEntity extends BaseMachineBlockEntity implements IGeneratingMachine {
    public BaseGeneratingMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int maxEnergy, int slots) {
        super(type, pos, blockState, maxEnergy, slots);
    }

    public static <T extends BlockEntity> void energyExportTick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        for(Direction direction : Direction.values())
        {

            BaseGeneratingMachineBlockEntity self = (BaseGeneratingMachineBlockEntity) t;

            BlockEntity neighbor = level.getBlockEntity(blockPos.relative(direction));
            if (neighbor == null) continue;

            IEnergyStorage target = null;

            if (neighbor instanceof BaseCableBlockEntity neighborCable) {
                target = neighborCable.getEnergyCap(neighbor, direction.getOpposite());
            } else if (neighbor instanceof BaseMachineBlockEntity machineBlockEntity) {
                target = machineBlockEntity.getEnergyCap(machineBlockEntity, direction.getOpposite());
            }

            if (target != null && target.canReceive()) {
                int extractSimulated = self.energyStorage.extractEnergy(1000, true);
                if (extractSimulated > 0) {
                    int accepted = target.receiveEnergy(extractSimulated, false);
                    self.energyStorage.extractEnergy(accepted, false);
                }
            }
        }
    }

    @Override
    public int getEnergyProduction() {
        return 0;
    }

    @Override
    public boolean canProduce() {
        return false;
    }
}
