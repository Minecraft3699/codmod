package com.mc3699.codmod.registry;

import com.mc3699.codmod.entity.applicant.ApplicantEntity;
import com.mc3699.codmod.entity.ariral.AriralEntity;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.firelight.FirelightEntity;
import com.mc3699.codmod.entity.friendlyFace.FriendlyFaceEntity;
import com.mc3699.codmod.entity.gianni.GianniEntity;
import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.entity.misguided.MisguidedEntity;
import com.mc3699.codmod.entity.parachuteChest.ParachuteChestEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.uav.UAVEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.RedWispEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.YellowWispEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CodEntities {
    public static final EntityEntry<VayEntity> VAY = CodRegistrate.INSTANCE.entity(
                    "vay",
                    VayEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 0.5f))
            .lang("Ζεστός Χαλκός")
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<TrialTraderEntity> TRIAL_TRADER = CodRegistrate.INSTANCE.entity(
                    "trial_trader",
                    TrialTraderEntity::new,
                    MobCategory.MISC
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .lang("Trial Trader")
            .register();
    public static final EntityEntry<DarkenerEntity> DARKENER = CodRegistrate.INSTANCE.entity(
                    "darkener",
                    DarkenerEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .lang("§kheywhyyoulookatcodmodsource?")
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();    public static final EntityEntry<SwarmCodEntity> SWARM_COD = CodRegistrate.INSTANCE.entity(
                    "swarm_cod",
                    SwarmCodEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(1f, 1f))
            .lang("Swarm Cod")
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<FriendlyFaceEntity> FRIENDLY_FACE = CodRegistrate.INSTANCE.entity(
                    "friendlyface",
                    FriendlyFaceEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<FirelightEntity> FIRELIGHT = CodRegistrate.INSTANCE.entity(
                    "firelight",
                    FirelightEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .lang("Fir§keLi§fght34§k52§f3")
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<AriralEntity> ARIRAL = CodRegistrate.INSTANCE.entity(
                    "ariral",
                    AriralEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<ApplicantEntity> APPLICANT = CodRegistrate.INSTANCE.entity(
                    "applicant",
                    ApplicantEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<GianniEntity> GIANNI = CodRegistrate.INSTANCE.entity(
                    "gianni",
                    GianniEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .lang("Gianni")
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<MisguidedEntity> MISGUIDED = CodRegistrate.INSTANCE.entity(
                    "misguided",
                    MisguidedEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 1.8f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<YellowWispEntity> YELLOW_WISP = CodRegistrate.INSTANCE.entity(
                    "yellow_wisp",
                    YellowWispEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.25f, 2f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<RedWispEntity> RED_WISP = CodRegistrate.INSTANCE.entity(
                    "red_wisp",
                    RedWispEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.25f, 2f))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<CodMissileEntity> COD_MISSILE = CodRegistrate.INSTANCE.entity(
                    "cod_missile",
                    (EntityType<CodMissileEntity> ty, Level lvl) -> new CodMissileEntity(
                            ty,
                            lvl,
                            0,
                            0,
                            0,
                            "cod_explosion",
                            null,
                            null
                    ),
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(0.5f, 2f)
                    .clientTrackingRange(256)
                    .updateInterval(1))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<ParachuteChestEntity> PARACHUTE_CHEST = CodRegistrate.INSTANCE.entity(
                    "parachute_chest",
                    (EntityType<ParachuteChestEntity> ty, Level lvl) -> new ParachuteChestEntity(
                            ty,
                            lvl,
                            new ItemStackHandler()
                    ),
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(1f, 2f).clientTrackingRange(256).updateInterval(1))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<UAVEntity> UAV = CodRegistrate.INSTANCE.entity(
                    "uav",
                    UAVEntity::new,
                    MobCategory.MONSTER
            )
            .properties((it) -> it.sized(1f, 1f).clientTrackingRange(1000).updateInterval(1))
            .loot((
                    (registrateEntityLootTables, type) ->
                            registrateEntityLootTables.add(type, new LootTable.Builder())
            ))
            .register();
    public static final EntityEntry<ItemProjectileEntity> ITEM_PROJECTILE = CodRegistrate.INSTANCE.entity(
                    "item_projectile",
                    (EntityType<ItemProjectileEntity> ty, Level lvl) -> new ItemProjectileEntity(
                            ty,
                            lvl,
                            new ItemStack(Items.COD, 1),
                            0,
                            0,
                            true
                    ),
                    MobCategory.MISC
            )
            .properties((it) -> it.sized(0.25f, 0.25f))
            .register();

    public static void register() {
        // Hooray! Force Java to load the class!
    }





}
