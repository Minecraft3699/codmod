package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CodDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTagsProvider blockProvider = generator.addProvider(
                event.includeServer(),
                new DatagenBlockTagProvider(packOutput, lookupProvider, Codmod.MOD_ID, existingFileHelper)
        );
        generator.addProvider(event.includeServer(), new DatagenLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(
                event.includeClient(),
                new DatagenItemModelProvider(packOutput, Codmod.MOD_ID, existingFileHelper)
        );

        generator.addProvider(event.includeServer(),
                new DatagenItemTagProvider(packOutput, lookupProvider, blockProvider.contentsGetter())
        );

        generator.addProvider(event.includeServer(), new DatagenSausageProvider(packOutput, lookupProvider));
    }

}
