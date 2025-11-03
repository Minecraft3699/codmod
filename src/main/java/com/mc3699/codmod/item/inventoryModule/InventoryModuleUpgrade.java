package com.mc3699.codmod.item.inventoryModule;

import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodPeripheralUpgradeTypes;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class InventoryModuleUpgrade implements IPocketUpgrade {
    @Override
    public UpgradeType<? extends IPocketUpgrade> getType() {
        return CodPeripheralUpgradeTypes.INVENTORY_MODULE_UPGRADE.get();
    }

    @Override
    public Component getAdjective() {
        return Component.literal("Inventory");
    }

    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(CodItems.INVENTORY_MODULE.get());
    }

    @Override
    public @Nullable IPeripheral createPeripheral(IPocketAccess access) {
        return null;
    }
}
