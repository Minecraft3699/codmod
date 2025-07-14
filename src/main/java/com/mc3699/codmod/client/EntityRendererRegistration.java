package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.backrooms.CeilingLightBlockEntityRenderer;
import com.mc3699.codmod.block.codNuke.CodNukeBlockEntityRenderer;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntityRender;
import com.mc3699.codmod.entity.applicant.ApplicantEntityRenderer;
import com.mc3699.codmod.entity.ariral.AriralEntityRenderer;
import com.mc3699.codmod.entity.codmissile.CodMissileEntityRenderer;
import com.mc3699.codmod.entity.codmissile.CodMissileModel;
import com.mc3699.codmod.entity.darkener.DarkenerEntityRenderer;
import com.mc3699.codmod.entity.firelight.FireLightEntityRenderer;
import com.mc3699.codmod.entity.friendlyFace.FriendlyFaceEntityRenderer;
import com.mc3699.codmod.entity.gianni.GianniEntityRenderer;
import com.mc3699.codmod.entity.itemProjectile.ItemProjectileRenderer;
import com.mc3699.codmod.entity.misguided.MisguidedEntityRenderer;
import com.mc3699.codmod.entity.parachuteChest.ParachuteChestEntityModel;
import com.mc3699.codmod.entity.parachuteChest.ParachuteChestEntityRenderer;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityModel;
import com.mc3699.codmod.entity.swarmCod.SwarmCodEntityRenderer;
import com.mc3699.codmod.entity.trialTrader.TrialTraderEntityRenderer;
import com.mc3699.codmod.entity.uav.UAVEntityRenderer;
import com.mc3699.codmod.entity.vay.VayEntityRenderer;
import com.mc3699.codmod.entity.wisp.BaseWispEntityRenderer;
import com.mc3699.codmod.entity.wisp.BaseWispModel;
import com.mc3699.codmod.entity.wisp.wispTypes.DistortedWispEntityRenderer;
import com.mc3699.codmod.registry.CodBlockEntities;
import com.mc3699.codmod.registry.CodEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EntityRendererRegistration {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CodEntities.SWARM_COD.get(), SwarmCodEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.VAY.get(), VayEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.TRIAL_TRADER.get(), TrialTraderEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.YELLOW_WISP.get(), DistortedWispEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.RED_WISP.get(), BaseWispEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.DARKENER.get(), DarkenerEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.FIRELIGHT.get(), FireLightEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.APPLICANT.get(), ApplicantEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.COD_MISSILE.get(), CodMissileEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.ARIRAL.get(), AriralEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.PARACHUTE_CHEST.get(), ParachuteChestEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.FRIENDLY_FACE.get(), FriendlyFaceEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.MISGUIDED.get(), MisguidedEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.UAV.get(), UAVEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.GIANNI.get(), GianniEntityRenderer::new);
        event.registerEntityRenderer(CodEntities.ITEM_PROJECTILE.get(), ItemProjectileRenderer::new);

        event.registerBlockEntityRenderer(CodBlockEntities.MANTLE_KEY.get(), MantleKeyBlockEntityRender::new);
        event.registerBlockEntityRenderer(CodBlockEntities.CEILING_LIGHT.get(), CeilingLightBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(CodBlockEntities.COD_NUKE.get(), CodNukeBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SwarmCodEntityModel.LAYER_LOCATION, SwarmCodEntityModel::createBodyLayer);
        event.registerLayerDefinition(BaseWispModel.LAYER_LOCATION, BaseWispModel::createBodyLayer);
        event.registerLayerDefinition(CodMissileModel.LAYER_LOCATION, CodMissileModel::createBodyLayer);
        event.registerLayerDefinition(
                ParachuteChestEntityModel.LAYER_LOCATION,
                ParachuteChestEntityModel::createBodyLayer
        );
    }

}
