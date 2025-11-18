package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.effect.BleedingEffect;
import com.mc3699.codmod.effect.HeartCorruptionEffect;
import com.mc3699.codmod.effect.InsanityMobEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CodMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT,
            Codmod.MOD_ID
    );

    public static final DeferredHolder<MobEffect, HeartCorruptionEffect> HEART_CORRUPTION = MOB_EFFECTS.register(
            "heart_corruption",
            HeartCorruptionEffect::new
    );

    public static final DeferredHolder<MobEffect, InsanityMobEffect> INSANITY = MOB_EFFECTS.register(
            "insanity",
            InsanityMobEffect::new
    );

    public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING = MOB_EFFECTS.register(
            "bleeding",
            BleedingEffect::new
    );

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
