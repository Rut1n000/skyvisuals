package com.skyvisuals;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyVisuals implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("skyvisuals");

    @Override
    public void onInitialize() {
        LOGGER.info("Sky Visuals initialized!");
    }
}
