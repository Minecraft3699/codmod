package com.mc3699.codmod.upgrades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upgrades {
    public static final Upgrade DOWNLOAD_SPEED = new Upgrade("download_speed", 0, 16, 20, 4);
    public static final Upgrade FILTER_SIZE = new Upgrade("filter_size", 0, 16, 15, 4);
    public static final Upgrade SERVER_STABILITY = new Upgrade("server_stability", 0, 16, 25, 4);
    public static final Upgrade PROCESSING_SPEED = new Upgrade("processing_speed", 0, 16, 20, 4);
    public static final Upgrade PING_SIZE = new Upgrade("ping_size", 0, 16, 15, 4);
    public static final Upgrade PING_SPEED = new Upgrade("ping_speed", 0, 16, 10, 4);
    public static final Upgrade SENSOR_SPEED = new Upgrade("sensor_speed", 0, 16, 5, 4);
    public static final Upgrade SENSOR_SIZE = new Upgrade("sensor_size", 0, 16, 15, 4);
    public static final Upgrade PING_COOLDOWN = new Upgrade("ping_cooldown", 0, 16, 15, 4);
    public static final Upgrade DETECTOR_SENSITIVITY = new Upgrade("detector_sensitivity", 0, 16, 10, 4);
    public static final Upgrade DETECTOR_FREQUENCY = new Upgrade("detector_frequency", 0, 16, 10, 4);
    public static final Upgrade SENSOR_SUCCESS_RATE = new Upgrade("sensor_success_rate", 0, 16, 5, 4);
    public static final Upgrade DETECTOR_QUALITY = new Upgrade("detector_quality", 0, 16, 20, 4);
    public static final Upgrade RADAR_HISTORY = new Upgrade("radar_history", 0, 4, 30, 4);
    public static final Upgrade RADAR_SPEED = new Upgrade("radar_speed", 0, 16, 15, 4);
    public static final Upgrade TRANSFORMER_STABILITY = new Upgrade("transformer_stability", 0, 16, 25, 4);
    public static final Upgrade BREAKER_COMPONENTS_TIME = new Upgrade("breaker_components_time", 0, 16, 20, 8);
    public static final Upgrade PROCESSING_LEVEL = new Upgrade("processing_level", 0, 3, 30, 10);

    public static final Map<String, Upgrade> UPGRADES = new HashMap<>();

    public static final List<Upgrade> ALL_UPGRADES = List.of(
            DOWNLOAD_SPEED,
            FILTER_SIZE,
            SERVER_STABILITY,
            PROCESSING_SPEED,
            PING_SIZE,
            PING_SPEED,
            SENSOR_SPEED,
            SENSOR_SIZE,
            PING_COOLDOWN,
            DETECTOR_SENSITIVITY,
            DETECTOR_FREQUENCY,
            SENSOR_SUCCESS_RATE,
            DETECTOR_QUALITY,
            RADAR_HISTORY,
            RADAR_SPEED,
            TRANSFORMER_STABILITY,
            BREAKER_COMPONENTS_TIME,
            PROCESSING_LEVEL
    );

    static {
        for (Upgrade upgrade : ALL_UPGRADES) {
            UPGRADES.put(upgrade.id(), upgrade);
        }
    }
}
