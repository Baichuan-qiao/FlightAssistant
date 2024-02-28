package ru.octol1ttle.flightassistant.alerts.firework;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;
import ru.octol1ttle.flightassistant.HudComponent;
import ru.octol1ttle.flightassistant.alerts.AbstractAlert;
import ru.octol1ttle.flightassistant.alerts.AlertSoundData;
import ru.octol1ttle.flightassistant.computers.AirDataComputer;
import ru.octol1ttle.flightassistant.computers.autoflight.FireworkController;
import ru.octol1ttle.flightassistant.config.FAConfig;



public class FireworkUnsafeAlert extends AbstractAlert {
    private final AirDataComputer data;
    private final FireworkController firework;

    public FireworkUnsafeAlert(AirDataComputer data, FireworkController firework) {
        this.data = data;
        this.firework = firework;
    }

    @Override
    public boolean isTriggered() {
        for (Hand hand : Hand.values()) {
            ItemStack stack = data.player.getStackInHand(hand);
            if (stack.getItem() instanceof FireworkRocketItem && !firework.isFireworkSafe(stack)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public @NotNull AlertSoundData getAlertSoundData() {
        return AlertSoundData.MASTER_WARNING;
    }

    @Override
    public int renderECAM(TextRenderer textRenderer, DrawContext context, int x, int y, boolean highlight) {
        return HudComponent.drawHighlightedText(textRenderer, context, Text.translatable("alerts.flightassistant.firework.unsafe"), x, y,
                FAConfig.hud().warningColor, highlight && data.isFlying);
    }
}
