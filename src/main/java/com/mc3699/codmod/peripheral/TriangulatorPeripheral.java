package com.mc3699.codmod.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;

import javax.annotation.Nullable;

public class TriangulatorPeripheral implements IPeripheral {

    @Override
    public String getType() {
        return "triangulator";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }
}
