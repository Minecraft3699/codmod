package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.dimension.DimensionKeys;
import com.mc3699.codmod.datagen.DatagenItemTagProvider;
import com.mc3699.codmod.item.OxygenTankItem;
import com.mc3699.codmod.network.OxygenClientPayload;
import com.mc3699.codmod.registry.CodDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class OxygenHandler {

    public static final List<ResourceKey<Level>> OXYGEN_DEPRIVED_DIMENSIONS = List.of(
            DimensionKeys.ENTROPY
    );

    public static final String OXYGEN_KEY = "oxygen_saturation";
    private static final int oxygenDamageDelay = 20;
    private static int oxygenDamage = oxygenDamageDelay;


    @SubscribeEvent
    public static void cancelKnockback(LivingKnockBackEvent event) {

        if (event.getEntity().getLastDamageSource() != null) {
            if (event.getEntity().getLastDamageSource().is(CodDamageTypes.NO_OXYGEN)) {
                event.setCanceled(true);
            }
        }

    }


    @SubscribeEvent
    public static void oxygenTick(ServerTickEvent.Post event) {

        oxygenDamage--;
        for (ResourceKey<Level> dimension : OXYGEN_DEPRIVED_DIMENSIONS) {
            ServerLevel serverLevel = event.getServer().getLevel(dimension);
            if(serverLevel == null) return;

            List<LivingEntity> livingEntities = new ArrayList<>();

            serverLevel.getAllEntities().forEach(entity -> {
                if (entity instanceof LivingEntity livingEntity) livingEntities.add(livingEntity);
            });

            for (LivingEntity entity : livingEntities) {

                CompoundTag data = entity.getPersistentData();

                int oxygen = data.contains(OXYGEN_KEY) ? data.getInt(OXYGEN_KEY) : 300;

                if (entity instanceof ServerPlayer player) {
                    if (checkPlayerSuit(player)) {

                        if (player.tickCount % 20 == 0 && oxygen < 200) {

                            for (ItemStack stack : player.getInventory().items) {
                                if (stack.getItem() instanceof OxygenTankItem tank) {
                                    if (tank.consumeOxygen(stack, 4)) {
                                        oxygen = Math.min(300, oxygen + 100);
                                        break;
                                    }
                                }
                            }
                        }

                    }
                    PacketDistributor.sendToPlayer(player, new OxygenClientPayload(oxygen));
                }

                if (oxygen == 0) {

                    DamageSource oxygenDamage = new DamageSource(entity.level()
                            .registryAccess()
                            .lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(CodDamageTypes.NO_OXYGEN));

                    entity.hurt(oxygenDamage, 2);
                }

                if (oxygen > 0) oxygen--;


                if (oxygenDamage <= 0) {
                    oxygenDamage = oxygenDamageDelay;
                }
                data.putInt(OXYGEN_KEY, oxygen);
            }
        }


    }

    private static boolean checkPlayerSuit(Player player) {
        for (ItemStack armor : player.getArmorSlots()) {
            if (!armor.is(DatagenItemTagProvider.SPACE_SUIT_VALID)) return false;
        }
        return true;
    }

}
