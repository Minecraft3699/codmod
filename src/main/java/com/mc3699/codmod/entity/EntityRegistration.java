package com.mc3699.codmod.entity;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.entity.applicant.ApplicantEntity;
import com.mc3699.codmod.entity.ariral.AriralEntity;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.firelight.FirelightEntity;
import com.mc3699.codmod.entity.friendlyFace.FriendlyFaceEntity;
import com.mc3699.codmod.entity.gianni.GianniEntity;
import com.mc3699.codmod.entity.misguided.MisguidedEntity;
import com.mc3699.codmod.entity.parachuteChest.ParachuteChestEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.uav.UAVEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.RedWispEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.YellowWispEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.items.ItemStackHandler;
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

    public static final Supplier<EntityType<DarkenerEntity>> DARKENER =
            ENTITIES.register("darkener", () ->
                    EntityType.Builder.of((EntityType<DarkenerEntity> entityType, Level level) -> new DarkenerEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("darkener"));

    public static final Supplier<EntityType<FriendlyFaceEntity>> FRIENDLY_FACE =
            ENTITIES.register("friendlyface", () ->
                    EntityType.Builder.of((EntityType<FriendlyFaceEntity> entityType, Level level) -> new FriendlyFaceEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("friendlyface"));

    public static final Supplier<EntityType<FirelightEntity>> FIRELIGHT =
            ENTITIES.register("firelight", () ->
                    EntityType.Builder.of((EntityType<FirelightEntity> entityType, Level level) -> new FirelightEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("firelight"));

    public static final Supplier<EntityType<AriralEntity>> ARIRAL =
            ENTITIES.register("ariral", () ->
                    EntityType.Builder.of((EntityType<AriralEntity> entityType, Level level) -> new AriralEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("ariral"));

    public static final Supplier<EntityType<ApplicantEntity>> APPLICANT =
            ENTITIES.register("applicant", () ->
                    EntityType.Builder.of((EntityType<ApplicantEntity> entityType, Level level) -> new ApplicantEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("applicant"));

    public static final Supplier<EntityType<GianniEntity>> GIANNI =
            ENTITIES.register("gianni", () ->
                    EntityType.Builder.of((EntityType<GianniEntity> entityType, Level level) -> new GianniEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("gianni"));

    public static final Supplier<EntityType<MisguidedEntity>> MISGUIDED =
            ENTITIES.register("misguided", () ->
                    EntityType.Builder.of((EntityType<MisguidedEntity> entityType, Level level) -> new MisguidedEntity(level), MobCategory.MONSTER)
                            .sized(0.5f,1.8f).build("misguided"));

    public static final Supplier<EntityType<YellowWispEntity>> YELLOW_WISP =
            ENTITIES.register("yellow_wisp", () ->
                    EntityType.Builder.of((EntityType<YellowWispEntity> entityType, Level level) -> new YellowWispEntity(level), MobCategory.MONSTER)
                            .sized(0.25f,2f).build("yellow_wisp"));

    public static final Supplier<EntityType<RedWispEntity>> RED_WISP =
            ENTITIES.register("red_wisp", () ->
                    EntityType.Builder.of((EntityType<RedWispEntity> entityType, Level level) -> new RedWispEntity(level), MobCategory.MONSTER)
                            .sized(0.25f,2f).build("red_wisp"));

    public static final Supplier<EntityType<CodMissileEntity>> COD_MISSILE =
            ENTITIES.register("cod_missile", () ->
                    EntityType.Builder.of((EntityType<CodMissileEntity> entityType, Level level) -> new CodMissileEntity(level,0,0,0, "cod_explosion", null,null), MobCategory.MONSTER)
                            .sized(0.5f,2f)
                            .clientTrackingRange(256)
                            .updateInterval(1)
                            .build("cod_missile"));

    public static final Supplier<EntityType<ParachuteChestEntity>> PARACHUTE_CHEST =
            ENTITIES.register("parachute_chest", () ->
                    EntityType.Builder.of((EntityType<ParachuteChestEntity> entityType, Level level) -> new ParachuteChestEntity(level, new ItemStackHandler()), MobCategory.MISC)
                            .sized(1f,1f)
                            .clientTrackingRange(256)
                            .updateInterval(1)
                            .build("parachute_chest"));

    public static final Supplier<EntityType<UAVEntity>> UAV =
            ENTITIES.register("uav", () ->
                    EntityType.Builder.of((EntityType<UAVEntity> entityType, Level level) -> new UAVEntity(level), MobCategory.MISC)
                            .sized(1f,1f)
                            .clientTrackingRange(1000)
                            .updateInterval(1)
                            .build("uav"));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }

}
