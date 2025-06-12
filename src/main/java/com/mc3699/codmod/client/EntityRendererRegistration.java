package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.block.BlockEntityRegistration;
import com.mc3699.codmod.block.mantleKey.MantleKeyBlockEntityRender;
import com.mc3699.codmod.entity.EntityRegistration;
import com.mc3699.codmod.entity.applicant.ApplicantEntityRenderer;
import com.mc3699.codmod.entity.ariral.AriralEntityRenderer;
import com.mc3699.codmod.entity.codmissile.CodMissileEntity;
import com.mc3699.codmod.entity.codmissile.CodMissileEntityRenderer;
import com.mc3699.codmod.entity.codmissile.CodMissileModel;
import com.mc3699.codmod.entity.darkener.DarkenerEntityRenderer;
import com.mc3699.codmod.entity.firelight.FireLightEntityRenderer;
import com.mc3699.codmod.entity.friendlyFace.FriendlyFaceEntityRenderer;
import com.mc3699.codmod.entity.gianni.GianniEntityRenderer;
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
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Codmod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EntityRendererRegistration {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(EntityRegistration.SWARM_COD.get(), SwarmCodEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.VAY.get(), VayEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.TRIAL_TRADER.get(), TrialTraderEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.YELLOW_WISP.get(), DistortedWispEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.RED_WISP.get(), BaseWispEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.DARKENER.get(), DarkenerEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.FIRELIGHT.get(), FireLightEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.APPLICANT.get(), ApplicantEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.COD_MISSILE.get(), CodMissileEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.ARIRAL.get(), AriralEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.PARACHUTE_CHEST.get(), ParachuteChestEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.FRIENDLY_FACE.get(), FriendlyFaceEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.MISGUIDED.get(), MisguidedEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.UAV.get(), UAVEntityRenderer::new);
        event.registerEntityRenderer(EntityRegistration.GIANNI.get(), GianniEntityRenderer::new);


        event.registerBlockEntityRenderer(BlockEntityRegistration.MANTLE_KEY.get(), MantleKeyBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(SwarmCodEntityModel.LAYER_LOCATION, SwarmCodEntityModel::createBodyLayer);
        event.registerLayerDefinition(BaseWispModel.LAYER_LOCATION, BaseWispModel::createBodyLayer);
        event.registerLayerDefinition(CodMissileModel.LAYER_LOCATION, CodMissileModel::createBodyLayer);
        event.registerLayerDefinition(ParachuteChestEntityModel.LAYER_LOCATION, ParachuteChestEntityModel::createBodyLayer);
    }

}
