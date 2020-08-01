package domain;

import java.awt.*;


public enum LocationType {
    COMPONENTS {
        @Override
        public Color getInitColor() {
            return new Color(0x00F2F9F9);
        }
    },LEISURE {
        @Override
        public Color getInitColor() {
            return new Color(0x00e9ebf2);
        }
    },EDUCATION {
        @Override
        public Color getInitColor() {
            return new Color(0x00fef7f8);
        }
    },DOMESTIC {
        @Override
        public Color getInitColor() {
            return new Color(0x00faeeef);
        }
    },TRANSPORT {
        @Override
        public Color getInitColor() {
            return new Color(0x00f8f8e6);
        }
    },WORKPLACE {
        @Override
        public Color getInitColor() {
            return new Color(0xE9F1F2);
        }
    };




    public abstract Color getInitColor();




}
