package com.skyvisuals.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyvisuals.SkyVisuals;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SkyConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("skyvisuals.json");

    public boolean enabled = true;
    public boolean customSky = true;
    public boolean customClouds = true;
    public float skyR = 0.05f;
    public float skyG = 0.12f;
    public float skyB = 0.40f;
    public float cloudSpeed = 1.0f;
    public float brightness = 1.0f;

    private static SkyConfig INSTANCE = new SkyConfig();

    public static SkyConfig get() { return INSTANCE; }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                INSTANCE = GSON.fromJson(json, SkyConfig.class);
            } catch (IOException e) {
                SkyVisuals.LOGGER.error("Failed to load config", e);
            }
        }
        save();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            SkyVisuals.LOGGER.error("Failed to save config", e);
        }
    }
}
