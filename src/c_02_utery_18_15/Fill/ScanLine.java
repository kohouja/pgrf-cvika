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
        int minY = 0;
        int maxY = 0;
        for(int i = 0; i < points.size(); i++){
            if(points.get(i).y > maxY){
//                hledani maxima
                maxY = points.get(i).y;
            }
            if(points.get(i).y < minY){
//                hledani minima
                minY = points.get(i).y;
            }
        }
        List<Edge> edges = new ArrayList<>();
        for(int a = 0; a < points.size()-1; a++){
            Edge edge = new Edge(points.get(a), points.get(a+1));
            if(!edge.isHorizontal()){
                edge.orientate();
                edges.add(edge);
            }
//            prida vsecky usecky az na posledni za predpokladu, ze nejsou horizontalni
        }
//        prida posledni usecku za predpokladu, ze neni horizontalni
        Edge edge = new Edge(points.get(0), points.get(points.size()-1));
        if(!edge.isHorizontal()){
            edge.orientate();
            edges.add(edge);
        }


//        projet vsechny body a vytvorit z nich usecky (jako polygon)
//        0. a 1. budou hrana; 1. a 2. budou druha hrana
//        ...; posledni a nulty budou posledni hrana
//        ignorovat vodorovne hrany
//        vytvorene hrany orientovat a pridat do seznamu

//        najit minimum a maximum pro Y
//         projet vsechny body a najit minimalni a maximalni Y
//        pro vsechna Y od min do max vcetne
        for(int y = minY; y <= maxY; y++){
            List<Integer> intersections = new ArrayList<>();
            for(Edge edgeI : edges){
                if(edgeI.intersectionExists(y)){
                    intersections.add(edgeI.getIntersection(y));
                }
                else if(edgeI.getY1() == y){
                    intersections.add(edgeI.getIntersection(y));
                }
            }
//            projit vsechny hrany
//            pokud hrana  ma prusecik pro dane Y
//            tak vypocitame prusecik a ulozime hodnotu do seznamu
            Collections.sort(intersections);
            if(intersections.size()>1){
                for(int i = 0; i<intersections.size(); i += 2){
                    for(int x = intersections.get(i); x<intersections.get(i+1); x++){
                        raster.drawPixel(x, y, fillColor);
                    }
                }
            }
//            volitelne vlastni algoritmus na setreni

//            vybarveni mezi pruseciky
//            spojeni vzdy sudeho s lichym
//            0. a 1.; 2. a 3.; ...
        }
//        obtahni hranice
//        renderer.drawPolygon(points. edgeColor);
    }
}
