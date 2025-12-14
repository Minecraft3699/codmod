package com.mc3699.codmod.client.image;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(value = Dist.CLIENT)
public class ImageDisplayHandler {

    private static final HashMap<UUID, RenderedImage> IMAGES = new HashMap<>();

    public static void addImage(ResourceLocation texture, int x, int y, int scaleX, int scaleY, int length) {
        RenderedImage image = new RenderedImage(texture, x, y, scaleX, scaleY, length);
        IMAGES.put(UUID.randomUUID(), image);
    }

    @SubscribeEvent
    public static void renderGUI(RenderGuiLayerEvent.Post event) {
        if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH)) {
            GuiGraphics graphics = event.getGuiGraphics();
            IMAGES.forEach((uuid, renderedImage) -> {
                graphics.blit(renderedImage.texture, renderedImage.x, renderedImage.y, 0, 0, renderedImage.scaleX, renderedImage.scaleY, renderedImage.scaleX, renderedImage.scaleY);
            });
        }
    }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        Iterator<Map.Entry<UUID, RenderedImage>> it = IMAGES.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<UUID, RenderedImage> entry = it.next();
            RenderedImage img = entry.getValue();
            img.length--;
            if (img.length <= 0) {
                Minecraft.getInstance().getTextureManager().release(img.texture);
                it.remove();
            }
        }
    }


    public static class RenderedImage {

        ResourceLocation texture;
        int x, y, scaleX, scaleY, length;

        public RenderedImage(ResourceLocation texture, int x, int y, int scaleX, int scaleY, int length) {
            this.x = x;
            this.y = y;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.length = length;
            this.texture = texture;
        }
    }

}
