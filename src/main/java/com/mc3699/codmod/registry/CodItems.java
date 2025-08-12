package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.*;
import com.mc3699.codmod.item.commandModule.CommandModuleItem;
import com.mc3699.codmod.item.designator.DesignatorItem;
import com.mc3699.codmod.item.environmentScanner.EnvironmentScannerItem;
import com.mc3699.codmod.item.hubModule.HubModuleItem;
import com.mc3699.codmod.item.inventoryModule.InventoryModuleItem;
import com.mc3699.codmod.item.marksmanRevolver.MarksmanRevolverItem;
import com.mc3699.codmod.item.transponder.TransponderItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.mc3699.codmod.registry.CodBlocks.BLOCKS;

public class CodItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Codmod.MOD_ID);

    public static final ItemEntry<CodRodItem> COD_ROD = CodRegistrate.INSTANCE.item("cod_rod", CodRodItem::new)
            .properties((p) -> p.rarity(Rarity.EPIC).durability(100))
            .register();

    public static final Supplier<Item> NULL_COD = ITEMS.register(
            "null_cod",
            () -> new Item(new Item.Properties().food(Foods.COD))
    );

    public static final Supplier<Item> MINICOD = ITEMS.register("minicod", MiniCodItem::new);

    public static final Supplier<Item> NULL_CHICKEN = ITEMS.register(
            "null_chicken",
            () -> new Item(new Item.Properties().food(Foods.CHICKEN))
    );

    public static final Supplier<Item> NULL_DRIED_KELP = ITEMS.register(
            "null_dried_kelp",
            () -> new Item(new Item.Properties().food(Foods.DRIED_KELP))
    );

    public static final Supplier<Item> SHRIMP = ITEMS.register(
            "shrimp",
            () -> new Item(new Item.Properties().food(Foods.COOKED_CHICKEN).rarity(Rarity.RARE))
    );

    public static final Supplier<Item> APPLICATION = ITEMS.register(
            "application",
            () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> INTEGRITY_COOKIE = ITEMS.register(
            "integrity_cookie",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(-10)
                    .saturationModifier(0)
                    .effect(() -> new MobEffectInstance(CodMobEffects.HEART_CORRUPTION, 200, 5, false, true), 1.0f)
                    .alwaysEdible()
                    .build()))
    );

    public static final Supplier<Item> REAL_COD = ITEMS.register(
            "realcod",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)
                    .food(new FoodProperties.Builder().nutrition(10000)
                            .saturationModifier(10000)
                            .alwaysEdible()
                            .build()))
    );

    public static final Supplier<Item> UAV = ITEMS.register("uav", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> DETECTOR = ITEMS.register("detector", () -> new Item(new Item.Properties()));


    public static final Supplier<Item> BROKEN_DETECTOR = ITEMS.register(
            "broken_detector",
            () -> new Item(new Item.Properties())
    );

    public static final Supplier<Item> GIANNI = ITEMS.register("gianni", () -> new Item(new Item.Properties()));

    public static final ItemEntry<MaxwellItem> MAXWELL = CodRegistrate.INSTANCE.item("maxwell", MaxwellItem::new)
            .properties((p) -> p.durability(100))
            .register();

    // did I do good this time?
    public static final ItemEntry<CircuitsBaneItem> CIRCUITS_BANE = CodRegistrate.INSTANCE.item(
                    "circuits_bane",
                    CircuitsBaneItem::new
            )
            .model((ctx, prov) -> prov.handheld(ctx.lazy()))
            .properties((properties -> properties.durability(1024).rarity(Rarity.EPIC)))
            .lang("Circuit's Bane")
            .register();

    public static final ItemEntry<ResistorItem> RESISTOR = CodRegistrate.INSTANCE.item("resistor", ResistorItem::new)
            .properties(properties -> properties.rarity(Rarity.EPIC))
            .lang("Comically Large Resistor")
            .register();

    public static final ItemEntry<ShrimpSniperItem> SHRIMP_SNIPER = CodRegistrate.INSTANCE.item(
                    "shrimp_sniper",
                    ShrimpSniperItem::new
            )
            .properties(properties -> properties)
            .lang("AWP_Shrimp")
            .register();

    public static final ItemEntry<OpticalTapeDriveItem> DRIVE = CodRegistrate.INSTANCE.item(
                    "drive",
                    OpticalTapeDriveItem::new
            )
            .properties(properties -> properties.stacksTo(1).rarity(Rarity.UNCOMMON))
            .lang("Optical Tape Drive")
            .register();

    public static final ItemEntry<CommandModuleItem> COMMAND_MODULE = CodRegistrate.INSTANCE.item(
                    "command_module",
                    CommandModuleItem::new
            )
            .properties(properties -> properties)
            .lang("Command Module")
            .register();

    public static final ItemEntry<HubModuleItem> HUB_MODULE = CodRegistrate.INSTANCE.item(
                    "hub_module",
                    HubModuleItem::new
            )
            .properties(properties -> properties)
            .lang("Hub Module")
            .register();

    public static final ItemEntry<InventoryModuleItem> INVENTORY_MODULE = CodRegistrate.INSTANCE.item(
                    "inventory_module",
                    InventoryModuleItem::new
            )
            .properties(properties -> properties)
            .lang("Inventory Module")
            .register();

    public static final ItemEntry<RoundMealItem> ROUND_MEAL = CodRegistrate.INSTANCE.item(
                    "round_meal",
                    RoundMealItem::new
            )
            .properties(properties -> properties.food(CodFoods.ROUND_MEAL))
            .lang("Round Meal")
            .register();

    public static final ItemEntry<NetworkFuckerItem> NETWORK_FUCKER = CodRegistrate.INSTANCE.item(
                    "network_fucker",
                    NetworkFuckerItem::new
            )
            .properties(properties -> properties)
            .lang("Network Fucker 9000")
            .register();

    public static final ItemEntry<ContinumSlicerItem> CONTINUM_SLICER = CodRegistrate.INSTANCE.item(
                    "scythe",
                    ContinumSlicerItem::new
            )
            .properties(properties -> properties)
            .lang("§5Continuum Slicer")
            .register();

    public static final ItemEntry<MiningHeadgearItem> MINING_HEADGEAR = CodRegistrate.INSTANCE.item(
                    "mining_headgear",
                    MiningHeadgearItem::new
            )
            .properties(properties -> properties)
            .lang("Mining Helmet")
            .register();

    public static final ItemEntry<Item> RED = CodRegistrate.INSTANCE.item("red", Item::new)
            .properties(properties -> properties)
            .lang("Red")
            .register();

    public static final ItemEntry<Item> GREEN = CodRegistrate.INSTANCE.item("green", Item::new)
            .properties(properties -> properties)
            .lang("Green")
            .register();

    public static final ItemEntry<CodPickaxeItem> COD_PICKAXE = CodRegistrate.INSTANCE.item("cod_pickaxe",CodPickaxeItem::new)
            .properties(properties -> properties)
            .lang("Cod Pickaxe")
            .register();

    public static final ItemEntry<SoulTakerItem> SOUL_TAKER = CodRegistrate.INSTANCE.item(
                    "soul_taker",
                    SoulTakerItem::new
            )
            .properties(properties -> properties)
            .lang("§fSoul §0Taker")
            .register();

    public static final ItemEntry<Item> BAGEL = CodRegistrate.INSTANCE.item(
            "bagel",
            Item::new
            )
            .properties(properties -> properties.food(Foods.BREAD))
            .lang("Bagel")
            .register();

    public static final ItemEntry<Item> DONUT = CodRegistrate.INSTANCE.item(
            "donut",
            Item::new
            )
            .properties(properties -> new Item.Properties().food(CodFoods.CAKE))
            .register();

    public static final ItemEntry<VoidBreakerItem> VOID_BREAKER = CodRegistrate.INSTANCE.item(
                    "voidbreaker",
                    VoidBreakerItem::new
            )
            .model((ctx, prov) -> prov.handheld(ctx.lazy()))
            .properties(properties -> properties)
            .lang("Voidbreaker")
            .register();

    public static final ItemEntry<Item> BLUE = CodRegistrate.INSTANCE.item(
                "blue",
                Item::new
            )
            .properties(properties -> properties)
            .lang("Blue")
            .register();

    public static final ItemEntry<Item> cod_bomb = CodRegistrate.INSTANCE.item(
                    "cod_bomb",
                    Item::new
            )
            .properties(properties -> properties)
            .lang("Cod Bomb")
            .register();


    public static final ItemEntry<ArmorItem> NBC_HELMET = CodRegistrate.INSTANCE.item(
            "nbc_helmet",
            (properties) -> new ArmorItem(Holder.direct(CodArmor.NBC_ARMOR_MATERIAL), ArmorItem.Type.HELMET, properties)
            )
            .properties(properties -> properties.stacksTo(1))
            .lang("NBC Suit Helmet")
            .register();

    public static final ItemEntry<ArmorItem> NBC_CHESTPLATE = CodRegistrate.INSTANCE.item(
                    "nbc_chestplate",
                    (properties) -> new ArmorItem(Holder.direct(CodArmor.NBC_ARMOR_MATERIAL), ArmorItem.Type.CHESTPLATE, properties)
            )
            .properties(properties -> properties.stacksTo(1))
            .lang("NBC Suit Chestplate")
            .register();

    public static final ItemEntry<ArmorItem> NBC_LEGGINGS = CodRegistrate.INSTANCE.item(
                    "nbc_leggings",
                    (properties) -> new ArmorItem(Holder.direct(CodArmor.NBC_ARMOR_MATERIAL), ArmorItem.Type.LEGGINGS, properties)
            )
            .properties(properties -> properties.stacksTo(1))
            .lang("NBC Suit Leggings")
            .register();

    public static final ItemEntry<NullChickenSwordItem> NULL_CHICKEN_SWORD = CodRegistrate.INSTANCE.item(
            "null_chicken_sword",
            NullChickenSwordItem::new
            )
            .properties(properties -> properties)
            .lang("Null Chicken Sword")
            .register();

    public static final ItemEntry<ArmorItem> NBC_BOOTS = CodRegistrate.INSTANCE.item(
                    "nbc_boots",
                    (properties) -> new ArmorItem(Holder.direct(CodArmor.NBC_ARMOR_MATERIAL), ArmorItem.Type.BOOTS, properties)
            )
            .properties(properties -> properties.stacksTo(1))
            .lang("NBC Suit Boots")
            .register();

    public static final ItemEntry<MarksmanRevolverItem> MARKSMAN_REVOLVER = CodRegistrate.INSTANCE.item(
            "marksman_revolver",
            (MarksmanRevolverItem::new))
            .lang("Codsman Revolver")
            .register();

    public static final ItemEntry<CurrentMeterItem> CURRENT_METER = CodRegistrate.INSTANCE.item(
            "current_meter",
            CurrentMeterItem::new)
            .lang("Current Meter")
            .register();

    public static final ItemEntry<EnvironmentScannerItem> ENVIRONMENT_SCANNER = CodRegistrate.INSTANCE.item(
            "environment_scanner",
            EnvironmentScannerItem::new)
            .properties(properties -> properties.stacksTo(1))
            .lang("Environment Scanner")
            .register();

    public static final ItemEntry<TransponderItem> TRANSPONDER = CodRegistrate.INSTANCE.item(
            "transponder",
            TransponderItem::new)
            .properties(properties -> properties.stacksTo(1))
            .lang("Transponder")
            .register();

    public static final ItemEntry<Item> TELEPORTED_BREAD = CodRegistrate.INSTANCE.item(
            "teleported_bread",
            Item::new)
            .properties(properties -> properties.food(CodFoods.MANGO))
            .lang("Teleported Bread")
            .register();

    public static final ItemEntry<DesignatorItem> DESIGNATOR = CodRegistrate.INSTANCE.item(
            "designator",
            DesignatorItem::new)
            .properties(properties -> properties.stacksTo(1))
            .lang("Target Designator")
            .register();

    public static final ItemEntry<DisconnectorItem> DISCONNECTOR = CodRegistrate.INSTANCE.item
            ("disconnector", DisconnectorItem::new)
            .properties(properties -> properties)
            .lang("Disconnector")
            .register();

    // VOTV Foods

    public static final Supplier<Item> BURGER = ITEMS.register(
            "burger",
            () -> new Item(new Item.Properties().food(CodFoods.BURGER))
    );

    public static final Supplier<Item> BAGUETTE = ITEMS.register(
            "baguette",
            () -> new Item(new Item.Properties().food(CodFoods.BAGUETTE))
    );

    public static final Supplier<Item> BANANA = ITEMS.register(
            "banana",
            () -> new Item(new Item.Properties().food(CodFoods.BANANA))
    );

    public static final Supplier<Item> BUN = ITEMS.register(
            "bun",
            () -> new Item(new Item.Properties().food(CodFoods.BUN))
    );

    public static final Supplier<Item> CAKE = ITEMS.register(
            "cake",
            () -> new Item(new Item.Properties().food(CodFoods.CAKE))
    );

    public static final Supplier<Item> CHEESE = ITEMS.register(
            "cheese",
            () -> new Item(new Item.Properties().food(CodFoods.CHEESE))
    );

    public static final Supplier<Item> CHICKEN_NUGGET = ITEMS.register(
            "chicken_nugget",
            () -> new Item(new Item.Properties().food(CodFoods.CHICKEN_NUGGET))
    );

    public static final Supplier<Item> CHOCOLATE = ITEMS.register(
            "chocolate",
            () -> new Item(new Item.Properties().food(CodFoods.CHOCOLATE))
    );

    public static final Supplier<Item> CUCUMBER = ITEMS.register(
            "cucumber",
            () -> new Item(new Item.Properties().food(CodFoods.CUCUMBER))
    );

    public static final Supplier<Item> FOOD_BOX = ITEMS.register(
            "food_box",
            () -> new Item(new Item.Properties().food(CodFoods.FOOD_BOX))
    );

    public static final Supplier<Item> LEMON = ITEMS.register(
            "lemon",
            () -> new Item(new Item.Properties().food(CodFoods.LEMON))
    );

    public static final Supplier<Item> MANGO = ITEMS.register(
            "mango",
            () -> new Item(new Item.Properties().food(CodFoods.MANGO))
    );

    public static final Supplier<Item> MRE = ITEMS.register(
            "mre",
            () -> new Item(new Item.Properties().food(CodFoods.MRE))
    );

    public static final Supplier<Item> ORANGE = ITEMS.register(
            "orange",
            () -> new Item(new Item.Properties().food(CodFoods.ORANGE))
    );

    public static final Supplier<Item> PIZZA = ITEMS.register(
            "pizza",
            () -> new Item(new Item.Properties().food(CodFoods.PIZZA))
    );

    public static final Supplier<Item> TACO = ITEMS.register(
            "taco",
            () -> new Item(new Item.Properties().food(CodFoods.TACO))
    );

    public static final Supplier<Item> TOMATO = ITEMS.register(
            "tomato",
            () -> new Item(new Item.Properties().food(CodFoods.TOMATO))
    );


    static {
        BLOCKS.getEntries().forEach(blockHolder -> {
            ITEMS.register(
                    blockHolder.getId().getPath(),
                    () -> new BlockItem(blockHolder.get(), new Item.Properties())
            );
        });
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
