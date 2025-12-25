package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.DellServerBlock;
import com.mc3699.codmod.block.EnderCropBlock;
import com.mc3699.codmod.block.backrooms.CeilingLightBlock;
import com.mc3699.codmod.block.bloodGenerator.BloodGeneratorBlock;
import com.mc3699.codmod.block.codNuke.CodNukeBlock;
import com.mc3699.codmod.block.graphicsMonitor.GraphicsMonitorBlock;
import com.mc3699.codmod.block.oxygenDistributor.OxygenDistributorBlock;
import com.mc3699.codmod.block.teleporter.TeleporterBlock;
import com.mc3699.codmod.block.thresholdParts.ThresholdPortalBlock;
import com.mc3699.codmod.block.consoles.ScanningConsoleBlock;
import com.mc3699.codmod.block.johnGeometry.JohnGeometryBlock;
import com.mc3699.codmod.block.launchpad.LaunchPadBlock;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlock;
import com.mc3699.codmod.block.opticalDriveInterface.OpticalDriveInterfaceBlock;
import com.mc3699.codmod.block.radar.RadarBlock;
import com.mc3699.codmod.block.server.ServerBlock;
import com.mc3699.codmod.block.subspace_tripmine.SubspaceTripmineBlock;
import com.mc3699.codmod.block.thresholdParts.ThresholdBackplaneBlock;
import com.mc3699.codmod.block.thresholdParts.ThresholdEmitterBlock;
import com.mc3699.codmod.block.triangulator.TriangulatorBlock;
import com.mc3699.codmod.block.uavController.UAVControllerBlock;
import com.mc3699.codmod.technology.coalGenerator.CoalGeneratorBlock;
import com.mc3699.codmod.technology.lowVoltageCable.LowVoltageCableBlock;
import com.mc3699.codmod.block.PlushieBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.FluidEntry;
import dev.wendigodrip.thebrokenscript.registry.TBSBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CodBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Codmod.MOD_ID);

    public static final DeferredBlock<Block> MOLTEN_COPPER = BLOCKS.register(
            "molten_copper",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGMA_BLOCK))
    );

    public static final DeferredBlock<Block> DELL_SERVER = BLOCKS.register(
            "dell_server", DellServerBlock::new
    );

    public static final DeferredBlock<Block> ENDER_CROP = BLOCKS.register("ender_crop",
            () -> new EnderCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

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


    public static final DeferredBlock<Block> OPTICAL_DRIVE_INTERFACE = BLOCKS.register(
            "optical_drive_interface",
            () -> new OpticalDriveInterfaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
    );


    public static final DeferredBlock<MantleKeyBlock> MANTLE_KEY = BLOCKS.register(
            "mantle_key",
            () -> new MantleKeyBlock(BlockBehaviour.Properties.of())
    );

    public static final BlockEntry<ScanningConsoleBlock> SCANNING_CONSOLE = CodRegistrate.INSTANCE.block(
            "scanning_console",
            ScanningConsoleBlock::new
    ).register();

    public static final BlockEntry<Block> COD_BLOCK = CodRegistrate.INSTANCE.block("cod_block", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK))
            .lang("Block of Cod")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CHOCOLATE_CAKE_BLOCK = CodRegistrate.INSTANCE.block("chocolate_cake_block", Block::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .lang("Chocolate Ice Cream Cake")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CONSULAR_CAKE_BLOCK = CodRegistrate.INSTANCE.block("consular_cake_block", Block::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .lang("Slime Cake")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PRESENT = CodRegistrate.INSTANCE.block("present", Block::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .lang("Present")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> COMPACTEDGLASS1 = CodRegistrate.INSTANCE.block("compactedglass1", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS))
            .simpleItem()
            .lang("Compacted Glass")
            .register();

    public static final BlockEntry<SubspaceTripmineBlock> SUBSPACE_TRIPMINE = CodRegistrate.INSTANCE.block(
                    "subspace_tripmine",
                    SubspaceTripmineBlock::new
            )
            .properties(properties -> properties.noCollission().noOcclusion())
            .lang("Subspace Tripmine")
            .simpleItem()
            .loot((c,b) -> c.add(b, c.createOreDrop(b, Items.COD)))
            .register();

    public static final BlockEntry<PlushieBlock> GARRETH_PLUSHIE = CodRegistrate.INSTANCE.block("garreth_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Garreth Plushie of stupidity.")
            .register();

    public static final BlockEntry<PlushieBlock> CONSULAR_PLUSHIE = CodRegistrate.INSTANCE.block("consular_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Consular Plush")
            .register();

    public static final BlockEntry<Block> CHEESE_BLOCK = CodRegistrate.INSTANCE.block("cheese_block", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL))
            .simpleItem()
            .lang("Cheese Block")
            .register();

    public static final BlockEntry<PlushieBlock> INKI_PLUSHIE = CodRegistrate.INSTANCE.block("inki_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Inki Plush")
            .register();

    public static final BlockEntry<PlushieBlock> ANCIENT_PLUSHIE = CodRegistrate.INSTANCE.block("ancient_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Ancients Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> SKISHEL_PLUSHIE = CodRegistrate.INSTANCE.block("skishel_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Skishel Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> TERRA_PLUSHIE = CodRegistrate.INSTANCE.block("terra_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Terra Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> FIRELIGHT_PLUSHIE = CodRegistrate.INSTANCE.block("firelight_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§5Firelight §rPlushie")
            .register();

    public static final BlockEntry<PlushieBlock> SCUFFED_PLUSHIE = CodRegistrate.INSTANCE.block("scuffed_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Scuffed Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> NORMAL_ABBE_PLUSHIE = CodRegistrate.INSTANCE.block("normal_abbe_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Abbe Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> PINKY_PLUSHIE = CodRegistrate.INSTANCE.block("pinky_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§dPinky§r §3Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> BLADE_PLUSHIE = CodRegistrate.INSTANCE.block("blade_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§cBlade Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> NUZ_PLUSHIE = CodRegistrate.INSTANCE.block("nuz_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§bNuz Plushie.")
            .register();

    public static final BlockEntry<PlushieBlock> ZETOS_PLUSHIE = CodRegistrate.INSTANCE.block("zetos_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§gZetos Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> EYAE_PLUSHIE = CodRegistrate.INSTANCE.block("eyae_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("§1Eyae Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> GUSTA_PLUSHIE = CodRegistrate.INSTANCE.block("gusta_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("ThinGusta Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> AMBORELLA_PLUSHIE = CodRegistrate.INSTANCE.block("amborella_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Amborella Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> XYZEN_PLUSHIE = CodRegistrate.INSTANCE.block("xyzen_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("Xyzen Plushie")
            .register();

    public static final BlockEntry<PlushieBlock> NAHBRO_PLUSHIE = CodRegistrate.INSTANCE.block("nahbro_plushie", PlushieBlock::new)
            .initialProperties(() -> Blocks.WHITE_WOOL)
            .properties(properties -> properties.noCollission().noOcclusion())
            .simpleItem()
            .lang("NahBro Plushie")
            .register();

    public static final BlockEntry<Block> MOIST_CARPET = CodRegistrate.INSTANCE.block("moist_carpet", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL))
            .simpleItem()
            .lang("Moist Carpet")
            .register();

    public static final BlockEntry<Block> CEILING_TILE = CodRegistrate.INSTANCE.block("ceiling_tile", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE))
            .simpleItem()
            .lang("Ceiling Tile")
            .register();

    public static final BlockEntry<CeilingLightBlock> CEILING_LIGHT = CodRegistrate.INSTANCE.block("ceiling_light", CeilingLightBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).lightLevel((state) -> state.getValue(CeilingLightBlock.ENABLED) ? 15 : 0))
            .simpleItem()
            .defaultBlockstate()
            .lang("Ceiling Light")
            .register();

    public static final BlockEntry<Block> UGLY_WALLPAPER = CodRegistrate.INSTANCE.block("ugly_wallpaper", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))
            .simpleItem()
            .lang("Ugly Wallpaper")
            .register();

    public static final BlockEntry<Block> RED_MOIST_CARPET = CodRegistrate.INSTANCE.block("red_moist_carpet", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL))
            .simpleItem()
            .lang("Stained Moist Carpet")
            .register();

    public static final BlockEntry<Block> RED_CEILING_TILE = CodRegistrate.INSTANCE.block("red_ceiling_tile", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE))
            .simpleItem()
            .lang("Stained Ceiling Tile")
            .register();

    public static final BlockEntry<Block> RED_CEILING_LIGHT = CodRegistrate.INSTANCE.block("red_ceiling_light", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE))
            .simpleItem()
            .lang("Stained Ceiling Light")
            .register();

    public static final BlockEntry<Block> RED_UGLY_WALLPAPER = CodRegistrate.INSTANCE.block("red_ugly_wallpaper", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD))
            .simpleItem()
            .lang("Stained Ugly Wallpaper")
            .register();

    public static final BlockEntry<ThresholdEmitterBlock> THRESHOLD_EMITTER = CodRegistrate.INSTANCE.block("threshold_emitter", ThresholdEmitterBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion())
            .simpleItem()
            .lang("Threshold Emitter")
            .register();


    public static final BlockEntry<ThresholdBackplaneBlock> THRESHOLD_BACKPLANE = CodRegistrate.INSTANCE.block("threshold_backplane", ThresholdBackplaneBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion())
            .simpleItem()
            .lang("Threshold Backplane")
            .register();

    public static final BlockEntry<Block> CERAMIC_TILE = CodRegistrate.INSTANCE.block("ceramic_tile", Block::new)
            .simpleItem()
            .lang("Ceramic Tiles")
            .register();

    public static final BlockEntry<StairBlock> CERAMIC_STAIRS = CodRegistrate.INSTANCE
            .block("ceramic_stairs", p -> new StairBlock(CodBlocks.CERAMIC_TILE.getDefaultState(), p))
            .simpleItem()
            .lang("Ceramic Tile Stairs")
            .blockstate((ctx, prov) ->
                    prov.stairsBlock(ctx.get(), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/ceramic_tile")))
            .register();

    public static final BlockEntry<SlabBlock> CERAMIC_SLAB = CodRegistrate.INSTANCE
            .block("ceramic_slab", SlabBlock::new)
            .simpleItem()
            .lang("Ceramic Tile Slab")
            .blockstate((ctx, prov) ->
                    prov.slabBlock(ctx.get(), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/ceramic_tile"), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/ceramic_tile")))
            .register();


    public static final BlockEntry<ThresholdPortalBlock> THRESHOLD_PORTAL = CodRegistrate.INSTANCE.block("threshold_portal",ThresholdPortalBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL))
            .register();

    public static final BlockEntry<CodNukeBlock> COD_NUKE = CodRegistrate.INSTANCE.block("cod_nuke", CodNukeBlock::new)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_BLOCK = CodRegistrate.INSTANCE.block("public_static_void", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Public Static Void")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_SMALL_TILES_BLOCK = CodRegistrate.INSTANCE.block("public_static_void_small_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Public Static Void Small Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_TILES_BLOCK = CodRegistrate.INSTANCE.block("public_static_void_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Public Static Void Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_DRIVE_BLOCK = CodRegistrate.INSTANCE.block("public_static_void_drive", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Public Static Void Drive")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_KEYPAD_BLOCK = CodRegistrate.INSTANCE.block("public_static_void_keypad", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Public Static Void Keypad")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> RED_PUBLIC_STATIC_VOID_BLOCK = CodRegistrate.INSTANCE.block("red_public_static_void", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Red Public Static Void")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> RED_PUBLIC_STATIC_VOID_SMALL_TILES_BLOCK = CodRegistrate.INSTANCE.block("red_public_static_void_small_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Red Public Static Void Small Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> RED_PUBLIC_STATIC_VOID_TILES_BLOCK = CodRegistrate.INSTANCE.block("red_public_static_void_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Red Public Static Void Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> RED_PUBLIC_STATIC_VOID_EYE = CodRegistrate.INSTANCE.block("red_public_static_void_eye", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Red Public Static Void Eye")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> RED_PUBLIC_STATIC_VOID_TILES_LOGO = CodRegistrate.INSTANCE.block("red_public_static_void_logo", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Red Public Static Void Logo")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PURPLE_PUBLIC_STATIC_VOID_BLOCK = CodRegistrate.INSTANCE.block("purple_public_static_void", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Purple Public Static Void")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PURPLE_PUBLIC_STATIC_VOID_SMALL_TILES_BLOCK = CodRegistrate.INSTANCE.block("purple_public_static_void_small_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Purple Public Static Void Small Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PURPLE_PUBLIC_STATIC_VOID_TILES_BLOCK = CodRegistrate.INSTANCE.block("purple_public_static_void_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Purple Public Static Void Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> BLUE_PUBLIC_STATIC_VOID_BLOCK = CodRegistrate.INSTANCE.block("blue_public_static_void", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Blue Public Static Void")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> BLUE_PUBLIC_STATIC_VOID_SMALL_TILES_BLOCK = CodRegistrate.INSTANCE.block("blue_public_static_void_small_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Blue Public Static Void Small Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> BLUE_PUBLIC_STATIC_VOID_TILES_BLOCK = CodRegistrate.INSTANCE.block("blue_public_static_void_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Blue Public Static Void Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> GREEN_PUBLIC_STATIC_VOID_BLOCK = CodRegistrate.INSTANCE.block("green_public_static_void", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Green Public Static Void")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> GREEN_PUBLIC_STATIC_VOID_SMALL_TILES_BLOCK = CodRegistrate.INSTANCE.block("green_public_static_void_small_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Green Public Static Void Small Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> GREEN_PUBLIC_STATIC_VOID_TILES_BLOCK = CodRegistrate.INSTANCE.block("green_public_static_void_tiles", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Green Public Static Void Tiles")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> SPIRAL_LAMP = CodRegistrate.INSTANCE.block("spiral_lamp", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE))
            .simpleItem()
            .lang("Pink Spiral Lamp")
            .register();

    public static final BlockEntry<StairBlock> PUBLIC_STATIC_VOID_STAIRS = CodRegistrate.INSTANCE.
            block("public_static_void_stairs", properties -> new StairBlock(CodBlocks.PUBLIC_STATIC_VOID_BLOCK.getDefaultState(), properties))
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.get(), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/public_static_void")))
            .lang("Public Static Void Stairs")
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> PUBLIC_STATIC_VOID_SLAB = CodRegistrate.INSTANCE
            .block("public_static_void_slab", SlabBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .blockstate((ctx, prov) -> {prov.slabBlock(ctx.get(), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/public_static_void"), ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/public_static_void"));})
            .lang("Public Static Void Slab")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ENTROPY_SHALE = CodRegistrate.INSTANCE.block("entropy_shale", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .lang("Entropic Shale")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ENTROPY_SAND = CodRegistrate.INSTANCE.block("entropy_sand", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.SAND))
            .lang("Entropic Regolith")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> ENTROPY_BASALT = CodRegistrate.INSTANCE.block("entropy_basalt", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE))
            .lang("Entropic Basalt")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> IRON_MANTLE = CodRegistrate.INSTANCE.block("iron_mantle", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRYING_OBSIDIAN))
            .lang("Iron Mantle")
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PUBLIC_STATIC_VOID_LIGHT = CodRegistrate.INSTANCE.block("public_static_void_light", Block::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE))
            .lang("Public Static Void Light")
            .simpleItem()
            .register();

    public static final BlockEntry<CoalGeneratorBlock> COAL_GENERATOR = CodRegistrate.INSTANCE.block("coal_generator", CoalGeneratorBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
            .blockstate(
                    (ctx, prov) -> {
                        prov.horizontalBlock(
                                ctx.get(),
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/machine_casing_side"),
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/coal_generator_front"),
                                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/machine_casing_top")
                                );}
            )
            .simpleItem()
            .register();

    public static final BlockEntry<GraphicsMonitorBlock> GRAPHICS_MONITOR = CodRegistrate.INSTANCE.block("graphics_monitor", GraphicsMonitorBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .blockstate((ctx, prov) -> {

               prov.horizontalBlock(
                        ctx.get(),
                        ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/graphics_monitor_side"),
                        ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/graphics_monitor_front"),
                        ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/graphics_monitor_top")
                );

            })
            .lang("Graphics Monitor")
            .simpleItem()
            .register();

    public static final BlockEntry<LowVoltageCableBlock> LV_CABLE_BLOCK = CodRegistrate.INSTANCE.block("lv_cable", LowVoltageCableBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
            .simpleItem()
            .register();

    public static final BlockEntry<TeleporterBlock> TELEPORTER = CodRegistrate.INSTANCE.block("teleporter", TeleporterBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
            .blockstate(
                    (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cube(
                            "teleporter",
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_bottom"),
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_top"),
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_side"),
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_side"),
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_side"),
                            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "block/teleporter_side")
                    ))
            )
            .simpleItem()
            .register();

    public static final BlockEntry<TriangulatorBlock> TRIANGULATOR = CodRegistrate.INSTANCE.block("triangulator", TriangulatorBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
            .simpleItem()
            .register();

    public static final BlockEntry<OxygenDistributorBlock> OXYGEN_DISTRIBUTOR = CodRegistrate.INSTANCE.block("oxygen_distributor", OxygenDistributorBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK))
            .simpleItem()
            .lang("Oxygen Distributor")
            .register();

    public static final BlockEntry<BloodGeneratorBlock> BLOOD_GENERATOR = CodRegistrate.INSTANCE.block("blood_generator", BloodGeneratorBlock::new)
            .properties(properties -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
            .simpleItem()
            .lang("Blood Generator")
            .register();



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        CodItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
