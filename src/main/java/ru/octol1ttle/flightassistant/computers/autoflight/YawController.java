package ru.octol1ttle.flightassistant.computers.autoflight;

import ru.octol1ttle.flightassistant.computers.AirDataComputer;
import ru.octol1ttle.flightassistant.computers.ITickableComputer;
import ru.octol1ttle.flightassistant.computers.TimeComputer;

public class YawController implements ITickableComputer {
    private final TimeComputer time;
    private final AirDataComputer data;

    public Float targetHeading;

    public YawController(TimeComputer time, AirDataComputer data) {
        this.time = time;
        this.data = data;
    }

    @Override
    public void tick() {
        if (!data.canAutomationsActivate()) {
            return;
        }

        smoothSetHeading(targetHeading, time.deltaTime * 2.0f);
    }

    private void smoothSetHeading(Float heading, float delta) {
        if (heading == null) {
            return;
        }

        float difference = heading - data.heading();
        if (difference < -180.0f) {
            difference += 360.0f;
        }
        if (difference > 180.0f) {
            difference -= 360.0f;
        }

        float newHeading;
        if (Math.abs(difference) < 0.05f) {
            newHeading = heading;
        } else {
            newHeading = data.heading() + difference * delta;
        }

        data.player().setYaw(newHeading - 180.0f);
    }

    @Override
    public String getId() {
        return "yaw_ctl";
    }

    @Override
    public void reset() {
        targetHeading = null;
    }
}
