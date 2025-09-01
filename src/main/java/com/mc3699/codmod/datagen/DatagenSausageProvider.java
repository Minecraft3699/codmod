package com.mc3699.codmod.datagen;

import com.mc3699.codmod.data.CodDataMaps;
import com.mc3699.codmod.data.SausageData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class DatagenSausageProvider extends DataMapProvider {
    protected DatagenSausageProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(CodDataMaps.SAUSAGE_DATA_MAP)
                .add(Items.BEEF.builtInRegistryHolder(),new SausageData(0xFF0000, 4, 2, 0.2f), false)
                .add(Items.CHICKEN.builtInRegistryHolder(), new SausageData(0xBB0000, 2, 1, 0.8f), false)
                .add(Items.APPLE.builtInRegistryHolder(), new SausageData(0x0000FF, 3, 2, 0.1f), false)
                .add(Items.GOLDEN_APPLE.builtInRegistryHolder(), new SausageData(0x00FF00,10,15,0), false)
                .add(Items.COD.builtInRegistryHolder(), new SausageData(0xFFFFFF,15,10, -10), false)
                .add(Items.MELON_SLICE.builtInRegistryHolder(), new SausageData(0xc49077, 2, 1, 0.2f), false)
                .add(Items.SWEET_BERRIES.builtInRegistryHolder(), new SausageData(0x370e0f, 1, 3, 0.3f), false)
                .add(Items.GLOW_BERRIES.builtInRegistryHolder(), new SausageData(0xee9444, 1, 4, 0.4f), false)
                .add(Items.CHORUS_FRUIT.builtInRegistryHolder(), new SausageData(0x775877, 2, 2, 0.9f), false)
                .add(Items.CARROT.builtInRegistryHolder(), new SausageData(0xfc8c09, 4, 2, 0.8f), false)
                .add(Items.GOLDEN_CARROT.builtInRegistryHolder(), new SausageData(0xe7eb56, 12, 24, -0.5f), false)
                .add(Items.POTATO.builtInRegistryHolder(), new SausageData(0xc69539, 3, 3, 0.2f), false);
        
    }
}
