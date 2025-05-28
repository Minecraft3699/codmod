package com.mc3699.codmod.sound;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SoundRegistration {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Codmod.MODID);



    public static final Supplier<SoundEvent> BAD_SUN_SIREN = registerSoundEvent("bad_sun_siren");



    private static Supplier<SoundEvent> registerSoundEvent(String name)
    {
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Codmod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

}
