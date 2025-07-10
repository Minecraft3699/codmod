package com.mc3699.codmod.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.resources.sounds.Sound;
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

    public static final RegistryEntry<SoundEvent, SoundEvent> TRIPMINE = CodRegistrate.INSTANCE.sound(
            "tripmine").simple(false).subtitle("Subspace Tripmine Explodes").register();

    public static final RegistryEntry<SoundEvent, SoundEvent> SAUL = CodRegistrate.INSTANCE.sound(
            "saul").simple(false).subtitle("Saul Goodman Noises").register();

    public static final RegistryEntry<SoundEvent, SoundEvent> ANYTHING = CodRegistrate.INSTANCE.sound("anything")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> GEOMETRY = CodRegistrate.INSTANCE.sound("geometry")
            .simple(false)
            .register();

    // sound submitted by @9272016
    public static final RegistryEntry<SoundEvent, SoundEvent> DISK_2016 = CodRegistrate.INSTANCE.sound("9_27_2016")
            .simple(false)
            .subtitle("? ? ?")
            .register();

    public static void register() {
        // This method just exists so Java will load the class LOL
        // Since it doesn't actually need to "push" anything to the registry manually anymore
    }
}
