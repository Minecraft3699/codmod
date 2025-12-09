package com.mc3699.codmod.item.codCannon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(value = Dist.CLIENT)
public class CodCannonListener {

    @SubscribeEvent
    public static void leftClick(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return;
        ItemStack held = player.getMainHandItem();
        if(!(held.getItem() instanceof MaliciousCodCannonItem)) return;
        PacketDistributor.sendToServer(new FireRailcannonPayload());
        event.setSwingHand(false);
        event.setCanceled(true);
    }

}
