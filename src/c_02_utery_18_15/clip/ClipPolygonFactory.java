package c_02_utery_18_15.clip;

import c_02_utery_18_15.model.Edge;
import c_02_utery_18_15.model.Point;


import java.util.ArrayList;
import java.util.List;

public class ClipPolygonFactory {
    private List<Point> polygonPoints;
    private int color;

    public ClipPolygonFactory() {
        this.polygonPoints = new ArrayList<>();
        makeClipPolygon();
    }

    public List<Point> getPolygonPoints() {
        return polygonPoints;
    }

    public int getColor() {
        return color;
    }

    private void makeClipPolygon(){
        Point p1 = new Point(5,5);
        Point p2 = new Point(60,400);
        Point p3 = new Point(400,500);
        polygonPoints.add(p1);
        polygonPoints.add(p2);
        polygonPoints.add(p3);

    }


}
