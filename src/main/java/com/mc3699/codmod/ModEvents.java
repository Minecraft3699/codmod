package com.mc3699.codmod;

import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.YellowWispEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Codmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(EntityRegistration.SWARM_COD.get(), SwarmCodEntity.createAttributes().build());
        event.put(EntityRegistration.VAY.get(), VayEntity.createAttributes().build());
        event.put(EntityRegistration.TRIAL_TRADER.get(), TrialTraderEntity.createAttributes().build());
        event.put(EntityRegistration.YELLOW_WISP.get(), YellowWispEntity.createAttributes().build());
        event.put(EntityRegistration.RED_WISP.get(), YellowWispEntity.createAttributes().build());
        event.put(EntityRegistration.DARKENER.get(), DarkenerEntity.createAttributes().build());
    }

}

