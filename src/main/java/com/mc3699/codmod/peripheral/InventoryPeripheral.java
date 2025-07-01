package com.mc3699.codmod.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import org.jspecify.annotations.Nullable;

public class InventoryPeripheral implements IPeripheral {
    @Override
    public String getType() {
        return "inventory";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
