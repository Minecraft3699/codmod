package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.colors.ColorManager;
import com.mc3699.codmod.network.ClientBadSunSyncPayload;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mc3699.codmod.registry.CodDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BadSunEvents {
    private static final Random random = new Random();
    public static boolean isBadSunDay = false;
    private static long tickCount = 0;

    private static boolean isInSun(ServerPlayer player) {
        if (player.isCreative() || player.isSpectator()) return false;

        MinecraftServer server = player.getServer();

        if (server == null) return false;
        if (!player.level().isDay()) return false;

        ServerLevel level = player.serverLevel();

        Vec3 sunPos = getSunDirection(level, 0).scale(1000);

        return level.canSeeSky(player.blockPosition()) && level.clip(new ClipContext(
                player.position().add(new Vec3(0, 1.62, 0)),
                sunPos,
                ClipContext.Block.VISUAL,
                ClipContext.Fluid.NONE,
                player
        )).getType() == HitResult.Type.MISS;
    }

    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (isBadSunDay && event.getEntity() instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new FoliageColorPayload(0xECD38A, false));
            PacketDistributor.sendToPlayer(serverPlayer, new ClientBadSunSyncPayload(isBadSunDay));
        }
    }

    @SubscribeEvent
    public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        ColorManager.resetFoliageColor();
        ColorManager.resetGrassColor();
    }


    public static Vec3 getSunDirection(Level level, float partialTicks) {
        float sunAngle = level.getSunAngle(partialTicks);
        double x = -Math.sin(sunAngle);
        double y = Math.cos(sunAngle);
        double z = 0;

        return new Vec3(x, y, z).normalize();
    }

    @SubscribeEvent
    public static void removeKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof Player player) {
            DamageSource badSunDamage = new DamageSource(player.level()
                    .registryAccess()
                    .lookupOrThrow(Registries.DAMAGE_TYPE)
                    .getOrThrow(CodDamageTypes.BAD_SUN));

            if (player.getLastDamageSource() != null) {
                if (player.getLastDamageSource().type().equals(badSunDamage.type())) {
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void badSunTick(ServerTickEvent.Post event) {
        tickCount++;
        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();

        ServerLevel overworld = event.getServer().overworld();

        if (overworld.isNight() && isBadSunDay) {
            isBadSunDay = false;
            PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0, true));
            PacketDistributor.sendToAllPlayers(new ClientBadSunSyncPayload(isBadSunDay));
        }

        if (tickCount % 20 == 0 && isBadSunDay) {
            for (ServerPlayer player : players) {
                if (isInSun(player)) {
                    PacketDistributor.sendToPlayer(player, new ClientBadSunSyncPayload(isBadSunDay));

                    DamageSource badSunDamage = new DamageSource(player.level()
                            .registryAccess()
                            .lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(CodDamageTypes.BAD_SUN));

                    player.hurt(badSunDamage, 2f);
                    ItemStack fleshItem = new ItemStack(Items.ROTTEN_FLESH, 1);
                    ItemEntity fleshEntity = new ItemEntity(
                            player.level(),
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            fleshItem
                    );
                    fleshEntity.setPickUpDelay(20);

                    float fleshEjectionRange = 0.25f;
                    float randX = random.nextFloat(-fleshEjectionRange, fleshEjectionRange);
                    float randY = random.nextFloat(0, fleshEjectionRange);
                    float randZ = random.nextFloat(-fleshEjectionRange, fleshEjectionRange);

                    player.level().addFreshEntity(fleshEntity);
                    fleshEntity.setDeltaMovement(randX, randY, randZ);
                }
            }
        }
    }
}
