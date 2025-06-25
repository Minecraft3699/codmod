package com.mc3699.codmod.block.opticalDriveInterface;

import com.mc3699.codmod.item.OpticalTapeDriveItem;
import com.mc3699.codmod.peripheral.OpticalDriveInterfacePeripheral;
import com.mc3699.codmod.registry.CodBlockEntities;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class OpticalDriveInterfaceBlockEntity extends BlockEntity {

    private ItemStackHandler inventory = new ItemStackHandler(1)
    {

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof OpticalTapeDriveItem;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(level != null)
            {
                BlockState state = level.getBlockState(getBlockPos());
                level.setBlock(getBlockPos(), state.setValue(OpticalDriveInterfaceBlock.HAS_DISK,!getStackInSlot(slot).isEmpty()), 3);
            }
        }
    };

    public void setDisk(ItemStack stack)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            inventory.setStackInSlot(0, stack);
            if(stack.isEmpty())
            {
                OpticalDriveInterfacePeripheral peripheral = (OpticalDriveInterfacePeripheral) serverLevel.getCapability(PeripheralCapability.get(), getBlockPos(), null);
                if(peripheral != null)
                {
                    peripheral.detachIfAttached();
                }
            }
        }

    }

    public ItemStack getDisk()
    {
        return inventory.getStackInSlot(0);
    }

    public OpticalDriveInterfaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(CodBlockEntities.OPTICAL_DRIVE_INTERFACE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
}
