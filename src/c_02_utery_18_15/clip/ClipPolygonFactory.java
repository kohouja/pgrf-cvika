package c_02_utery_18_15.clip;

import c_02_utery_18_15.model.Edge;
import c_02_utery_18_15.model.Point;


import java.util.ArrayList;
import java.util.List;

public class ClipPolygonFactory {
    private List<Point> polygonPoints;
    private List<Integer> anglesList;
    private int color;

    public int originX;
    public int originY;

    public ClipPolygonFactory() {
        this.polygonPoints = new ArrayList<>();
        this.anglesList = new ArrayList<>();
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

    public boolean isGoodPoint(Point point) {
        polygonPoints.add(point);
        for (int i = 0; i < polygonPoints.size(); i++) {
            int x = polygonPoints.get((i + 1) % polygonPoints.size()).x - polygonPoints.get(i).x;
            int y = polygonPoints.get((i + 1) % polygonPoints.size()).y - polygonPoints.get(i).y;
            int uhel = (int) Math.toDegrees(Math.atan2(x, y));
            if (uhel < 0) {
                uhel += 360;
            }
            anglesList.add(uhel);
        }

        for (int i = 0; i < anglesList.size() - 1; i++) {
            int ee = 180 - (anglesList.get(i + 1) - anglesList.get(i));
            if (ee > 180 || ee < 0) {
                anglesList.clear();
                polygonPoints.remove(polygonPoints.size() - 1);
                return false;
            }
        }
        anglesList.clear();
        polygonPoints.remove(polygonPoints.size() - 1);
        return true;
    }


}
