package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.CodRodItem;
import com.mc3699.codmod.item.MiniCodItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import static com.mc3699.codmod.registry.CodBlocks.BLOCKS;

public class CodItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Codmod.MOD_ID);

    public static final Supplier<Item> COD_ROD = ITEMS.register("cod_rod", CodRodItem::new);

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

    // VOTV Foods

    public static final Supplier<Item> BURGER = ITEMS.register("burger",
            () -> new Item(new Item.Properties().food(CodFoods.BURGER)));

    public static final Supplier<Item> BAGUETTE = ITEMS.register("baguette",
            () -> new Item(new Item.Properties().food(CodFoods.BAGUETTE)));

    public static final Supplier<Item> BANANA = ITEMS.register("banana",
            () -> new Item(new Item.Properties().food(CodFoods.BANANA)));

    public static final Supplier<Item> BUN = ITEMS.register("bun",
            () -> new Item(new Item.Properties().food(CodFoods.BUN)));

    public static final Supplier<Item> CAKE = ITEMS.register("cake",
            () -> new Item(new Item.Properties().food(CodFoods.CAKE)));

    public static final Supplier<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().food(CodFoods.CHEESE)));

    public static final Supplier<Item> CHICKEN_NUGGET = ITEMS.register("chicken_nugget",
            () -> new Item(new Item.Properties().food(CodFoods.CHICKEN_NUGGET)));

    public static final Supplier<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(CodFoods.CHOCOLATE)));

    public static final Supplier<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(CodFoods.CUCUMBER)));

    public static final Supplier<Item> FOOD_BOX = ITEMS.register("food_box",
            () -> new Item(new Item.Properties().food(CodFoods.FOOD_BOX)));

    public static final Supplier<Item> LEMON = ITEMS.register("lemon",
            () -> new Item(new Item.Properties().food(CodFoods.LEMON)));

    public static final Supplier<Item> MANGO = ITEMS.register("mango",
            () -> new Item(new Item.Properties().food(CodFoods.MANGO)));

    public static final Supplier<Item> MRE = ITEMS.register("mre",
            () -> new Item(new Item.Properties().food(CodFoods.MRE)));

    public static final Supplier<Item> ORANGE = ITEMS.register("orange",
            () -> new Item(new Item.Properties().food(CodFoods.ORANGE)));

    public static final Supplier<Item> PIZZA = ITEMS.register("pizza",
            () -> new Item(new Item.Properties().food(CodFoods.PIZZA)));

    public static final Supplier<Item> TACO = ITEMS.register("taco",
            () -> new Item(new Item.Properties().food(CodFoods.TACO)));

    public static final Supplier<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().food(CodFoods.TOMATO)));


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
