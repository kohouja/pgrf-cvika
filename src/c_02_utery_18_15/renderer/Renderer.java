package c_02_utery_18_15.renderer;

import c_02_utery_18_15.model.Edge;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.view.Raster;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private Raster raster;

    public Renderer(Raster raster) {
        this.raster = raster;
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        float k = dy / (float) dx;
        // https://www.google.com/search?q=java+dividing+two+integers
        float q = y1 - k * x1;

        if (Math.abs(k) < 1) {
            // řídící osa X

            if (x2 < x1) {
                int x3 = x2;
                x2 = x1;
                x1 = x3;
            }

            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                int ry = Math.round(y);
                raster.drawPixel(x, ry, color);
            }
        } else {
            // řídící osa Y

        }
    }

    public void drawDDA(int x1, int y1, int x2, int y2, int color) {

        int dx = x2 - x1;
        int dy = y2 - y1;
        float x, y, k, h = 0, g = 0;

        k = dy / (float) dx;
        if (Math.abs(k) < 1) {
            // řídící osa X

            g = 1;
            h = k;
        } else {
            // řídící osa Y
            g = 1 / k;
            h = 1;
//            na nasledujici podminku jsem prisel sam ty kokso 4:07 rano || prohozeni ridici osy y
            if((x2>x1 && y2<y1) || (x2<x1 && y2>y1)){
                g = -g;
                h = -h;
            }
        }

        x = x1;
        y = y1;
//        na tohle jsem taky prisel sam ty kokso 3:30 rano || prohozeni ridici osy x
        if(x2<x1){
            for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
                drawPixel(Math.round(x), Math.round(y), color);
                x -= g;
                y -= h;
            }
        }else{
            for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
                drawPixel(Math.round(x), Math.round(y), color);
                x += g;
                y += h;
            }
        }


    }

    public void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= raster.getResizableWidth()) return;
        if (y < 0 || y >= raster.getResizableHeight()) return;
        // nastavit pixel do img
        raster.drawPixel(x, y, color);
    }

    public void drawPolygon(List<Point> polygonPoints, int color) {
        for(int i = 0; i<polygonPoints.size() - 1; i++){
            drawDDA(
                    polygonPoints.get(i).x,
                    polygonPoints.get(i).y,
                    polygonPoints.get(i+1).x,
                    polygonPoints.get(i+1).y,
                    color
            );
        }
        drawDDA(
                polygonPoints.get(0).x,
                polygonPoints.get(0).y,
                polygonPoints.get(polygonPoints.size()-1).x,
                polygonPoints.get(polygonPoints.size()-1).y,
                color
        );

    }

    public void drawLines() {

    }

    public List<Point> clip(List<Point> polygonIn, List<Point> clippingArea){
////        in  - seznam vrcholu orezavaneho polygonu (na tabuli ten cerny)
////        clip polygon - orezavaci polygon (na tabuli ten zleny)
////        out - seznam vrcholu orezavaneho polygonu
//        List <Point> in  = new ArrayList<>();
//
//        Point p1 = clipPolygon.get(clipPolygon.size()-1);
//        for(Point p2 : clipPolygon){
//            Edge edge = new Edge(p1, p2);
//            edge.orientate();
//    // vytvor hranu z bodu p1 a p2
////            Point v1 = in.last
//            for(Point v2: in){
////                TODO algoritmus
//                if(edge.isInside(v2)){
//                 out.add(v2);
//                }
//            }
//            p1 = p2;
//            in = out; // aktualizuj orezavany polygon
//        }
//
//        return in;
        List<Point> input;

        List<Point> out = new ArrayList<>();
        for(Point pointIn : polygonIn){
            out.add(pointIn);
        }
        List<Edge> clippingEdges = new ArrayList<>();
        for (int i = 0; i < clippingArea.size(); i++) {
            clippingEdges.add(new Edge(clippingArea.get(i), clippingArea.get((i + 1) % clippingArea.size())));
        }

        for (Edge edge : clippingEdges) {

            input = new ArrayList<>(out);
            out.clear();

            Point v1 = input.get(input.size() - 1);
            for (Point v2 : input) {
                if (edge.isInside(v2)) {
                    if (!edge.isInside(v1))
                        out.add(edge.getIntersection(v1, v2));
                    out.add(v2);
                } else {
                    if (edge.isInside(v1))
                        out.add(edge.getIntersection(v1, v2));
                }
                v1 = v2;
            }
        }

        return out;
    }

}
