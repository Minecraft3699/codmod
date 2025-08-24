package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.client.EntropyEvents;
import com.mc3699.codmod.datagen.DatagenItemTagProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class OxygenHandler {


    private static final String OXYGEN_KEY = "oxygen_saturation";
    private static final int oxygenDamageDelay = 40;
    private static int oxygenDamage = oxygenDamageDelay;

    @SubscribeEvent
    public static void oxygenTick(ServerTickEvent.Post event)
    {

            ServerLevel serverLevel = event.getServer().getLevel(EntropyEvents.ENTROPY_KEY);

            oxygenDamage--;
            List<LivingEntity> entropyEntities = new ArrayList<>();
            serverLevel.getAllEntities().forEach(entity -> {
                if(entity instanceof LivingEntity livingEntity) entropyEntities.add(livingEntity);
            });

            for(LivingEntity entity : entropyEntities) {

                CompoundTag data = entity.getPersistentData();


                if(entity instanceof Player player)
                {
                    if(checkPlayerSuit(player)) { data.putInt(OXYGEN_KEY, 300); }
                }

                int oxygen = data.contains(OXYGEN_KEY) ? data.getInt(OXYGEN_KEY) : 300;
                if (oxygen > 0) oxygen--;

                // damage at zero
                if (oxygen == 0 && oxygenDamage == 0) {
                    entity.hurt(serverLevel.damageSources().drown(), 2);
                }

                if (oxygenDamage <= 0) {
                    oxygenDamage = oxygenDamageDelay;
                }

                data.putInt(OXYGEN_KEY, oxygen);
            }
    }

    private static boolean checkPlayerSuit(Player player)
    {
        for(ItemStack armor : player.getArmorSlots())
        {
            if(!armor.is(DatagenItemTagProvider.SPACE_SUIT_VALID)) return false;
        }
        return true;
    }

}
