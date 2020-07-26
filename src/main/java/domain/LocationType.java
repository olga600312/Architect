package domain;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;


public enum LocationType {
    COMPONENTS,LEISURE,EDUCATION,DOMESTICS,TRANSPORT,WORKPLACE;
    private Color initColor;


    public Color getInitColor() {
        return initColor;
    }

    public void setInitColor(Color initColor) {
        this.initColor = initColor;
    }


}
