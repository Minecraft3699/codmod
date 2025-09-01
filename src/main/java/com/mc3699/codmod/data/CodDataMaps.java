package com.mc3699.codmod.data;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.item.SausageItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CodDataMaps {

    public static final DataMapType<Item, SausageData> SAUSAGE_DATA_MAP = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "sausage_data"),
            Registries.ITEM,
            SausageData.CODEC)
            .synced(SausageData.CODEC, true)
            .build();

    @SubscribeEvent
    public static void registerDatamaps(RegisterDataMapTypesEvent event)
    {
        event.register(SAUSAGE_DATA_MAP);
    }

}
