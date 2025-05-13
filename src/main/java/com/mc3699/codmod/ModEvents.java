package com.mc3699.codmod;

import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.yellowWisp.YellowWispEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(EntityRegistration.SWARM_COD.get(), SwarmCodEntity.createAttributes().build());
        event.put(EntityRegistration.VAY.get(), VayEntity.createAttributes().build());
        event.put(EntityRegistration.TRIAL_TRADER.get(), TrialTraderEntity.createAttributes().build());
        event.put(EntityRegistration.YELLOW_WISP.get(), YellowWispEntity.createAttributes().build());
    }

}

