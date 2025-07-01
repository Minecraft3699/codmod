package com.mc3699.codmod.network;

import com.mojang.blaze3d.platform.NativeImage;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.server.MinecraftServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MonitorSystem {

    private static final Map<String, ScreenshotData> screenshots = new ConcurrentHashMap<>();
    private record ScreenshotData(int[] pixels, int width, int height) {}

    public static void storeScreenshot(String playerName, int[] screenshotData, int width, int height)
    {
        screenshots.put(playerName, new ScreenshotData(screenshotData, width, height));
    }

    public static void endpoint(MinecraftServer server, HttpServer httpServer)
    {
        httpServer.createContext("/screenshot/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            String playerName = path.substring("/screenshot/".length());
            ScreenshotData screenshot = screenshots.get(playerName);
            if (screenshot != null) {
                try {
                    NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, screenshot.width, screenshot.height, false);
                    for (int i = 0; i < screenshot.pixels.length; i++) {
                        int x = i % screenshot.width;
                        int y = i / screenshot.width;
                        nativeImage.setPixelRGBA(x, y, screenshot.pixels[i]);
                    }
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(nativeImageToBufferedImage(nativeImage), "png", outputStream);
                    byte[] pngData = outputStream.toByteArray();
                    exchange.getResponseHeaders().set("Content-Type", "image/png");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    exchange.sendResponseHeaders(200, pngData.length);
                    exchange.getResponseBody().write(pngData);
                    nativeImage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "Error processing screenshot for player: " + playerName;
                    exchange.sendResponseHeaders(500, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                }
            } else {
                String response = "No screenshot for player: " + playerName;
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseBody().write(response.getBytes());
            }
            exchange.close();
        });
    }

    private static BufferedImage nativeImageToBufferedImage(NativeImage nativeImage) {
        int width = nativeImage.getWidth();
        int height = nativeImage.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] pixelData = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < width * height; i++) {
            int x = i % width;
            int y = i / width;
            int rgba = nativeImage.getPixelRGBA(x, y);
            // color fix (idk how tf this works, I hate working with bits)
            int a = (rgba >> 24) & 0xFF;
            int r = (rgba >> 16) & 0xFF;
            int g = (rgba >> 8) & 0xFF;
            int b = rgba & 0xFF;
            pixelData[i] = (a << 24) | (b << 16) | (g << 8) | r;
        }
        return bufferedImage;
    }

}
