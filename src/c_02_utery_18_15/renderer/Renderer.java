package c_02_utery_18_15.renderer;

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

    public List<Point> clip(List<Point> polygon, List<Point> clipPolygon){
//        in  - seznam vrcholu orezavaneho polygonu (na tabuli ten cerny)
//        slip polygon - orezavaci polygon (na tabuli ten zleny)
//        out - seznam vrcholu orezavaneho polygonu
        List <Point> in = polygon;

        Point p1 = null; //vloz posledni clip point

        for(Point p2 : clipPolygon){
            List<Point> out = new ArrayList<>();
//            vytvor hranu z bodu p1 a p2
//            Point v1 = in.last
            for(Point v2: in){
//                TODO algoritmus
            }
            p1 = p2;
            in = out; // aktualizuj orezavany polygon
        }

        return in;
    }

    /*
    public void drawPolygon(List<Integer> points) {
        clear();
        drawLine(points.get(0), points.get(1), points.get(2), points.get(3));
        i += 2;
        // for cyklus po dvou se správným omezením
        drawLine(points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3));
    }
    */
}
