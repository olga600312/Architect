package global;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:36
 */
@Slf4j
public class App {
    public static void main(String[] args) {
       Color c1=new Color(0xd7dfe1, true);
       Color c2=Utilities.color(c1,50,0.4f);

      System.err.println("Color c1:"+c1.getRGB()+" Color c2:"+c2.getRGB());
    }
}
