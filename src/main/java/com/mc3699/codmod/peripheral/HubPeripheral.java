package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.registry.CodBlocks;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class HubPeripheral implements IPeripheral {

    private final IPocketAccess pocketAccess;

    public HubPeripheral(IPocketAccess access) {
        this.pocketAccess = access;
    }

    @Override
    public String getType() {
        return "hub";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return false;
    }

    @LuaFunction
    public boolean test() {
        ItemStack stack = new ItemStack(CodBlocks.RADAR.get());
        if (stack.getItem() instanceof IPeripheral peripheral) {
            peripheral.attach((IComputerAccess) pocketAccess);
            return true;
        }
        return false;
    }

}
