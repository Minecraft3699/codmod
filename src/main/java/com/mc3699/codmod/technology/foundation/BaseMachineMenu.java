package com.mc3699.codmod.technology.foundation;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class BaseMachineMenu extends AbstractContainerMenu {

    protected final BlockEntity blockEntity;
    protected final ContainerData data;
    protected final ContainerLevelAccess access;

    public BaseMachineMenu(@Nullable MenuType<?> menuType, int containerId, BlockEntity blockEntity, ContainerData data, ContainerLevelAccess access) {
        super(menuType, containerId);
        this.blockEntity = blockEntity;
        this.data = data;
        this.access = access;
        addDataSlots(data);;
    }

    protected void addPlayerInventory(Inventory playerInv) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for (int x = 0; x < 9; x++) {
            addSlot(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
