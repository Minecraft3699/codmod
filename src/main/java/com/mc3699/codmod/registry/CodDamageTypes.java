package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class CodDamageTypes {
    public static final ResourceKey<DamageType> JOB_APPLICATION = type("job_application");
    public static final ResourceKey<DamageType> BAD_SUN = type("bad_sun");
    public static final ResourceKey<DamageType> COD_ROD = type("cod_rod");

    private static ResourceKey<DamageType> type(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Codmod.id(id));
    }
}
