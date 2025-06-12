package com.mc3699.codmod.creativetab;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockRegistration;
import com.mc3699.codmod.item.ItemRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodmodCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Codmod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CODMOD_TAB =
            TABS.register("codmod_tab", ()-> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.codmod"))
                    .icon(() -> new ItemStack(ItemRegistration.NULL_COD.get()))
                    .displayItems(
                            (itemDisplayParameters, output) ->
                            {

                                output.accept(ItemRegistration.COD_ROD.get());
                                output.accept(ItemRegistration.APPLICATION.get());
                                output.accept(ItemRegistration.NULL_COD.get());
                                output.accept(ItemRegistration.NULL_CHICKEN.get());
                                output.accept(ItemRegistration.NULL_DRIED_KELP.get());
                                output.accept(ItemRegistration.INTEGRITY_COOKIE.get());
                                output.accept(ItemRegistration.REAL_COD.get());
                                output.accept(BlockRegistration.RADAR.asItem());
                                output.accept(BlockRegistration.UAV_CONTROLLER.asItem());

                            }
                    )
                    .build());

    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }


}
