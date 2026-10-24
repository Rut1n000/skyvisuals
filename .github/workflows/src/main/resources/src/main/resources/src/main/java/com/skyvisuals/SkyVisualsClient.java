package com.skyvisuals;

import com.skyvisuals.config.SkyConfig;
import com.skyvisuals.gui.SkyVisualsScreen;
import com.skyvisuals.render.SkyRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SkyVisualsClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        SkyConfig.load();
        SkyRenderer.register();

        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyvisuals.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.skyvisuals"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new SkyVisualsScreen(null));
                }
            }
        });
    }
}
