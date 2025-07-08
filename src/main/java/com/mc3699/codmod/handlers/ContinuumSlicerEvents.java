package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Vector3f;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Codmod.MOD_ID)
public class ContinuumSlicerEvents {

    @SubscribeEvent
    public static void fallEvent(LivingFallEvent event)
    {
        if(event.getEntity() instanceof Player player)
        {
            if(player.getPersistentData().getBoolean("NoFall"))
            {
                event.setDamageMultiplier(0);
                player.getPersistentData().putBoolean("NoFall", false);
            }
        }
    }

    @SubscribeEvent
    public static void fallingTick(PlayerTickEvent.Post event)
    {
        if(event.getEntity().getPersistentData().getBoolean("NoFall"))
        {
            if(event.getEntity().level() instanceof ServerLevel serverLevel)
            {
                serverLevel.sendParticles(
                        new DustParticleOptions(new Vector3f(1.0F, 0.0F, 1.0F), 1F),
                        event.getEntity().getX() + 0.5, event.getEntity().getY() + 0.5, event.getEntity().getZ() + 0.5,
                        8, // Count
                        0.0, 0.5, 0.0, // Spread
                        20.0 // Speed
                );
            }
        }

        if(event.getEntity().isCreative())
        {
            event.getEntity().getPersistentData().putBoolean("NoFall",false);
        }
    }

}
