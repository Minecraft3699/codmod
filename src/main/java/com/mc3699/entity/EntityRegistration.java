package com.mc3699.entity;

import com.mc3699.codmod.Codmod;
import com.mc3699.entity.swarmCod.SwarmCodEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EntityRegistration {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Codmod.MODID);

    public static final Supplier<EntityType<SwarmCodEntity>> SWARM_COD =
            ENTITIES.register("swarm_cod", () ->
                    EntityType.Builder.of(SwarmCodEntity::new, MobCategory.MONSTER)
                            .sized(1f,1f).build("swarm_cod"));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }

}
