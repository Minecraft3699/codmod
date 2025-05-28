package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockRegistration;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ItemRegistration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Codmod.MODID);

    public static final Supplier<Item> COD_ROD = ITEMS.register("cod_rod", CodRodItem::new);

    public static final Supplier<Item> DELL_SERVER =ITEMS.register("dell_server", ()-> new BlockItem(BlockRegistration.DELL_SERVER.get(), new Item.Properties()));

    public static final Supplier<Item> MANTLE_KEY = ITEMS.register("mantle_key", () -> new BlockItem(BlockRegistration.MANTLE_KEY.get(), new Item.Properties()));

    public static final Supplier<Item> NULL_COD = ITEMS.register("null_cod", () ->
            new Item(new Item.Properties().food(Foods.COD)));

    public static final Supplier<Item> MINICOD = ITEMS.register("minicod", MiniCodItem::new);

    public static final Supplier<Item> NULL_CHICKEN = ITEMS.register("null_chicken", () ->
            new Item(new Item.Properties().food(Foods.CHICKEN)));

    public static final Supplier<Item> NULL_DRIED_KELP = ITEMS.register("null_dried_kelp",
            () -> new Item(new Item.Properties().food(Foods.DRIED_KELP)));

    public static final Supplier<Item> APPLICATION = ITEMS.register("application", () ->
            new Item(new Item.Properties()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
