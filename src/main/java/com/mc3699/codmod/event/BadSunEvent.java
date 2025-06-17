package com.mc3699.codmod.event;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.handlers.BadSunEvents;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mc3699.codmod.registry.CodSounds;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BadSunEvent extends BaseEvent {
    public BadSunEvent() {
        super(0);
    }

    @Override
    public void execute(ServerLevel level, ServerPlayer player, Vec3 vec3) {
        level.setDayTime(22740);

        // >:3 - I called them victims
        for (ServerPlayer victim : level.getServer().getPlayerList().getPlayers()) {
            victim.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    CodSounds.BAD_SUN_SIREN.get(),
                    SoundSource.PLAYERS,
                    1,
                    1
            );
        }

        Codmod.SERVER_QUEUE.add(
                63 * 20, () -> {
                    PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0xECD38A, false));
                    BadSunEvents.isBadSunDay = true;
                }
        );
    }
}
