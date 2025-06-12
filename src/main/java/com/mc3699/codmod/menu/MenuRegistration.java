package com.mc3699.codmod.menu;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.launchpad.LaunchPadBlockEntity;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuRegistration {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Codmod.MODID);


}
