package com.mc3699.codmod.event;

import com.mc3699.codmod.bad_sun.BadSunEvents;
import com.mc3699.codmod.bad_sun.DelayUtil;
import com.mc3699.codmod.network.FoliageColorPayload;
import com.mc3699.codmod.sound.SoundRegistration;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class BadSunEvent extends BaseEvent {
    public BadSunEvent() {
        super(0);
    }

    @Override
    public void execute(@NotNull Level level, @NotNull Entity entity, @NotNull Vec3 vec3) {

        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.setDayTime(22740);

            for(ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers())
            {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistration.BAD_SUN_SIREN.get(), SoundSource.PLAYERS, 1, 1);
            }

            DelayUtil.schedule(serverLevel, (63*20), () ->
            {
                PacketDistributor.sendToAllPlayers(new FoliageColorPayload(0xECD38A, false));
                BadSunEvents.isBadSunDay = true;
            });
        }

    }
}
