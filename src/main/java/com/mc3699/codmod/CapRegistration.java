package com.mc3699.codmod;

import com.mc3699.codmod.block.BlockEntityRegistration;
import com.mc3699.codmod.block.radar.RadarBlockEntity;
import com.mc3699.codmod.peripheral.LaunchPadPeripheral;
import com.mc3699.codmod.peripheral.RadarPeripheral;
import com.mc3699.codmod.peripheral.UAVControllerPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CapRegistration {

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(PeripheralCapability.get(), BlockEntityRegistration.LAUNCH_PAD.get(), (blockEntity, dir) -> new LaunchPadPeripheral(blockEntity));
        event.registerBlockEntity(PeripheralCapability.get(), BlockEntityRegistration.RADAR.get(), (blockEntity, dir) -> new RadarPeripheral(blockEntity));
        event.registerBlockEntity(PeripheralCapability.get(), BlockEntityRegistration.UAV_CONTROLLER.get(), (blockEntity, dir) -> new UAVControllerPeripheral(blockEntity));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistration.LAUNCH_PAD.get(), (blockEntity, dir) -> blockEntity.getItems());

    }

}
