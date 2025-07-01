package com.mc3699.codmod.peripheral;

import com.mc3699.codmod.registry.CodBlocks;
import com.mc3699.codmod.registry.CodItems;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class HubPeripheral implements IPeripheral {

    private IPocketAccess pocketAccess;

    public HubPeripheral(IPocketAccess access)
    {
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
    public boolean test()
    {
        ItemStack stack = new ItemStack(CodBlocks.RADAR.get());
        if(stack.getItem() instanceof IPeripheral peripheral)
        {
            peripheral.attach((IComputerAccess) pocketAccess);
            return true;
        }
        return false;
    }

}
