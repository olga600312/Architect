package domain;

import global.Utilities;
import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:24
 */
@Data
@Builder
public class Cause {
    private HorizontalLocation rowHeader;
    private ArrayList<Entry> data;

    public void initColors(float step) {
        data.forEach(e -> {
            LocationType type = e.getColumnHeader().getType();

            e.setColor(Utilities.color(type.getInitColor(), e.getTotal(), step));
        });
    }

    public Collection<String> getVerticalLocation() {
        ArrayList<String> s = new ArrayList<>();
        data.forEach(e -> {
            if (!s.contains(e.columnHeader.getName()))
                s.add(e.columnHeader.getName());
        });
        return s;
    }


    @Data
    @Builder
    public static class Entry {
        private VerticalLocation columnHeader;
        private int total;
        private transient Color color;
        private String file;
    }

    public Entry entryAt(int index) {
        return index >= 0 && index < data.size() ? data.get(index) : null;
    }

}
