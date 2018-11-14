package c_02_utery_18_15.controller;

import c_02_utery_18_15.clip.ClipPolygonFactory;
import c_02_utery_18_15.fill.Patterns;
import c_02_utery_18_15.fill.ScanLine;
import c_02_utery_18_15.fill.SeedFill;
import c_02_utery_18_15.fileHandler.FileHandler;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.renderer.Renderer;
import c_02_utery_18_15.view.LoadDialog;
import c_02_utery_18_15.view.PgrfWindow;
import c_02_utery_18_15.view.Raster;
import c_02_utery_18_15.view.SaveDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class PgrfController {
    private boolean drawMode = false;
    private boolean clipMode = false;

    private PgrfWindow pgrfWindow;
    private Raster raster;
    private Renderer renderer;
    private SeedFill seedFill;
    private boolean seedFillSwitch;
    private Patterns patterns;
    private FileHandler fileHandler = new FileHandler();
    private ClipPolygonFactory clipPolygonFactory;
    private final List<Point> polygonPoints = new ArrayList<>();
    private List<Point> clippedPolygon = new ArrayList<>();
    private final List<Point> clipPolygonPoints = new ArrayList<>();
    private final List<Point> linePoints = new ArrayList<>();
    private final ScanLine scanLine = new ScanLine();



    public PgrfController(PgrfWindow window) {
        initObjects(window);
        initListeners();


    }

    private void initObjects(PgrfWindow window) {
        this.pgrfWindow = window;
        raster = new Raster();
        window.add(raster); // vložit plátno do okna

        // zprovozni key listeners
        raster.setFocusable(true);
        raster.requestFocusInWindow();

        renderer = new Renderer(raster);

        seedFill = new SeedFill();
        seedFill.setRaster(raster);

        patterns = new Patterns();
        patterns.setRaster(raster);

        scanLine.setPatterns(patterns);

        this.clipPolygonFactory = new ClipPolygonFactory();


    }

    private void initListeners() {
        pgrfWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                raster.resize(size);
            }
        });

        pgrfWindow.drawMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawMode = drawMode? false : true;
                if(drawMode){
                    pgrfWindow.save.setEnabled(drawMode);
                    pgrfWindow.load.setEnabled(drawMode);
                }else{
                    pgrfWindow.save.setEnabled(drawMode);
                    pgrfWindow.load.setEnabled(drawMode);
                }
                System.out.println(drawMode);
            }
        });

       pgrfWindow.clipPolygonMode.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               clipMode = clipMode? false : true;
               if(clipMode){
                   pgrfWindow.fillByPattern.setEnabled(clipMode);
                   pgrfWindow.scanLineFill.setEnabled(clipMode);
                   pgrfWindow.totalClear.setEnabled(clipMode);

               }else{
                   pgrfWindow.scanLineFill.setEnabled(clipMode);
                   pgrfWindow.fillByPattern.setEnabled(clipMode);
                   pgrfWindow.totalClear.setEnabled(clipMode);
               }
               System.out.println(clipMode);
           }
       });

       pgrfWindow.save.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               pgrfWindow.saveDialog.setLocationRelativeTo(pgrfWindow);
               pgrfWindow.saveDialog.setVisible(true);
           }
       });
       pgrfWindow.saveDialog.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patterns.getPointsList().add(patterns.findZeroPoint());
                fileHandler.setList(patterns.getPointsList());
                fileHandler.save(pgrfWindow.saveDialog.file.getText());
                pgrfWindow.saveDialog.setVisible(false);
            }
       });



       pgrfWindow.load.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               pgrfWindow.loadDialog.setLocationRelativeTo(pgrfWindow);
               pgrfWindow.loadDialog.setVisible(true);
           }
       });

        pgrfWindow.loadDialog.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patterns.setPointsList(fileHandler.load(pgrfWindow.loadDialog.file.getText()));
                patterns.drawPattern();
                pgrfWindow.loadDialog.setVisible(false);
            }
        });



       pgrfWindow.fillByPattern.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               patterns.loadCigaro();
               patterns.sortPointsByLines();
               scanLine.setRaster(raster);
               scanLine.init(polygonPoints, 0x000000, 0x00ffff);
               scanLine.fill();
               update();
               scanLine.fillByPattern();
               renderer.drawPolygon(polygonPoints, 0x00ffff);
           }
       });

       pgrfWindow.scanLineFill.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               scanLine.setRaster(raster);
               scanLine.init(polygonPoints, 0xff0000, 0x00ffff);
               scanLine.fill();
               renderer.drawPolygon(polygonPoints, 0x00ffff);
           }
       });

       pgrfWindow.clear.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               raster.clear();
           }
       });

       pgrfWindow.totalClear.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               totalClear();
           }
       });

       raster.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                   if(clipMode && !e.isControlDown()){
                       polygonPoints.add(new Point(e.getX(), e.getY()));
                       if(polygonPoints.size() == 1){
                           polygonPoints.add(new Point(e.getX(), e.getY()));
                       }else if(SwingUtilities.isRightMouseButton(e)){
                           linePoints.add(new Point(e.getX(), e.getY()));
                           linePoints.add(new Point(e.getX(), e.getY()));
                       }

                       update();
                   }
                }
       });

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown() && clipMode) {
                    seedFill.init(e.getX(), e.getY(), 0xffff00);
                    seedFill.fill();
                } else {
                    raster.drawPixel(e.getX(), e.getY(), 0xffffff);
                }
                if(drawMode && e.isShiftDown()){
                    patterns.transformPattern(e.getX(), e.getY());
                    raster.clear();
                    patterns.drawCigaro();
                }
            }

        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
               if(clipMode){
                   if(SwingUtilities.isLeftMouseButton(e)){
                       polygonPoints.get(polygonPoints.size() - 1).x = e.getX();
                       polygonPoints.get(polygonPoints.size() - 1).y = e.getY();
                       renderer.drawPolygon(polygonPoints, 0x00ffff);

                   }
                   renderer.drawPolygon(clipPolygonFactory.getPolygonPoints(), clipPolygonFactory.getColor());
                   update();
                   if(polygonPoints.size() > 2){
                       clippedPolygon = renderer.clip(polygonPoints, clipPolygonFactory.getPolygonPoints());
//
                       if (clippedPolygon.size() > 2) {
                           scanLine.setRaster(raster);
                           scanLine.init(clippedPolygon, 0xff0000, 0x00ffff);

                           scanLine.fill(clippedPolygon);
                           renderer.drawPolygon(clippedPolygon, 0xff0000);
                       }

                   }

                   renderer.drawPolygon(polygonPoints, 0x00ffff);

               }
               if(drawMode){
                    int color = 0xffffff;
                    raster.drawPixel(e.getX(), e.getY(), color);
                    patterns.getPointsList().add(new Point(e.getX(), e.getY(), new Color(color)));
               }
            }
        });

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.isShiftDown()){
                    patterns.transformPattern(e.getX(), e.getY());
                    raster.clear();
                    patterns.drawCigaro();
                }
            }
        });

        // chceme, aby canvas měl focus hned při spuštění
        raster.requestFocus();
    }

    private void update(){
        raster.clear();
        renderer.drawLines();
        renderer.drawPolygon(polygonPoints, 0x00ffff);
        renderer.drawPolygon(clipPolygonFactory.getPolygonPoints(), Color.WHITE.getRGB());
    }

    public void totalClear(){
        raster.clear();
        patterns.totalClear();
        polygonPoints.clear();
        clippedPolygon.clear();
        linePoints.clear();
        Math.atan2()
    }

}
