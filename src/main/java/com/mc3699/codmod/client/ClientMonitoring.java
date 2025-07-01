package com.mc3699.codmod.client;

import com.mc3699.codmod.Codmod;
import com.mc3699.codmod.network.MonitorPacket;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EventBusSubscriber(modid = Codmod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientMonitoring {

    private static int tickCount = 0;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        tickCount++;
        if (tickCount % 20 == 0) {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null) return;
            String playerName = client.player.getName().getString();
            try {
                NativeImage screenshot = Screenshot.takeScreenshot(client.getMainRenderTarget());
                int[] rawData = screenshot.getPixelsRGBA();
                client.execute(() -> client.getConnection()
                        .send(new MonitorPacket(playerName, rawData, screenshot.getWidth(), screenshot.getHeight())));
                screenshot.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
