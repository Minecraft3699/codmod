package com.mc3699.codmod.item;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Codmod.MODID);

    public static final Supplier<Item> COD_ROD = ITEMS.register("cod_rod", CodRodItem::new);
    public static final Supplier<Item> NULL_COD = ITEMS.register("null_cod", () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
