package com.mc3699.codmod.technology.foundation;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachineMenu extends AbstractContainerMenu {

    protected final BlockEntity blockEntity;
    protected final ContainerData data;
    protected final ContainerLevelAccess access;

    public BaseMachineMenu(@Nullable MenuType<?> menuType, int containerId, BlockEntity blockEntity, ContainerData data, ContainerLevelAccess access) {
        super(menuType, containerId);
        this.blockEntity = blockEntity;
        this.data = data;
        this.access = access;
        addDataSlots(data);
    }

    protected void addPlayerInventory(Inventory playerInv, int startX, int startY) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(playerInv, x + y * 9 + 9, startX + x * 18, startY + y * 18));
            }
        }
    }

    protected void addPlayerHotbar(Inventory playerInv, int startX, int y) {
        for (int x = 0; x < 9; x++) {
            addSlot(new Slot(playerInv, x, startX + x * 18, y));
        }
    }

    protected void addFullPlayerInventory(Inventory playerInv, int x, int y) {
        addPlayerInventory(playerInv, x, y);
        addPlayerHotbar(playerInv, x, y + 58);
    }

    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((level, pos) -> {
            BlockState state = level.getBlockState(pos);
            return level.getBlockEntity(pos) == blockEntity &&
                    player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
        }, true);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = getSlot(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack result = stack.copy();

        int containerSlots = slots.size() - 36;

        if (index < containerSlots) {
            if (!moveItemStackTo(stack, containerSlots, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(stack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return result;
    }
}
