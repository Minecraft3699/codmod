package com.mc3699.codmod.handlers;

import com.mc3699.codmod.network.CodVariables;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import java.util.Comparator;

public class NullFangCallbacks {
    private static final float PULL_FORCE_X = 0.025f;
    private static final float PULL_FORCE_Y = 0.02f;
    private static final float GRAB_DAMAGE_RESISTANCE_TICKS = 100;
    private static final float WITHER_DURATION_TICKS = 60;
    private static final int HOOK_DETECTION_RANGE = 50;
    private static final float GRAB_DETECT_RANGE = 1.5f;
    private static final float ENTITY_YANK_SPEED = 1.5f;

    public static void onHitEntity(LevelAccessor world, double x, double y, double z, Entity hitEntity, Entity shooter) {
        if (hitEntity == null || shooter == null) return;

        setPlayerGrappling(shooter, false);

        if (hitEntity instanceof LivingEntity living) {
            double dx = shooter.getX() - living.getX();
            double dy = shooter.getY() - living.getY();
            double dz = shooter.getZ() - living.getZ();

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance > 0) {
                dx = (dx / distance) * ENTITY_YANK_SPEED;
                dy = (dy / distance) * ENTITY_YANK_SPEED;
                dz = (dz / distance) * ENTITY_YANK_SPEED;

                double antiThing = Math.max(0.2, (distance / 10.0) * 0.5);
                dx += (dx / ENTITY_YANK_SPEED) * antiThing;
                dy += (dy / ENTITY_YANK_SPEED) * antiThing;
                dz += (dz / ENTITY_YANK_SPEED) * antiThing;
            }

            dy += 0.5;

            Vec3 motion = new Vec3(dx, dy, dz);
            living.setDeltaMovement(motion);
        }

        playHitSound(world, x, y, z, "codmod:nullfanghit", 0.55f, 1.15f);
    }

    public static void onHitBlock(LevelAccessor world, BlockPos blockPos, Entity shooter) {
        if (shooter == null) return;

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        Player nearestPlayer = findNearestPlayer(world, x, y, z, HOOK_DETECTION_RANGE);
        if (nearestPlayer != null && nearestPlayer == shooter) {
            if (!shooter.isShiftKeyDown()) {
                setHookPosition(shooter, x, y, z);
                setPlayerGrappling(shooter, true);
            }
        }

        playHitSound(world, x, y, z, "codmod:nullfangclang", 0.45f, 1.0f);
    }

    @EventBusSubscriber
    public static class GrapplingTickHandler {
        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            if (isPlayerGrappling(player)) {
                applyGrapplePull(player);
            }
        }
    }

    private static void applyGrapplePull(LivingEntity player) {
        double hx = getHookX(player);
        double hy = getHookY(player);
        double hz = getHookZ(player);

        double dx = hx - player.getX();
        double dy = hy - player.getY();
        double dz = hz - player.getZ();

        player.push(dx * PULL_FORCE_X, (dy * PULL_FORCE_Y), dz * PULL_FORCE_X);

        if (player.distanceToSqr(hx, hy, hz) < GRAB_DETECT_RANGE * GRAB_DETECT_RANGE) {
            setPlayerGrappling(player, false);
        }
    }

    public static void onMeleeHit(LevelAccessor world, double x, double y, double z, Entity hitEntity, Entity attacker) {
        if (hitEntity == null || attacker == null) return;

        playHitSound(world, x, y, z, "codmod:nullfanghit", 1.0f, 1.0f);

        if (hitEntity instanceof LivingEntity living && !living.level().isClientSide()) {
            living.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) WITHER_DURATION_TICKS, 1, true, true));
        }

        if (hitEntity instanceof LivingEntity living) {
            living.setSprinting(false);
            living.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(attacker.getX(), attacker.getY() + 1, attacker.getZ()));
        }

        if (hitEntity instanceof LivingEntity living && living.getHealth() <= 0) {
            playHitSound(world, x, y, z, "codmod:nullfanghit", 0.6f, 0.75f);
        }
    }

    public static void onRangedShot(LevelAccessor world, double x, double y, double z, Entity shooter) {
        if (shooter == null) return;

        if (shooter instanceof LivingEntity living && !living.level().isClientSide()) {
            living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int) GRAB_DAMAGE_RESISTANCE_TICKS, 0, false, false));
        }

        setPlayerGrappling(shooter, false);

        playHitSound(world, x, y, z, "codmod:nullfangthrow", 0.6f, 1.0f);
    }

    public static void onInventoryTick(Entity entity) {
        if (entity != null && entity.isShiftKeyDown()) {
            setPlayerGrappling(entity, false);
        }
    }

    private static void playHitSound(LevelAccessor world, double x, double y, double z, String soundId, float volume, float pitch) {
        if (world instanceof Level level && !level.isClientSide()) {
            level.playSound(null, BlockPos.containing(x, y, z),
                    BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)),
                    SoundSource.PLAYERS, volume, pitch);
        } else if (world instanceof Level level) {
            level.playLocalSound(x, y, z,
                    BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId)),
                    SoundSource.PLAYERS, volume, pitch, false);
        }
    }

    private static Player findNearestPlayer(LevelAccessor world, double x, double y, double z, double range) {
        return world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), range, range, range))
                .stream()
                .min(Comparator.comparingDouble(p -> p.distanceToSqr(x, y, z)))
                .orElse(null);
    }

    public static void setPlayerGrappling(Entity entity, boolean grappling) {
        CodVariables.PlayerVariables vars = entity.getData(CodVariables.PLAYER_VARIABLES);
        vars.isGrappling = grappling;
        vars.syncPlayerVariables(entity);
    }

    public static boolean isPlayerGrappling(Entity entity) {
        return entity.getData(CodVariables.PLAYER_VARIABLES).isGrappling;
    }

    private static void setHookPosition(Entity entity, double x, double y, double z) {
        CodVariables.PlayerVariables vars = entity.getData(CodVariables.PLAYER_VARIABLES);
        vars.hookx = x;
        vars.hooky = y;
        vars.hookz = z;
        vars.syncPlayerVariables(entity);
    }

    private static double getHookX(Entity entity) {
        return entity.getData(CodVariables.PLAYER_VARIABLES).hookx;
    }

    private static double getHookY(Entity entity) {
        return entity.getData(CodVariables.PLAYER_VARIABLES).hooky;
    }

    private static double getHookZ(Entity entity) {
        return entity.getData(CodVariables.PLAYER_VARIABLES).hookz;
    }
}