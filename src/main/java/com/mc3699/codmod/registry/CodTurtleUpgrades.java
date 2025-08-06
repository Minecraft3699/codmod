package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.environmentScanner.EnvironmentScannerItem;
import com.mc3699.codmod.item.environmentScanner.EnvironmentScannerUpgrade;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodTurtleUpgrades {

    public static final DeferredRegister<UpgradeType<? extends ITurtleUpgrade>> TURTLE_UPGRADES = DeferredRegister.create(ITurtleUpgrade.typeRegistry(), Codmod.MOD_ID);

    public static final DeferredHolder<UpgradeType<? extends ITurtleUpgrade>, UpgradeType<ITurtleUpgrade>> ENVIRONMENT_SCANNER = TURTLE_UPGRADES.register(
            "environment_scanner",
            () -> UpgradeType.simple(new EnvironmentScannerUpgrade())
    );

    public static void register(IEventBus eventBus)
    {
        TURTLE_UPGRADES.register(eventBus);
    }


}
