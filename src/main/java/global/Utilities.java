package global;

import java.awt.*;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:40
 */
public class Utilities {
    public static Color color(Color initColor,int count, float step){
        float[] hsbvals=Color.RGBtoHSB(initColor.getRed(),initColor.getGreen(),initColor.getBlue(),null);
        float saturation=hsbvals[1]+(count*step)/100;
        hsbvals[1]=saturation;
        int rgb=Color.HSBtoRGB(hsbvals[0],hsbvals[1],hsbvals[2]);
        return new Color(rgb);
    }
}
