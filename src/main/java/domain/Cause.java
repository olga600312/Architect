package domain;

import lombok.Data;

import java.util.Collection;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 09:24
 */
@Data
public class Cause {
    private Location location;
    private Collection<Entry> data;
    private int initColor;

    @Data
    private static class Entry {
        private Location location;
        private int total;
    }

}
