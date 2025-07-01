package com.mc3699.codmod.network;

import com.mc3699.codmod.entity.wisp.BaseWispEntity;
import foundry.veil.api.client.render.light.Light;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.WeakHashMap;

record HeadlampLightData(Light light, Vec3 position, Vec3 orientation) {}

public class ClientLightHandler {

    private static final Map<HeadlampLightData, Player> playerHeadlampLights = new WeakHashMap<>();


}
