package c_02_utery_18_15.Fill;

import c_02_utery_18_15.model.Edge;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.view.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine  implements  Filler{

    private Raster raster;
    private List<Point> points;
    private int fillColor, edgeColor;

    @Override
    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill() {
        scanline();
    }

    public void init(List<Point> points, int fillColor, int edgeColor){
        this.points = points;
        this.fillColor = fillColor;
        this.edgeColor = edgeColor;
    }

    private void scanline(){
        //
        List<Edge> edges = new ArrayList<>();
//        projet vsechny body a vytvorit z nich usecky (jako polygon)
//        0. a 1. budou hrana; 1. a 2. budou druha hrana
//        ...; posledni a nulty budou posledni hrana
//        ignorovat vodorovne hrany
//        vytvorene hrany orientovat a pridat do seznamu

//        najit minimum a maximum pro Y
        int minY = points.get(0).y;
        int maxY = minY;
//         projet vsechny body a najit minimalni a maximalni Y
//        pro vsechna Y od min do max vcetne
        for(int y = minY; y <= maxY; y++){
            List<Integer> intersections = new ArrayList<>();
//            projit vsechny hrany
//            pokud hrana  ma prusecik pro dane Y
//            tak vypocitame prusecik a ulozime hodnotu do seznamu
            Collections.sort(intersections);
//            volitelne vlastni algoritmus na setreni

//            vybarveni mezi pruseciky
//            spojeni vzdy sudeho s lichym
//            0. a 1.; 2. a 3.; ...
        }
//        obtahni hranice
//        renderer.drawPolygon(points. edgeColor);
    }
}
