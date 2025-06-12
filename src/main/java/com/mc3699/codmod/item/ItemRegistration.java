package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.detector.DetectorItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.mc3699.codmod.block.BlockRegistration.BLOCKS;

public class ItemRegistration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Codmod.MODID);

    public static final Supplier<Item> COD_ROD = ITEMS.register("cod_rod", CodRodItem::new);

    public static final Supplier<Item> NULL_COD = ITEMS.register("null_cod", () ->
            new Item(new Item.Properties().food(Foods.COD)));

    public static final Supplier<Item> MINICOD = ITEMS.register("minicod", MiniCodItem::new);

    public static final Supplier<Item> NULL_CHICKEN = ITEMS.register("null_chicken", () ->
            new Item(new Item.Properties().food(Foods.CHICKEN)));

    public static final Supplier<Item> NULL_DRIED_KELP = ITEMS.register("null_dried_kelp",
            () -> new Item(new Item.Properties().food(Foods.DRIED_KELP)));

    public static final Supplier<Item> SHRIMP = ITEMS.register("shrimp", () ->
            new Item(new Item.Properties().food(Foods.COOKED_CHICKEN).rarity(Rarity.RARE)));

    public static final Supplier<Item> APPLICATION = ITEMS.register("application", () ->
            new Item(new Item.Properties()));

    public static final Supplier<Item> INTEGRITY_COOKIE = ITEMS.register("integrity_cookie",
            IntegrityCookieItem::new);

    public static final Supplier<Item> REAL_COD = ITEMS.register("realcod", () ->
        new Item(new Item.Properties().rarity(Rarity.EPIC).food(new FoodProperties.Builder().nutrition(10000).saturationModifier(10000).alwaysEdible().build())));

    public static final Supplier<Item> UAV = ITEMS.register("uav", UAVItem::new);

    public static final Supplier<Item> DETECTOR = ITEMS.register("detector", DetectorItem::new);

    public static final Supplier<Item> BROKEN_DETECTOR = ITEMS.register("broken_detector", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> GIANNI = ITEMS.register("gianni", () -> new Item(new Item.Properties()));

    static {
        BLOCKS.getEntries().forEach(blockHolder -> {
            ITEMS.register(blockHolder.getId().getPath(), () ->
                    new BlockItem(blockHolder.get(), new Item.Properties()));
        });
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
