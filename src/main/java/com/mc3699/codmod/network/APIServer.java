package com.mc3699.codmod.network;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

record BanData(String username, String bannedBy, String uuid, String banStart, String banEnd, String reason) {
}

public class APIServer {

    private HttpServer httpServer;

    public void startServer(MinecraftServer server) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(15017), 0);
        BanEndpoint(server, httpServer);
        httpServer.start();
    }

    public void stopServer()
    {
        httpServer.stop(0);
    }

    private void BanEndpoint(MinecraftServer server, HttpServer httpServer) {
        httpServer.createContext(
                "/api/bans", exchange -> {
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
                }
        );
    }
}