package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlock;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlockEntity;
import com.mc3699.codmod.item.OpticalTapeDriveItem;
import dan200.computercraft.api.filesystem.WritableMount;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class OpticalDriveInterfacePeripheral implements IPeripheral {

    private final OpticalDriveInterfaceBlockEntity drive;
    private IComputerAccess computer;

    public OpticalDriveInterfacePeripheral(OpticalDriveInterfaceBlockEntity drive) {
        this.drive = drive;
    }

    @Override
    public String getType() {
        return "optical_interface";
    }

    @Override
    public void attach(@NotNull IComputerAccess computer) {
        this.computer = computer;
        ItemStack disk = drive.getDisk();
        if (!disk.isEmpty() && disk.getItem() instanceof OpticalTapeDriveItem media && drive.getLevel() instanceof ServerLevel serverLevel) {
            try {
                computer.mount("optical", media.createDataMount(disk, serverLevel));
            } catch (Exception e) {
                // just fucking deal with it, I don't want to hear it if your mount fails
            }
        }
    }

    public void detachIfAttached() {
        if (computer != null) {
            computer.unmount("optical");
            computer = null;
        }
    }

    @Override
    public void detach(@NotNull IComputerAccess computer) {
        if(this.computer != null)
        {
            this.computer.unmount("optical");
        }

        this.computer = null;
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
