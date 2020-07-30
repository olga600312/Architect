package domain;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;


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
            return new Color(0x00e9f1f2);
        }
    };




    public abstract Color getInitColor();




}
