package ru.octol1ttle.flightassistant;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;
import ru.octol1ttle.flightassistant.config.FAConfig;

public class Dimensions {

    public int hScreen;
    public int wScreen;
    public int degreesPerPixel;
    public int xMid;
    public int yMid;

    public int wFrame;
    public int hFrame;
    public int lFrame;
    public int rFrame;
    public int tFrame;
    public int bFrame;

    public void update(DrawContext context, double fov) {
        hScreen = MathHelper.floor(context.getScaledWindowHeight() / FAConfig.get().hudScale);
        wScreen = MathHelper.floor(context.getScaledWindowWidth() / FAConfig.get().hudScale);

        degreesPerPixel = MathHelper.floor(hScreen / fov);
        xMid = wScreen / 2;
        yMid = hScreen / 2;

        wFrame = (int) (wScreen * FAConfig.get().frameWidth);
        hFrame = (int) (hScreen * FAConfig.get().frameHeight);

        lFrame = (wScreen - wFrame) / 2;
        rFrame = lFrame + wFrame;

        tFrame = (hScreen - hFrame) / 2;
        bFrame = tFrame + hFrame;
    }
}