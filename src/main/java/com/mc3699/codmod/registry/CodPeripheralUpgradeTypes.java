package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.commandModule.CommandModuleUpgrade;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodPeripheralUpgradeTypes {

    public static final DeferredRegister<UpgradeType<? extends IPocketUpgrade>> UPGRADES = DeferredRegister.create(IPocketUpgrade.typeRegistry(), Codmod.MOD_ID);

    public static final DeferredHolder<UpgradeType<? extends IPocketUpgrade>, UpgradeType<IPocketUpgrade>> COMMAND_MODULE_UPGRADE =
            UPGRADES.register("command_module", () -> UpgradeType.simple(new CommandModuleUpgrade()));

    public static void register(IEventBus eventBus)
    {
        UPGRADES.register(eventBus);
    }


}
