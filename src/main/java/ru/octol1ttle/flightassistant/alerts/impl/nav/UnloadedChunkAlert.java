package ru.octol1ttle.flightassistant.alerts.impl.nav;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import ru.octol1ttle.flightassistant.DrawHelper;
import ru.octol1ttle.flightassistant.alerts.impl.AlertSoundData;
import ru.octol1ttle.flightassistant.alerts.api.BaseAlert;
import ru.octol1ttle.flightassistant.alerts.api.IECAMAlert;
import ru.octol1ttle.flightassistant.computers.impl.safety.ChunkStatusComputer;
import ru.octol1ttle.flightassistant.config.FAConfig;
import ru.octol1ttle.flightassistant.registries.ComputerRegistry;

public class UnloadedChunkAlert extends BaseAlert implements IECAMAlert {
    private final ChunkStatusComputer chunkStatus = ComputerRegistry.resolve(ChunkStatusComputer.class);

    @Override
    public boolean isTriggered() {
        return chunkStatus.shouldWarn();
    }

    @Override
    public int render(TextRenderer textRenderer, DrawContext context, int x, int y, boolean highlight) {
        return DrawHelper.drawHighlightedText(textRenderer, context, Text.translatable("alerts.flightassistant.unloaded_chunk"), x, y,
                FAConfig.indicator().warningColor, highlight);
    }

    @Override
    public @NotNull AlertSoundData getSoundData() {
        return AlertSoundData.MASTER_WARNING;
    }
}
