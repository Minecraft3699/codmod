package com.mc3699.codmod.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.sounds.SoundEvent;

public class CodSounds {
    public static final RegistryEntry<SoundEvent, SoundEvent> BAD_SUN_SIREN = CodRegistrate.INSTANCE.sound(
            "bad_sun_siren").simple(false).subtitle("Ominous Siren Sounds").register();

    public static final RegistryEntry<SoundEvent, SoundEvent> MISSILE_ENTRY = CodRegistrate.INSTANCE.sound(
            "missile_entry").simple(false).register();

    public static final RegistryEntry<SoundEvent, SoundEvent> MISSILE_LAUNCH = CodRegistrate.INSTANCE.sound(
            "missile_launch").simple(false).register();

    public static final RegistryEntry<SoundEvent, SoundEvent> FIDDLESTICKS = CodRegistrate.INSTANCE.sound(
            "fiddlesticks").simple(false).subtitle("Oh Fiddlesticks, What now?").register();

    public static final RegistryEntry<SoundEvent, SoundEvent> ANYTHING = CodRegistrate.INSTANCE.sound("anything")
            .simple(false)
            .register();

    public static void register() {
        // This method just exists so Java will load the class LOL
        // Since it doesn't actually need to "push" anything to the registry manually anymore
    }
}
