package c_02_utery_18_15.controller;

import c_02_utery_18_15.clip.ClipPolygonFactory;
import c_02_utery_18_15.fill.Patterns;
import c_02_utery_18_15.fill.ScanLine;
import c_02_utery_18_15.fill.SeedFill;
import c_02_utery_18_15.fileHandler.FileHandler;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.renderer.Renderer;
import c_02_utery_18_15.view.PgrfWindow;
import c_02_utery_18_15.view.Raster;

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
    private FileHandler fileHandler;
    private ClipPolygonFactory clipPolygonFactory;
    private final List<Point> polygonPoints = new ArrayList<>();
    private List<Point> clippedPolygon;
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
               }else{
                   pgrfWindow.fillByPattern.setEnabled(clipMode);
               }
               System.out.println(clipMode);
           }
       });

       pgrfWindow.save.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               patterns.getPointsList().add(patterns.findZeroPoint());
               if(fileHandler != null){
                   fileHandler.setList(patterns.getPointsList());
               }else{
                   fileHandler = new FileHandler(patterns.getPointsList());
               }

               fileHandler.save();
           }
       });

       pgrfWindow.load.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                   patterns.drawCigaro();
           }
       });

       pgrfWindow.clear.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               raster.clear();
               if(clipMode){
//                   TODO smazat pyco polygony atd...
               }else if(drawMode){
//                   TODO smazat cigaro a nebo i nakreslenej navrh
               }
           }
       });

       raster.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                   if(clipMode){
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
            }
        });

        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyCode());
                // na klávesu C vymazat plátno
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    raster.clear();
                }
            }
        });

        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    System.out.println("kokot");
                    scanLine.setRaster(raster);
                    scanLine.init(polygonPoints, 0xff0000, 0x00ffff);
//                    scanLine.fill();
                    System.out.println("e = [" + e + "]");
                    scanLine.fillByPattern();
                    renderer.drawPolygon(polygonPoints, 0x00ffff);


                }

            }
        });
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    if(!seedFillSwitch){
                        seedFillSwitch = true;
                    }else{
                        seedFillSwitch = false;
                    }
                }
            }
        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(e.isAltDown()){
                    int color = 0xffffff;
                    raster.drawPixel(e.getX(), e.getY(), color);
                    patterns.getPointsList().add(new Point(e.getX(), e.getY(), new Color(color)));
                }
            }
        });

        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    patterns.getPointsList().add(patterns.findZeroPoint());
                    if(fileHandler != null){
                        fileHandler.setList(patterns.getPointsList());
                    }else{
                        fileHandler = new FileHandler(patterns.getPointsList());
                    }

                    fileHandler.save();
                }


            }
        });
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    patterns.drawCigaro();
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



}
