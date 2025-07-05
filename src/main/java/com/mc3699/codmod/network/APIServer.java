package com.mc3699.codmod.network;

import com.google.gson.Gson;
import com.mojang.blaze3d.platform.NativeImage;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.UserBanList;
import net.neoforged.fml.common.EventBusSubscriber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

record BanData(String username, String bannedBy, String uuid, String banStart, String banEnd, String reason) {}
record PlayerData(String username) {}

public class APIServer {



    public void startServer(MinecraftServer server) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(15017), 0);

        BanEndpoint(server, httpServer);

        httpServer.start();
    }

    private void BanEndpoint(MinecraftServer server, HttpServer httpServer)
    {
        httpServer.createContext("/api/bans", exchange -> {
            try {
                UserBanList banList = server.getPlayerList().getBans();

                String json = new Gson().toJson(banList.getEntries().stream()
                        .map(entry -> new BanData(
                                entry.getDisplayName().getString(),
                                entry.getSource(),
                                UUID.randomUUID().toString(),
                                entry.getCreated().toString(),
                                entry.getExpires() != null ? entry.getExpires().toString() : null,
                                entry.getReason()
                        ))
                        .toList());

                byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, jsonBytes.length);

                try (OutputStream outputStream = exchange.getResponseBody()) {
                    outputStream.write(jsonBytes);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1);
                }
            exchange.close();
        });
    }

    private void PlayerEndpoint(MinecraftServer server, HttpServer httpServer)
    {
        httpServer.createContext("/api/players", exchange -> {

        });
    }





}