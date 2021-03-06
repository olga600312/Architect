package global;

import java.awt.*;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:40
 */
public class Utilities {
    public static Color color(Color initColor, int count, float step) {
        float[] hsbValues = Color.RGBtoHSB(initColor.getRed(), initColor.getGreen(), initColor.getBlue(), null);
        float saturation = count == 0 ? 0 : hsbValues[1] + (count * step) / 100;
        hsbValues[1] = Math.min(saturation, 0.6f);
        if (saturation > 0.5) {
            hsbValues[2] /= 1.5;
        }
        int rgb = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);
        return count > 0 ? new Color(rgb) : Constants.BACKGROUND;
    }

    public static Color setSaturation(Color initColor, float saturation) {
        float[] hsbValues = Color.RGBtoHSB(initColor.getRed(), initColor.getGreen(), initColor.getBlue(), null);
        hsbValues[1] = saturation;
       // hsbValues[2] /= 1.5;
        int rgb = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);
        return new Color(rgb);
    }

    public static float getSaturation(Color c){
        float[] hsbValues = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        return hsbValues[1];
    }
}
