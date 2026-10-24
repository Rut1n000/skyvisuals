package com.skyvisuals.render;

import com.skyvisuals.config.SkyConfig;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import com.mojang.blaze3d.systems.RenderSystem;

public class SkyRenderer {
    public static void register() {
        WorldRenderEvents.START.register(context -> {
            SkyConfig cfg = SkyConfig.get();
            if (!cfg.enabled || !cfg.customSky) return;

            RenderSystem.clearColor(
                    cfg.skyR * cfg.brightness,
                    cfg.skyG * cfg.brightness,
                    cfg.skyB * cfg.brightness,
                    1.0f
            );
        });
    }
}
