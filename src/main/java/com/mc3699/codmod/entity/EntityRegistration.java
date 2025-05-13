package com.mc3699.codmod.entity;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.yellowWisp.YellowWispEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityRegistration {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Codmod.MODID);

    public static final Supplier<EntityType<SwarmCodEntity>> SWARM_COD =
            ENTITIES.register("swarm_cod", () ->
                    EntityType.Builder.of(SwarmCodEntity::new, MobCategory.MONSTER)
                            .sized(1f,1f).build("swarm_cod"));

    public static final Supplier<EntityType<VayEntity>> VAY =
            ENTITIES.register("vay", () ->
                    EntityType.Builder.of((EntityType<VayEntity> entityType, Level level) -> new VayEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("vay"));

    public static final Supplier<EntityType<TrialTraderEntity>> TRIAL_TRADER =
            ENTITIES.register("trial_trader", () ->
                    EntityType.Builder.of((EntityType<TrialTraderEntity> entityType, Level level) -> new TrialTraderEntity(level), MobCategory.MISC)
                            .sized(0.5f,1.8f).build("trial_trader"));

    public static final Supplier<EntityType<YellowWispEntity>> YELLOW_WISP =
            ENTITIES.register("yellow_wisp", () ->
                    EntityType.Builder.of((EntityType<YellowWispEntity> entityType, Level level) -> new YellowWispEntity(level), MobCategory.MONSTER)
                            .sized(0.25f,0.25f).build("yellow_wisp"));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }

}
