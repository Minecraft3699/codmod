package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.consoles.ScanningConsoleBlockEntity;
import com.mc3699.codmod.block.johnGeometry.JohnGeometryBlockEntity;
import com.mc3699.codmod.block.launchpad.LaunchPadBlockEntity;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntity;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlockEntity;
import com.mc3699.codmod.block.radar.RadarBlockEntity;
import com.mc3699.codmod.block.uavController.UAVControllerBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class CodBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE,
            Codmod.MOD_ID
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final Supplier<BlockEntityType<MantleKeyBlockEntity>> MANTLE_KEY = BLOCK_ENTITIES.register(
            "mantle_key",
            () -> new BlockEntityType<>(MantleKeyBlockEntity::new, Set.of(CodBlocks.MANTLE_KEY.get()), null)
    );

    public static final Supplier<BlockEntityType<JohnGeometryBlockEntity>> JOHN_GEOMETRY = BLOCK_ENTITIES.register(
            "john_geometry",
            () -> new BlockEntityType<>(JohnGeometryBlockEntity::new, Set.of(CodBlocks.JOHN_GEOMETRY.get()), null)
    );

    public static final Supplier<BlockEntityType<LaunchPadBlockEntity>> LAUNCH_PAD = BLOCK_ENTITIES.register(
            "launch_pad",
            () -> new BlockEntityType<>(LaunchPadBlockEntity::new, Set.of(CodBlocks.LAUNCH_PAD.get()), null)
    );

    public static final Supplier<BlockEntityType<RadarBlockEntity>> RADAR = BLOCK_ENTITIES.register(
            "radar",
            () -> new BlockEntityType<>(RadarBlockEntity::new, Set.of(CodBlocks.RADAR.get()), null)
    );

    public static final Supplier<BlockEntityType<UAVControllerBlockEntity>> UAV_CONTROLLER = BLOCK_ENTITIES.register(
            "uav_controller",
            () -> new BlockEntityType<>(UAVControllerBlockEntity::new, Set.of(CodBlocks.UAV_CONTROLLER.get()), null)
    );

    public static final Supplier<BlockEntityType<OpticalDriveInterfaceBlockEntity>> OPTICAL_DRIVE_INTERFACE = BLOCK_ENTITIES.register(
            "optical_drive_interface",
            () -> new BlockEntityType<>(OpticalDriveInterfaceBlockEntity::new, Set.of(CodBlocks.OPTICAL_DRIVE_INTERFACE.get()), null)
    );

    public static final BlockEntityEntry<ScanningConsoleBlockEntity> SCANNING_CONSOLE = CodRegistrate.INSTANCE.blockEntity("scanning_console",
            ScanningConsoleBlockEntity::new
    ).validBlock(CodBlocks.SCANNING_CONSOLE).register();
}
