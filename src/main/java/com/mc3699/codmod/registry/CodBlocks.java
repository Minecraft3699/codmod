package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.DellServerBlock;
import com.mc3699.codmod.block.consoles.ScanningConsoleBlock;
import com.mc3699.codmod.block.johnGeometry.JohnGeometryBlock;
import com.mc3699.codmod.block.launchpad.LaunchPadBlock;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlock;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlock;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlockEntity;
import com.mc3699.codmod.block.radar.RadarBlock;
import com.mc3699.codmod.block.server.ServerBlock;
import com.mc3699.codmod.block.subspace_tripmine.SubspaceTripmineBlock;
import com.mc3699.codmod.block.uavController.UAVControllerBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Codmod.MOD_ID);

    public static final DeferredBlock<Block> MOLTEN_COPPER = BLOCKS.register(
            "molten_copper",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK))
    );

    public static final DeferredBlock<Block> DELL_SERVER = BLOCKS.register(
            "dell_server", DellServerBlock::new
    );

    public static final DeferredBlock<Block> RADAR = BLOCKS.register("radar", RadarBlock::new);

    public static final DeferredBlock<LaunchPadBlock> LAUNCH_PAD = BLOCKS.register("launch_pad", LaunchPadBlock::new);

    public static final DeferredBlock<UAVControllerBlock> UAV_CONTROLLER = BLOCKS.register(
            "uav_controller",
            UAVControllerBlock::new
    );

    public static final DeferredBlock<Block> STABLE_DISRUPTION = BLOCKS.register(
            "stable_disruption",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noCollission())
    );

    public static final DeferredBlock<Block> SERVER = BLOCKS.register("server", ServerBlock::new);

    public static final DeferredBlock<Block> JOHN_GEOMETRY = BLOCKS.register("john_geometry", JohnGeometryBlock::new);


    public static final DeferredBlock<Block> OPTICAL_DRIVE_INTERFACE = BLOCKS.register("optical_drive_interface",
            () -> new OpticalDriveInterfaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));


    public static final DeferredBlock<MantleKeyBlock> MANTLE_KEY = BLOCKS.register(
            "mantle_key",
            () -> new MantleKeyBlock(BlockBehaviour.Properties.of())
    );

    public static final BlockEntry<ScanningConsoleBlock> SCANNING_CONSOLE = CodRegistrate.INSTANCE.block("scanning_console",
            ScanningConsoleBlock::new
    ).register();

    public static final BlockEntry<Block> COD_BLOCK = CodRegistrate.INSTANCE.block("cod_block", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK))
            .lang("Block of Cod")
            .simpleItem()
            .register();

    public static final BlockEntry<SubspaceTripmineBlock> SUBSPACE_TRIPMINE = CodRegistrate.INSTANCE.block("subspace_tripmine",
            SubspaceTripmineBlock::new)
            .properties(properties -> properties.noCollission().noOcclusion())
            .lang("Subspace Tripmine")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MOIST_CARPET = CodRegistrate.INSTANCE.block("moist_carpet" , Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL))
            .simpleItem()
            .lang("Moist Carpet")
            .register();

    public static final BlockEntry<Block> CEILING_TILE = CodRegistrate.INSTANCE.block("ceiling_tile" , Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE))
            .simpleItem()
            .lang("Ceiling Tile")
            .register();

    public static final BlockEntry<Block> CEILING_LIGHT = CodRegistrate.INSTANCE.block("ceiling_light" , Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE))
            .simpleItem()
            .lang("Ceiling Light")
            .register();

    public static final BlockEntry<Block> UGLY_WALLPAPER = CodRegistrate.INSTANCE.block("ugly_wallpaper" , Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))
            .simpleItem()
            .lang("Ugly Wallpaper")
            .register();





    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
