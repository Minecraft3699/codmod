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

    public static final RegistryEntry<SoundEvent, SoundEvent> BEEP = CodRegistrate.INSTANCE.sound("beep")
            .simple(false)
            .subtitle("Beep")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> DETONATION = CodRegistrate.INSTANCE.sound("detonation")
            .simple(false)
            .subtitle("Cod Nuke prepares to detonate")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> COIN = CodRegistrate.INSTANCE.sound("coin")
            .simple(false)
            .subtitle("Spare Cod")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> NULL_CHICKEN_BLAST = CodRegistrate.INSTANCE.sound("null_chicken_blast")
            .simple(false)
            .subtitle("Null Chicken Blast")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> TRANSPONDER_CALL = CodRegistrate.INSTANCE.sound("transponder_call")
            .simple(false)
            .subtitle("Transponder Rings")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> TRANSPONDER_ALERT = CodRegistrate.INSTANCE.sound("transponder_alert")
            .simple(false)
            .subtitle("Transponder Alerts")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> DESIGNATE_TARGET = CodRegistrate.INSTANCE.sound("designate_target")
            .simple(false)
            .subtitle("Target Designator Transmits...")
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> GUNSHOT = CodRegistrate.INSTANCE.sound("gunshot")
            .simple(false)
            .subtitle("Gunshot")
            .register();
    public static final RegistryEntry<SoundEvent, SoundEvent> NULLFANGHIT = CodRegistrate.INSTANCE.sound("nullfanghit")
            .simple(false)
            .register();
    public static final RegistryEntry<SoundEvent, SoundEvent> NULLFANGTHROW = CodRegistrate.INSTANCE.sound("nullfangthrow")
            .simple(false)
            .register();
    public static final RegistryEntry<SoundEvent, SoundEvent> NULLFANGCLANG = CodRegistrate.INSTANCE.sound("nullfangclang")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> CAPOSWING = CodRegistrate.INSTANCE.sound("caposwing")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> CAPOHIT = CodRegistrate.INSTANCE.sound("capohit")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> CAPOKILL = CodRegistrate.INSTANCE.sound("capokill")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> ORCHWORK = CodRegistrate.INSTANCE.sound("orchwork")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> INSANE = CodRegistrate.INSTANCE.sound("insane")
            .simple(false)
            .register();

    public static final RegistryEntry<SoundEvent, SoundEvent> PLINKYPOO = CodRegistrate.INSTANCE.sound("plinkypoo")
            .simple(false)
            .register();
    public static final RegistryEntry<SoundEvent, SoundEvent> TRIPMINE_ARMED = CodRegistrate.INSTANCE.sound("tripmine_armed")
            .simple(false)
            .register();

    public static void register() {
        // This method just exists so Java will load the class LOL
        // Since it doesn't actually need to "push" anything to the registry manually anymore
    }
}
