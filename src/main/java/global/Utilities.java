package global;

import view.MainFrame;

import java.awt.*;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:40
 */
public class Utilities {
    public static Color color(Color initColor, int count, float step) {
        float[] hsbvals = Color.RGBtoHSB(initColor.getRed(), initColor.getGreen(), initColor.getBlue(), null);
        float saturation = count == 0 ? 0 : hsbvals[1] + (count * step) / 100;
        hsbvals[1] = Math.min(saturation, 0.6f);
        if (saturation > 0.5) {
            hsbvals[2] /= 1.5;
        }
        int rgb = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
        return count > 0 ? new Color(rgb) : Constants.BACKGROUND;
    }

    public static Color setSaturation(Color initColor, float saturation) {
        float[] hsbvals = Color.RGBtoHSB(initColor.getRed(), initColor.getGreen(), initColor.getBlue(), null);
        hsbvals[1] = saturation;
       // hsbvals[2] /= 1.5;
        int rgb = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
        return new Color(rgb);
    }
}
