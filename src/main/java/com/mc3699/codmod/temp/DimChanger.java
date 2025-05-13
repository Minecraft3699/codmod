package com.mc3699.codmod.temp;

import com.mc3699.codmod.Codmod;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.List;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DimChanger {

    // thebrokenscript:day_b
    // thebrokenscript:clan_void
    // thebrokenscript:null_torture


    private static final List<ResourceLocation> BROKEN_DIMENSIONS = List.of(
            ResourceLocation.fromNamespaceAndPath("thebrokenscript", "day_b"),
            ResourceLocation.fromNamespaceAndPath("thebrokenscript", "day_a"),
            ResourceLocation.fromNamespaceAndPath("thebrokenscript", "clan_void"),
            ResourceLocation.fromNamespaceAndPath("thebrokenscript", "null_torture")
    );

    private static final Vec3 SPAWN_LOCATION = new Vec3(-200,71,56);


    @SubscribeEvent
    public static void disableDims(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        ServerLevel overworld = event.getEntity().getServer().getLevel(ServerLevel.OVERWORLD);
        if(BROKEN_DIMENSIONS.contains(event.getTo().location()))
        {
            event.getEntity().teleportTo(overworld, SPAWN_LOCATION.x, SPAWN_LOCATION.y, SPAWN_LOCATION.z, RelativeMovement.ALL, 0,0);
        }

    }

}
