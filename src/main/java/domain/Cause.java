package domain;

import global.Utilities;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:24
 */
@Data
public class Cause {
    private Location location;
    private ArrayList<Entry> data;

    public void initColors(float step){
        data.forEach(e->e.setColor(Utilities.color(location.getInitColor(),e.getTotal(),step)));
    }


    @Data
    public static class Entry {
        private Location location;
        private int total;
        private Color color;
    }

    public Entry entryAt(int index) {
        return index >= 0 && index < data.size() ? data.get(index) : null;
    }

}
