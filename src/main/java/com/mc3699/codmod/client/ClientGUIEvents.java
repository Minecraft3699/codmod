package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.Random;

@EventBusSubscriber(modid = Codmod.MOD_ID, value = Dist.CLIENT)
public class ClientGUIEvents {

    private static final ResourceLocation CORRUPTED_HEART = ResourceLocation.fromNamespaceAndPath(
            Codmod.MOD_ID,
            "textures/gui/corrupt_full.png"
    );
    private static final ResourceLocation VANILLA_HEART = ResourceLocation.fromNamespaceAndPath(
            "minecraft",
            "textures/gui/sprites/hud/heart/full.png"
    );
    private static final Random random = new Random();


    @SubscribeEvent
    public static void onRenderGUI(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH)) {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.hasEffect(CodMobEffects.HEART_CORRUPTION)) {
                event.setCanceled(true);
                renderCustomHearts(event.getGuiGraphics(), player);
            }
        }
    }

    private static void renderCustomHearts(GuiGraphics guiGraphics, Player player) {

        int maxHealth = (int) player.getMaxHealth();
        int health = (int) player.getHealth();
        int corruptedHearts = (20 - maxHealth) / 2; // 2 health = 1 heart
        int x = guiGraphics.guiWidth() / 2 - 91; // Vanilla heart position
        int y = guiGraphics.guiHeight() - 39;

        for (int i = 0; i < corruptedHearts; i++) {

            int randX = random.nextInt(-5, 5);
            int randY = random.nextInt(-5, 5);

            guiGraphics.blit(CORRUPTED_HEART, x + (i * 8) + randX, y + randY, 0, 0, 9, 9, 9, 9);
        }

        for (int i = corruptedHearts; i < health / 2; i++) {
            int randX = random.nextInt(-5, 5);
            int randY = random.nextInt(-5, 5);
            guiGraphics.blitSprite(VANILLA_HEART, x + (i * 8) + randX, y + randY, 9, 9);
        }
    }

}
