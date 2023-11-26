package ru.octol1ttle.flightassistant.alerts;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record AlertSoundData(@Nullable SoundEvent sound, int priority, float volume, boolean repeat) {
    public static final AlertSoundData STALL = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:stall")),
            0,
            0.75f,
            true
    );
    public static final AlertSoundData PULL_UP = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:pull_up")),
            1,
            0.75f,
            false
    );
    public static final AlertSoundData SINK_RATE = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:sink_rate")),
            2,
            0.75f,
            false
    );
    public static final AlertSoundData TERRAIN = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:terrain")),
            2,
            0.75f,
            false
    );
    public static final AlertSoundData MASTER_WARNING = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:warning")),
            3,
            0.5f,
            true
    );

    public static final AlertSoundData MASTER_CAUTION = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:caution")),
            4,
            0.5f,
            false
    );
    public static final AlertSoundData ALTITUDE_ALERT = new AlertSoundData(
            SoundEvent.of(new Identifier("flightassistant:altitude_alert")),
            5,
            0.25f,
            true
    );
    public static final AlertSoundData EMPTY = new AlertSoundData(
            null,
            Integer.MAX_VALUE,
            0.0f,
            false
    );
}
