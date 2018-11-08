package c_02_utery_18_15.fileHandler;

import c_02_utery_18_15.Fill.Patterns;
import c_02_utery_18_15.model.Point;


import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class FileHandler {
    private Patterns patterns;
    private List<Point> list;
    private final File FILE = new File("patterns.csv");
    public FileHandler(List<Point> list) {
        this.list = list;
    }
    public FileHandler(){
        this.list = new ArrayList<>();
    }

    public  void setList(List <Point> list){
        this.list = list;
    }
    public void save(){
        try{
            File file = new File("patterns.csv");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Point point : list){
               bufferedWriter.write(point.x + ";" + point.y + ";" + point.color.getRed() + ";" + point.color.getGreen() + ";" + point.color.getBlue());
               bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public List<Point> load(){
        try{
            String file = "patterns.csv";
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,  "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                String[] coordinates = strLine.split(";");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                int r = Integer.parseInt(coordinates[2]);
                int g = Integer.parseInt(coordinates[3]);
                int b = Integer.parseInt(coordinates[4]);
                Color color = new Color(r, g, b);
                Point point = new Point(x,y, color);
                list.add(point);
            }
        }catch (Exception e){
            System.out.println(e);
        }

            return list;
    }


}
