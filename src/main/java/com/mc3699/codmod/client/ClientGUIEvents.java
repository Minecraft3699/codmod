package com.mc3699.codmod.client;

import ca.weblite.objc.Client;
import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.datagen.DatagenItemTagProvider;
import com.mc3699.codmod.registry.CodItems;
import com.mc3699.codmod.registry.CodMobEffects;
import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.ItemStackTagFix;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
                renderCorruptHearts(event.getGuiGraphics(), player);
            }

            if(player != null && player.getInventory().getArmor(3).is(CodItems.SPACE_HELMET))
            {
                renderSpaceSuit(event.getGuiGraphics(), player);
            }
        }
    }

    private static void renderCorruptHearts(GuiGraphics guiGraphics, Player player) {

        int maxHealth = (int) player.getMaxHealth();
        int health = (int) player.getHealth();
        int corruptedHearts = (20 - maxHealth) / 2;
        int x = guiGraphics.guiWidth() / 2 - 91;
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


    public static final ResourceLocation OXYGEN_FULL = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/gui/oxygen_full.png");
    public static final ResourceLocation OXYGEN_EMPTY = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/gui/oxygen_empty.png");
    public static final ResourceLocation OXYGEN_SAT_FULL = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/gui/oxygen_saturation_full.png");
    public static final ResourceLocation OXYGEN_SAT_EMPTY = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/gui/oxygen_saturation_empty.png");
    public static final ResourceLocation WARNING = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "textures/gui/warning.png");


    private static void renderSpaceSuit(GuiGraphics guiGraphics, Player player)
    {

        guiGraphics.blit(OXYGEN_EMPTY, 5,5,0,0, 128, 8, 128,8);
        guiGraphics.blit(OXYGEN_SAT_EMPTY, 5,15,0,0, 128, 8, 128,8);

        boolean suitValid = true;
        for(ItemStack stack : player.getArmorSlots())
        {
            if(!stack.is(DatagenItemTagProvider.SPACE_SUIT_VALID))
            {
                suitValid =false;
            }
        }

        if(!suitValid)
        {
            if(System.currentTimeMillis() % 1000 < 500)
            {
                guiGraphics.blit(WARNING, guiGraphics.guiWidth()/2-6,guiGraphics.guiHeight()/2-40,0,0, 16, 16, 16,16);
                guiGraphics.drawString(Minecraft.getInstance().font, "SUIT ERROR", guiGraphics.guiWidth()/2-25, guiGraphics.guiHeight()/2-20, 0xFF0000);
            }
        }

    }
}
