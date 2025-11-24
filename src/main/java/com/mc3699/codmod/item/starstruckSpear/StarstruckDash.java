package com.mc3699.codmod.item.starstruckSpear;

import com.mc3699.codmod.network.CodVariables;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class StarstruckDash {
    public static void performDash(LivingEntity player, int charge) {
        CodVariables.PlayerVariables vars = player.getData(CodVariables.PLAYER_VARIABLES);
        vars.isDashing = true;
        vars.dashTicksRemaining = 30;
        double dashSpeed = 0;
        if (charge >= 10 && charge < 20) {
            dashSpeed = 1.25;
        } else if (charge >= 20) {
            dashSpeed = 2.1;
        }
        Vec3 lookDirection = player.getLookAngle();
        Vec3 dashVelocity = lookDirection.scale(dashSpeed);

        dashVelocity = new Vec3(
                dashVelocity.x,
                dashVelocity.y * 0.75,
                dashVelocity.z
        );

        player.setDeltaMovement(dashVelocity);
        player.hurtMarked = true;
    }

    public static boolean isPlayerDashing(Entity entity) {
        return entity.getData(CodVariables.PLAYER_VARIABLES).isDashing;
    }

    @EventBusSubscriber
    private static class PlayerTickHandler {

        @SubscribeEvent
        private static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            if (isPlayerDashing(player)) {

                CodVariables.PlayerVariables vars = player.getData(CodVariables.PLAYER_VARIABLES);
                vars.dashTicksRemaining--;

                if (vars.dashTicksRemaining <= 0) {
                    vars.isDashing = false;
                } else {
                    AABB playerBox = player.getBoundingBox();
                    playerBox.inflate(2);

                    List<Entity> collidingEntities = player.level().getEntities(player, playerBox);
                    for (Entity entity : collidingEntities) {
                        if (entity instanceof LivingEntity target) {
                            DamageSource dashDamage = player.damageSources().playerAttack(player);
                            target.hurt(dashDamage, 5.5f);
                        }
                    }
                }
            }
        }
    }
}
