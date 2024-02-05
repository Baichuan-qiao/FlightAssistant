package ru.octol1ttle.flightassistant;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import ru.octol1ttle.flightassistant.alerts.AlertSoundData;
import ru.octol1ttle.flightassistant.computers.ComputerHost;

import static ru.octol1ttle.flightassistant.FlightAssistant.CONFIG_SETTINGS;

public class FAKeyBindings {
    private static KeyBinding toggleDisplayMode;

    private static KeyBinding toggleFlightDirectors;
    private static KeyBinding toggleAutoFirework;
    private static KeyBinding toggleAutoPilot;

    private static KeyBinding dismissMasterWarning;
    private static KeyBinding dismissMasterCaution;

    private static KeyBinding lockManualFireworks;

    public static void setup() {
        toggleDisplayMode = new KeyBinding("key.flightassistant.toggle_display_mode", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT, "category.flightassistant");

        toggleFlightDirectors = new KeyBinding("key.flightassistant.toggle_flight_directors", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_1, "category.flightassistant");
        toggleAutoFirework = new KeyBinding("key.flightassistant.toggle_auto_firework", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_2, "category.flightassistant");
        toggleAutoPilot = new KeyBinding("key.flightassistant.toggle_auto_pilot", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_3, "category.flightassistant");

        dismissMasterWarning = new KeyBinding("key.flightassistant.dismiss_master_warning", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_0, "category.flightassistant");
        dismissMasterCaution = new KeyBinding("key.flightassistant.dismiss_master_caution", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_DECIMAL, "category.flightassistant");

        lockManualFireworks = new KeyBinding("key.flightassistant.lock_manual_fireworks", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_DECIMAL, "category.flightassistant");

        KeyBindingHelper.registerKeyBinding(toggleDisplayMode);

        KeyBindingHelper.registerKeyBinding(toggleFlightDirectors);
        KeyBindingHelper.registerKeyBinding(toggleAutoFirework);
        KeyBindingHelper.registerKeyBinding(toggleAutoPilot);

        KeyBindingHelper.registerKeyBinding(dismissMasterWarning);
        KeyBindingHelper.registerKeyBinding(dismissMasterCaution);

        KeyBindingHelper.registerKeyBinding(lockManualFireworks);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleDisplayMode.wasPressed()) {
                CONFIG_SETTINGS.toggleDisplayMode(client);
            }

            ComputerHost host = HudRenderer.getHost();
            if (host != null) {
                while (toggleFlightDirectors.wasPressed()) {
                    host.autoflight.flightDirectorsEnabled = !host.autoflight.flightDirectorsEnabled;
                }

                while (toggleAutoFirework.wasPressed()) {
                    if (!host.autoflight.autoFireworkEnabled) {
                        host.autoflight.autoFireworkEnabled = true;
                    } else {
                        host.autoflight.disconnectAutoFirework(false);
                    }
                }

                while (toggleAutoPilot.wasPressed()) {
                    if (!host.autoflight.autoPilotEnabled) {
                        host.autoflight.autoPilotEnabled = true;
                    } else {
                        host.autoflight.disconnectAutopilot(false);
                    }
                }

                while (dismissMasterWarning.wasPressed()) {
                    if (!host.alert.dismiss(AlertSoundData.AUTOPILOT_FORCED_OFF) && !host.alert.dismiss(AlertSoundData.AUTOPILOT_DISCONNECTED_BY_PLAYER)) {
                        host.alert.dismiss(AlertSoundData.MASTER_WARNING);
                    }
                }

                while (dismissMasterCaution.wasPressed()) {
                    host.alert.dismiss(AlertSoundData.MASTER_CAUTION);
                }

                while (lockManualFireworks.wasPressed()) {
                    host.firework.lockManualFireworks = !host.firework.lockManualFireworks;
                }
            }
        });
    }
}
