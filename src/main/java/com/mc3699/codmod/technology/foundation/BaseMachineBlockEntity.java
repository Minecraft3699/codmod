package com.mc3699.codmod.technology.foundation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BaseMachineBlockEntity extends BlockEntity implements IEnergyStorage {

    protected final EnergyStorage energyStorage;
    protected final ContainerData data;
    protected final ItemStackHandler itemStackHandler;
    private final Lazy<IItemHandler> itemHandlerLazy;

    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int maxEnergy, int slots) {
        super(type, pos, blockState);
        this.energyStorage = new EnergyStorage(maxEnergy);
        this.data = new SimpleContainerData(16);

        this.itemStackHandler = new ItemStackHandler(slots)
        {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.itemHandlerLazy = Lazy.of(() -> itemStackHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("energy",energyStorage.serializeNBT(registries));
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energyStorage.deserializeNBT(registries, tag.getCompound("energy"));
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        data.set(0, energyStorage.getEnergyStored());
    }

    @Override
    public int receiveEnergy(int i, boolean b) {
        return energyStorage.receiveEnergy(i, b);
    }

    @Override
    public int extractEnergy(int i, boolean b) {
        return energyStorage.extractEnergy(i,b);
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public @Nullable IItemHandler getItemCap(BlockEntity o, Direction direction) {
        return itemStackHandler;
    }

    public @Nullable IEnergyStorage getEnergyCap(BlockEntity o, Direction direction)
    {
        return energyStorage;
    }
}
