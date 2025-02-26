package ru.octol1ttle.flightassistant.alerts.impl.autoflight;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import ru.octol1ttle.flightassistant.DrawHelper;
import ru.octol1ttle.flightassistant.alerts.api.BaseAlert;
import ru.octol1ttle.flightassistant.alerts.api.IECAMAlert;
import ru.octol1ttle.flightassistant.alerts.impl.AlertSoundData;
import ru.octol1ttle.flightassistant.computers.impl.autoflight.AutoFlightController;
import ru.octol1ttle.flightassistant.config.FAConfig;
import ru.octol1ttle.flightassistant.registries.ComputerRegistry;

public class AutopilotOffAlert extends BaseAlert implements IECAMAlert {
    private final AutoFlightController autoflight = ComputerRegistry.resolve(AutoFlightController.class);

    public AutopilotOffAlert() {
        this.hidden = true;
    }

    @Override
    public boolean isTriggered() {
        return !autoflight.autoPilotEnabled;
    }

    @Override
    public @NotNull AlertSoundData getSoundData() {
        return AlertSoundData.AUTOPILOT_DISCONNECT;
    }

    @Override
    public int render(TextRenderer textRenderer, DrawContext context, int x, int y, boolean highlight) {
        return DrawHelper.drawHighlightedText(textRenderer, context, Text.translatable("alerts.flightassistant.autoflight.autopilot_off"), x, y,
                FAConfig.indicator().warningColor,
                highlight && autoflight.apDisconnectionForced);
    }
}
