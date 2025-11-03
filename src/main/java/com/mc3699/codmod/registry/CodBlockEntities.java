package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.backrooms.CeilingLightBlockEntity;
import com.mc3699.codmod.block.codNuke.CodNukeBlockEntity;
import com.mc3699.codmod.block.consoles.ScanningConsoleBlockEntity;
import com.mc3699.codmod.block.graphicsMonitor.GraphicsMonitorBlockEntity;
import com.mc3699.codmod.block.johnGeometry.JohnGeometryBlockEntity;
import com.mc3699.codmod.block.launchpad.LaunchPadBlockEntity;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntity;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlockEntity;
import com.mc3699.codmod.block.oxygenDistributor.OxygenDistributorBlockEntity;
import com.mc3699.codmod.block.radar.RadarBlockEntity;
import com.mc3699.codmod.block.teleporter.TeleporterBlockEntity;
import com.mc3699.codmod.block.triangulator.TriangulatorBlockEntity;
import com.mc3699.codmod.block.uavController.UAVControllerBlockEntity;
import com.mc3699.codmod.technology.coalGenerator.CoalGeneratorBlockEntity;
import com.mc3699.codmod.technology.lowVoltageCable.LowVoltageCableBlockEntity;
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
    public static final BlockEntityEntry<ScanningConsoleBlockEntity> SCANNING_CONSOLE = CodRegistrate.INSTANCE.blockEntity(
            "scanning_console",
            ScanningConsoleBlockEntity::new
    ).validBlock(CodBlocks.SCANNING_CONSOLE).register();

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }    public static final Supplier<BlockEntityType<MantleKeyBlockEntity>> MANTLE_KEY = BLOCK_ENTITIES.register(
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
            () -> new BlockEntityType<>(
                    OpticalDriveInterfaceBlockEntity::new,
                    Set.of(CodBlocks.OPTICAL_DRIVE_INTERFACE.get()),
                    null
            )
    );

    public static final Supplier<BlockEntityType<CeilingLightBlockEntity>> CEILING_LIGHT = BLOCK_ENTITIES.register(
            "ceiling_light",
            () -> new BlockEntityType<>(CeilingLightBlockEntity::new, Set.of(CodBlocks.CEILING_LIGHT.get()), null)
    );

    public static final Supplier<BlockEntityType<CodNukeBlockEntity>> COD_NUKE = BLOCK_ENTITIES.register(
            "cod_nuke",
            () -> new BlockEntityType<>(CodNukeBlockEntity::new, Set.of(CodBlocks.COD_NUKE.get()), null)
    );

    public static final Supplier<BlockEntityType<GraphicsMonitorBlockEntity>> GRAPHICS_MONITOR = BLOCK_ENTITIES.register(
            "graphics_monitor",
            () -> new BlockEntityType<>(GraphicsMonitorBlockEntity::new, Set.of(CodBlocks.GRAPHICS_MONITOR.get()), null)
    );

    public static final Supplier<BlockEntityType<TeleporterBlockEntity>> TELEPORTER = BLOCK_ENTITIES.register(
            "teleporter",
            () -> new BlockEntityType<>(TeleporterBlockEntity::new, Set.of(CodBlocks.TELEPORTER.get()), null)
    );

    public static final Supplier<BlockEntityType<TriangulatorBlockEntity>> TRIANGULATOR = BLOCK_ENTITIES.register(
            "triangulator",
            () -> new BlockEntityType<>(TriangulatorBlockEntity::new, Set.of(CodBlocks.TRIANGULATOR.get()), null)
    );

    public static final Supplier<BlockEntityType<OxygenDistributorBlockEntity>> OXYGEN_DISTRIBUTOR = BLOCK_ENTITIES.register(
            "oxygen_distributor",
            () -> new BlockEntityType<>(OxygenDistributorBlockEntity::new, Set.of(CodBlocks.OXYGEN_DISTRIBUTOR.get()), null)
    );

    // Cod Tech Stuff

    public static final Supplier<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = BLOCK_ENTITIES.register(
            "coal_generator",
            () -> new BlockEntityType<>(CoalGeneratorBlockEntity::new, Set.of(CodBlocks.COAL_GENERATOR.get()), null)
    );

    public static final Supplier<BlockEntityType<LowVoltageCableBlockEntity>> LOW_VOLTAGE_CABLE = BLOCK_ENTITIES.register(
            "lv_cable",
            ()->new BlockEntityType<>(LowVoltageCableBlockEntity::new, Set.of(CodBlocks.LV_CABLE_BLOCK.get()), null)
    );


}
