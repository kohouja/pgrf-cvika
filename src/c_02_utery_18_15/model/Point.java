package c_02_utery_18_15.model;

import java.awt.*;

public class Point {
    public int x;
    public int y;
    public Color color;

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
