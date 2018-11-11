package c_02_utery_18_15.fill;

import c_02_utery_18_15.fileHandler.FileHandler;
import c_02_utery_18_15.model.Line;
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
    private List<Line> lineList = new ArrayList<>();

    public void init(Raster raster){
           setRaster(raster);
    }

    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    public Patterns() {
        this.pointsList = new ArrayList<>();
        loadCigaro();
        sortPointsByLines();
    }

    public List<Point> getPointsList() {
        return pointsList;
    }
    public List<Line> getLineList(){return lineList;}

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
        this.setPointsList(fileHandler.load("cigaro.csv"));
//        for(Point point : this.getPointsList()){
//            raster.drawPixel(point.x, point.y, point.color.getRGB());
//        }
    }

    public void drawCigaro(){
        loadCigaro();
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
        Color color = new Color(0,0,0);
        return new Point(minX, minY, color);
    }

    public Point findMaxPoint(){
        int maxX = 0;
        int maxY = 0;
        for(Point point : pointsList){
            if(point.x > maxX){
                maxX = point.x;
            }
            if(point.y > maxY){
                maxY = point.y;
            }
        }
        Color color = new Color(0,0,0);
        return new Point(maxX, maxY, color);
    }

    public int getPatternWidth(){
        return findMaxPoint().x - findZeroPoint().x;
    }

    public int getPatternHeight(){
        return findMaxPoint().y - findZeroPoint().y;
    }

    public void sortPointsByLines(){
        Point zeroPoint = findZeroPoint();
        Point maxPoint = findMaxPoint();
        for(int i = zeroPoint.y; i < maxPoint.y; i++){
            Line line = new Line();
            for(Point randomPlacedPoint : pointsList){
                if(randomPlacedPoint.y == i){
                    line.getPointList().add(randomPlacedPoint);
                }
            }
            lineList.add(line);
        }
    }

    public void drawPatternLine(int numberOfLine, int numberOfColumn){
        List<Point> thatLine = lineList.get(numberOfLine).getPointList();
        for(Point point : thatLine){
            raster.drawPixel(point.x + numberOfColumn, point.y, point.color.getRGB());
        }
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

    public List<Point> getAbsolutePointLineList(int newZeroPointX, int numberOfLine, int numberOfRow){
        List<Point> absolutePointLineList = new ArrayList<>();
        List<Point> line = lineList.get(numberOfLine).getPointList();
        Point originZeroPoint = findZeroPoint();
        int originZeroPointX = originZeroPoint.x;
        int originZeroPointY = originZeroPoint.y;
        for(Point point : line){
            int relativeXposition = point.x - originZeroPointX;
            int absoluteXposition = relativeXposition + newZeroPointX;
            int relativeYposition = point.y - originZeroPointY;
            int absoluteYposition = relativeYposition + numberOfRow*getPatternHeight();
            absolutePointLineList.add(new Point(absoluteXposition, absoluteYposition, point.color));
        }
        return absolutePointLineList;
    }


}
