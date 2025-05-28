package com.mc3699.codmod.effect;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegistration {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Codmod.MODID);

    public static final DeferredHolder<MobEffect, HeartCorruptionEffect> HEART_CORRUPTION = MOB_EFFECTS.register("heart_corruption", HeartCorruptionEffect::new);

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }

}
