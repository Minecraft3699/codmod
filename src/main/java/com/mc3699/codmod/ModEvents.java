package com.mc3699.codmod;

import com.mc3699.codmod.entity.applicant.ApplicantEntity;
import com.mc3699.codmod.entity.ariral.AriralEntity;
import com.mc3699.codmod.entity.cod_almighty.CodAlmightyEntity;
import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.firelight.FirelightEntity;
import com.mc3699.codmod.entity.friendlyFace.FriendlyFaceEntity;
import com.mc3699.codmod.entity.gianni.GianniEntity;
import com.mc3699.codmod.entity.misguided.MisguidedEntity;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntity;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.entity.wisp.wispTypes.YellowWispEntity;
import com.mc3699.codmod.registry.CodEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CodEntities.SWARM_COD.get(), SwarmCodEntity.createAttributes().build());
        event.put(CodEntities.VAY.get(), VayEntity.createAttributes().build());
        event.put(CodEntities.TRIAL_TRADER.get(), TrialTraderEntity.createAttributes().build());
        event.put(CodEntities.YELLOW_WISP.get(), YellowWispEntity.createAttributes().build());
        event.put(CodEntities.RED_WISP.get(), YellowWispEntity.createAttributes().build());
        event.put(CodEntities.DARKENER.get(), DarkenerEntity.createAttributes().build());
        event.put(CodEntities.FIRELIGHT.get(), FirelightEntity.createAttributes().build());
        event.put(CodEntities.APPLICANT.get(), ApplicantEntity.createAttributes().build());
        event.put(CodEntities.ARIRAL.get(), AriralEntity.createAttributes().build());
        event.put(CodEntities.FRIENDLY_FACE.get(), FriendlyFaceEntity.createAttributes().build());
        event.put(CodEntities.GIANNI.get(), GianniEntity.createAttributes().build());
        event.put(CodEntities.MISGUIDED.get(), MisguidedEntity.createAttributes().build());
        event.put(CodEntities.COD_ALMIGHTY.get(), CodAlmightyEntity.createAttributes().build());
    }
}
