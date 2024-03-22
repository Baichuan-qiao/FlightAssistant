package ru.octol1ttle.flightassistant.computers.safety;

import net.minecraft.MinecraftVersion;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import ru.octol1ttle.flightassistant.computers.AirDataComputer;
import ru.octol1ttle.flightassistant.computers.ITickableComputer;
import ru.octol1ttle.flightassistant.config.FAConfig;

public class ElytraStateController implements ITickableComputer {
    private final AirDataComputer data;
    private boolean syncedState;
    private boolean changesPending;

    public ElytraStateController(AirDataComputer data) {
        this.data = data;
    }

    @Override
    public void tick() {
        if (syncedState != data.isFlying() || data.player().isOnGround()) {
            changesPending = false;
        }
        if (!isAvailable() || changesPending || !data.canAutomationsActivate(false)) {
            return;
        }

        if (FAConfig.computer().closeElytraUnderwater && data.isFlying() && data.player().isSubmergedInWater()) {
            // Retract the wings
            sendSwitchState();
        }

        boolean flying = data.isFlying() || data.player().getAbilities().flying;
        boolean hasUsableElytra = data.elytraHealth != null && data.elytraHealth > (1 / 432.0f);
        boolean notLookingToClutch = data.pitch() > -70.0f;
        if (FAConfig.computer().openElytraAutomatically
                && data.fallDistance() > 3.0f && !flying && hasUsableElytra && notLookingToClutch) {
            // Extend the wings
            sendSwitchState();
        }
    }

    private void sendSwitchState() {
        syncedState = data.isFlying();
        data.player().networkHandler.sendPacket(new ClientCommandC2SPacket(data.player(), ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        changesPending = true;
    }

    public static boolean isAvailable() {
        return MinecraftVersion.CURRENT.getProtocolVersion() >= 764;
    }

    @Override
    public String getId() {
        return "elytra_state";
    }

    @Override
    public void reset() {
        syncedState = false;
        changesPending = false;
    }
}