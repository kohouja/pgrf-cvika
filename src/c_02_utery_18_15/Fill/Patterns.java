package c_02_utery_18_15.Fill;

import c_02_utery_18_15.fileHandler.FileHandler;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.view.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Patterns {
    private FileHandler fileHandler;
    private Raster raster;
    private List<Point> pointsList;
    private List<Point> transformList = new ArrayList<>();

    public void init(Raster raster){
           setRaster(raster);
    }

    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    public Patterns() {
        this.pointsList = new ArrayList<>();
    }

    public List<Point> getPointsList() {
        return pointsList;
    }

    public void setPointsList(List<Point> pointsList){
        this.pointsList = pointsList;
    }

    public void drawCircle(){

         for(int i = 0; i<=20; i++){
             for(int q = 0; q<=20; q++){
                 raster.drawPixel(q, i, 0xff0000);
             }
         }


    }

    public void loadCigaro(){
        fileHandler = new FileHandler();
        this.setPointsList(fileHandler.load());
        for(Point point : this.getPointsList()){
            raster.drawPixel(point.x, point.y, point.color.getRGB());
        }
    }

    public void drawCigaro(){
        for(Point point : this.getPointsList()){
            raster.drawPixel(point.x, point.y, point.color.getRGB());
        }
    }

    public Point findZeroPoint(){
        int minX = pointsList.get(0).x;
        int minY = pointsList.get(0).y;
        for(Point point : pointsList){
            if(point.x < minX){
                minX = point.x;
            }
            if(point.y < minY){
                minY = point.y;
            }
        }
        Color color = new Color(255,255,255);
        return new Point(minX, minY, color);
    }

    public void transformPattern(int x, int y){
        Point zeroPoint = new Point(pointsList.get(pointsList.size()-1).x, pointsList.get(pointsList.size()-1).y);
        for(Point point : pointsList){
            transformList.add(new Point(point.x - zeroPoint.x, point.y - zeroPoint.y, point.color));
        }
        zeroPoint = new Point(x, y);
        for(int i = 0; i<pointsList.size(); i++){
            pointsList.get(i).x = transformList.get(i).x + zeroPoint.x;
            pointsList.get(i).y = transformList.get(i).y + zeroPoint.y;
        }

    }

}
