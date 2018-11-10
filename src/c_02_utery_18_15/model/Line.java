package c_02_utery_18_15.model;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<Point> pointList;

    public Line() {
        this.pointList = new ArrayList<>();
    }

    public List<Point> getPointList() {
        return pointList;
    }

}
