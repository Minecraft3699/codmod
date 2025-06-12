package com.mc3699.codmod.bad_sun;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mc3699.codmod.registry.CodDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class BadSunEvents {
    private static Random random = new Random();
    private static long tickCount = 0;
    public static boolean isBadSunDay = false;

    private static boolean isInSun(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) return false;
        if (!player.level().isDay()) return false;
        BlockPos pos = BlockPos.containing(player.position().add(0, 1, 0));
        return player.level().canSeeSky(pos);
    }

    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (isBadSunDay && event.getEntity() instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new FoliageColorPayload(0xECD38A, false));
        }
    }

    @SubscribeEvent
    public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        ColorManager.resetFoliageColor();
        ColorManager.resetGrassColor();
    }

    @SubscribeEvent
    public static void badSunTick(ServerTickEvent.Post event) {
        tickCount++;
        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();

        ServerLevel overworld = event.getServer().overworld();

        if (overworld.isNight() && isBadSunDay) {
            isBadSunDay = false;
            PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0, true));
        }

        if (tickCount % 20 == 0 && isBadSunDay) {
            for (ServerPlayer player : players) {
                if (isInSun(player)) {

                    DamageSource badSunDamage = new DamageSource(player.level()
                            .registryAccess()
                            .lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(CodDamageTypes.BAD_SUN));

                    if (!player.isCreative()) {
                        player.hurt(badSunDamage, 2);
                        ItemStack fleshItem = new ItemStack(Items.ROTTEN_FLESH, 1);
                        ItemEntity fleshEntity = new ItemEntity(
                                player.level(),
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                fleshItem
                        );
                        fleshEntity.setPickUpDelay(20);

                        float fleshEjectionRange = 0.2f;
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

}
