package com.mc3699.codmod.handlers;


import com.mc3699.codmod.item.UmbralReaverItem;
import dev.wendigodrip.thebrokenscript.registry.TBSParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class UmbralReaverCallbacks {

    @SubscribeEvent
    public static void onEnderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        ThrownEnderpearl pearl = event.getPearlEntity();

        if (pearl.getPersistentData().getBoolean("fromReaver")) {
            event.setAttackDamage(0);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.isShiftKeyDown() && player.getMainHandItem().getItem() instanceof UmbralReaverItem) {
            if (player.level().isClientSide()) {
                for (int i = 0; i < 1; i++) {
                    double offsetX = (player.getRandom().nextDouble() - 0.5) * 3;
                    double offsetY = player.getRandom().nextDouble() * player.getBbHeight();
                    double offsetZ = (player.getRandom().nextDouble() - 0.5) * 3;

                    player.level().addParticle(
                            TBSParticleTypes.EYES.get(),
                            player.getX() + offsetX,
                            player.getY() + offsetY,
                            player.getZ() + offsetZ,
                            0, 0, 0
                    );
                }
            }
        }
    }

}
