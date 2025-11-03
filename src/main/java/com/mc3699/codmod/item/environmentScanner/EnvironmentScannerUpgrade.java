package com.mc3699.codmod.item.environmentScanner;

import com.mc3699.codmod.peripheral.EnvironmentScannerPeripheral;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodTurtleUpgrades;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class EnvironmentScannerUpgrade extends AbstractTurtleUpgrade {

    public EnvironmentScannerUpgrade() {
        super(TurtleUpgradeType.PERIPHERAL, Component.literal("Scanning"), new ItemStack(CodItems.ENVIRONMENT_SCANNER.get()));
    }

    @Override
    public @Nullable IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new EnvironmentScannerPeripheral(turtle);
    }

    @Override
    public UpgradeType<? extends ITurtleUpgrade> getType() {
        return CodTurtleUpgrades.ENVIRONMENT_SCANNER.get();
    }
}
