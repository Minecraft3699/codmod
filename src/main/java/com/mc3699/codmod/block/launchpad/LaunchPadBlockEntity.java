package com.mc3699.codmod.block.launchpad;

import com.mc3699.codmod.block.BlockEntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class LaunchPadBlockEntity extends BlockEntity {

    public static final int SIZE = 27;
    private ItemStackHandler items = new ItemStackHandler(SIZE)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(level != null)
            {
                level.invalidateCapabilities(worldPosition);
            }
            super.onContentsChanged(slot);
        }
    };

    public LaunchPadBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistration.LAUNCH_PAD.get(), pos, blockState);
    }

    public ItemStackHandler getItems()
    {
        return items;
    }

    public void clearItems()
    {
        items = new ItemStackHandler(SIZE);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",items.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items.deserializeNBT(registries, tag.getCompound("inventory"));
    }
}
