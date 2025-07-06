package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            Codmod.MOD_ID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CODMOD_TAB =
            TABS.register(
                    "codmod_tab", () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.codmod"))
                            .icon(() -> new ItemStack(CodItems.NULL_COD.get()))
                            .displayItems(
                                    (itemDisplayParameters, output) ->
                                    {
                                        output.accept(CodItems.APPLICATION.get());
                                        output.accept(CodItems.NULL_COD.get());
                                        output.accept(CodItems.NULL_CHICKEN.get());
                                        output.accept(CodItems.NULL_DRIED_KELP.get());
                                        output.accept(CodItems.INTEGRITY_COOKIE.get());
                                        output.accept(CodItems.REAL_COD.get());
                                        output.accept(CodBlocks.RADAR.asItem());
                                        output.accept(CodBlocks.UAV_CONTROLLER.asItem());
                                    }
                            )
                            .build()
            );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VOTV_FOODS =
            TABS.register(
                    "votv_food_tab", () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.votv_food"))
                            .icon(() -> new ItemStack(CodItems.MRE.get()))
                            .displayItems(
                                    (
                                            (itemDisplayParameters, output) -> {
                                                output.accept(CodItems.BANANA.get());
                                                output.accept(CodItems.BAGUETTE.get());
                                                output.accept(CodItems.BUN.get());
                                                output.accept(CodItems.CAKE.get());
                                                output.accept(CodItems.CHEESE.get());
                                                output.accept(CodItems.CHOCOLATE.get());
                                                output.accept(CodItems.CHICKEN_NUGGET.get());
                                                output.accept(CodItems.CUCUMBER.get());
                                                output.accept(CodItems.FOOD_BOX.get());
                                                output.accept(CodItems.LEMON.get());
                                                output.accept(CodItems.MANGO.get());
                                                output.accept(CodItems.MRE.get());
                                                output.accept(CodItems.ORANGE.get());
                                                output.accept(CodItems.PIZZA.get());
                                                output.accept(CodItems.TACO.get());
                                                output.accept(CodItems.TOMATO.get());
                                            }
                                    )
                            )
                            .build()
            );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }


}
