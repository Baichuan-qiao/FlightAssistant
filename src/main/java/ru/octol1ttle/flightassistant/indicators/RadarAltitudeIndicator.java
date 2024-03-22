package ru.octol1ttle.flightassistant.indicators;

import java.awt.Color;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import ru.octol1ttle.flightassistant.Dimensions;
import ru.octol1ttle.flightassistant.HudComponent;
import ru.octol1ttle.flightassistant.computers.AirDataComputer;
import ru.octol1ttle.flightassistant.computers.navigation.FlightPlanner;
import ru.octol1ttle.flightassistant.config.FAConfig;

public class RadarAltitudeIndicator extends HudComponent {
    private final Dimensions dim;
    private final AirDataComputer data;
    private final FlightPlanner plan;

    public RadarAltitudeIndicator(Dimensions dim, AirDataComputer data, FlightPlanner plan) {
        this.dim = dim;
        this.data = data;
        this.plan = plan;
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer) {
        if (!FAConfig.indicator().showRadarAltitude) {
            return;
        }
        if (!data.isCurrentChunkLoaded) {
            renderFaulted(context, textRenderer);
            return;
        }

        int bottom = dim.bFrame;
        int xAltText = dim.rFrame + 7;
        int safeLevel = data.groundLevel == data.voidLevel() ? data.voidLevel() + 16 : data.groundLevel;
        Color color = getAltitudeColor(safeLevel, data.altitude());

        drawText(textRenderer, context, Text.translatable(data.groundLevel == data.voidLevel() ? "flightassistant.void_level" : "flightassistant.ground_level"), xAltText - 10, bottom, color);
        drawText(textRenderer, context, asText("%d", MathHelper.floor(data.heightAboveGround())), xAltText, bottom, color);
        drawBorder(context, xAltText - 2, bottom - 2, 28, color);
    }

    private Color getAltitudeColor(int safeLevel, float altitude) {
        if (altitude <= safeLevel) {
            return FAConfig.indicator().warningColor;
        }

        Integer minimums = plan.getMinimums(data.groundLevel);
        if (minimums != null && altitude <= minimums) {
            return FAConfig.indicator().cautionColor;
        }

        return FAConfig.indicator().frameColor;
    }

    @Override
    public void renderFaulted(DrawContext context, TextRenderer textRenderer) {
        drawText(textRenderer, context, Text.translatable("flightassistant.radar_short"), dim.rFrame - 3, dim.bFrame, FAConfig.indicator().warningColor);
    }

    @Override
    public String getId() {
        return "radar";
    }
}
