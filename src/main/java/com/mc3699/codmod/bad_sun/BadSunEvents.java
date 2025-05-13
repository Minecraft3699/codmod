package com.mc3699.codmod.bad_sun;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.network.CameraRotationPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class BadSunEvents {


    //TODO: finish bad sun
    @SubscribeEvent
    public static void badSunTick(ServerTickEvent.Post event)
    {
        List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();

        for(ServerPlayer player : players)
        {
            //BadSunUtil.makePlayerLookAtSun(player, player.serverLevel());
        }
    }

}
