package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.peripheral.LaunchPadPeripheral;
import com.mc3699.codmod.peripheral.OpticalDriveInterfacePeripheral;
import com.mc3699.codmod.peripheral.RadarPeripheral;
import com.mc3699.codmod.peripheral.UAVControllerPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CodCapabilities {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                PeripheralCapability.get(),
                CodBlockEntities.LAUNCH_PAD.get(),
                (blockEntity, dir) -> new LaunchPadPeripheral(blockEntity)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                CodBlockEntities.RADAR.get(),
                (blockEntity, dir) -> new RadarPeripheral(blockEntity)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                CodBlockEntities.UAV_CONTROLLER.get(),
                (blockEntity, dir) -> new UAVControllerPeripheral(blockEntity)
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CodBlockEntities.LAUNCH_PAD.get(),
                (blockEntity, dir) -> blockEntity.getItems()
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                CodBlockEntities.OPTICAL_DRIVE_INTERFACE.get(),
                (driveInterface, direction) -> new OpticalDriveInterfacePeripheral(driveInterface)
        );
    }
}
