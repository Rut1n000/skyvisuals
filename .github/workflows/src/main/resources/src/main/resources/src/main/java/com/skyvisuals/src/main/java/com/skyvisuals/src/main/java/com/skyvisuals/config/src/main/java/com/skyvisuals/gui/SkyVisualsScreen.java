package com.skyvisuals.gui;

import com.skyvisuals.config.SkyConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class SkyVisualsScreen extends Screen {
    private final Screen parent;

    public SkyVisualsScreen(Screen parent) {
        super(Text.literal("Sky Visuals"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        SkyConfig cfg = SkyConfig.get();
        int cx = this.width / 2;
        int y = this.height / 2 - 80;

        this.addDrawableChild(ButtonWidget.builder(
                toggleLabel("Enabled", cfg.enabled),
                b -> { cfg.enabled = !cfg.enabled; b.setMessage(toggleLabel("Enabled", cfg.enabled)); SkyConfig.save(); }
        ).dimensions(cx - 100, y, 200, 20).build());
        y += 24;

        this.addDrawableChild(ButtonWidget.builder(
                toggleLabel("Custom Sky", cfg.customSky),
                b -> { cfg.customSky = !cfg.customSky; b.setMessage(toggleLabel("Custom Sky", cfg.customSky)); SkyConfig.save(); }
        ).dimensions(cx - 100, y, 200, 20).build());
        y += 24;

        this.addDrawableChild(ButtonWidget.builder(
                toggleLabel("Custom Clouds", cfg.customClouds),
                b -> { cfg.customClouds = !cfg.customClouds; b.setMessage(toggleLabel("Custom Clouds", cfg.customClouds)); SkyConfig.save(); }
        ).dimensions(cx - 100, y, 200, 20).build());
        y += 24;

        this.addDrawableChild(new FloatSlider(cx - 100, y, 200, 20, "Cloud Speed",
                cfg.cloudSpeed, 0.0f, 3.0f, v -> { cfg.cloudSpeed = v; SkyConfig.save(); }));
        y += 24;

        this.addDrawableChild(new FloatSlider(cx - 100, y, 200, 20, "Brightness",
                cfg.brightness, 0.2f, 2.0f, v -> { cfg.brightness = v; SkyConfig.save(); }));
        y += 40;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                b -> this.close()
        ).dimensions(cx - 100, y, 200, 20).build());
    }

    private Text toggleLabel(String name, boolean value) {
        return Text.literal(name + ": " + (value ? "§aON" : "§cOFF"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§7Нажми RShift чтобы открыть снова"),
                this.width / 2, 36, 0xAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        SkyConfig.save();
        this.client.setScreen(parent);
    }

    private static class FloatSlider extends SliderWidget {
        private final String label;
        private final float min, max;
        private final java.util.function.Consumer<Float> onChange;

        public FloatSlider(int x, int y, int w, int h, String label, float value, float min, float max,
                           java.util.function.Consumer<Float> onChange) {
            super(x, y, w, h, Text.empty(), (value - min) / (max - min));
            this.label = label;
            this.min = min;
            this.max = max;
            this.onChange = onChange;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            float v = min + (float) (this.value * (max - min));
            this.setMessage(Text.literal(label + ": " + String.format("%.2f", v)));
        }

        @Override
        protected void applyValue() {
            onChange.accept(min + (float) (this.value * (max - min)));
        }
    }
}
