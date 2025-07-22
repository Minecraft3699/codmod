package com.mc3699.codmod.technology.coalGenerator;

import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.technology.foundation.BaseMachineBlockEntity;
import com.mc3699.codmod.technology.foundation.IGeneratingMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
public class CoalGeneratorBlockEntity extends BaseMachineBlockEntity implements IGeneratingMachine {

    private int burnTime = 0;

    public CoalGeneratorBlockEntity( BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.COAL_GENERATOR.get(), pos, blockState, 1000, 2);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if(level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(blockPos) instanceof CoalGeneratorBlockEntity coalGen)
        {
            if(coalGen.canProduce() && coalGen.burnTime > 0)
            {
                coalGen.energyStorage.receiveEnergy(coalGen.getEnergyProduction(), false);
                coalGen.burnTime--;
                coalGen.setChanged();
            } else {
                if(coalGen.canProduce())
                {
                    coalGen.getItemStackHandler().getStackInSlot(0).consume(1, null);
                    coalGen.burnTime = 80;
                    coalGen.setChanged();
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("burnTime", burnTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        burnTime = tag.getInt("burnTime");
    }

    @Override
    public int getEnergyProduction() {
        return 2;
    }

    @Override
    public boolean canProduce() {
        return (getItemStackHandler().getStackInSlot(0).getItem() == Items.COAL  || burnTime > 0) && getEnergyStored() != getMaxEnergyStored();
    }
}
