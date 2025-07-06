package com.mc3699.codmod.item.commandModule;

import com.mc3699.codmod.peripheral.CommandPeripheral;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodPeripheralUpgradeTypes;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class CommandModuleUpgrade implements IPocketUpgrade {


    @Override
    public UpgradeType<? extends IPocketUpgrade> getType() {
        return CodPeripheralUpgradeTypes.COMMAND_MODULE_UPGRADE.get();
    }

    @Override
    public Component getAdjective() {
        return Component.literal("Administrative").withColor(0xFF0000);
    }

    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(CodItems.COMMAND_MODULE.get());
    }

    @Override
    public @Nullable IPeripheral createPeripheral(IPocketAccess access) {
        return new CommandPeripheral(access);
    }
}
