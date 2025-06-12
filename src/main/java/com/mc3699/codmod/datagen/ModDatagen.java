package com.mc3699.codmod.datagen;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DatagenBlockTagProvider(packOutput,lookupProvider, Codmod.MODID, existingFileHelper));
        generator.addProvider(event.includeServer(), new DatagenLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new DatagenItemModelProvider(packOutput, Codmod.MODID, existingFileHelper));
    }

}
