package c_02_utery_18_15.controller;

import c_02_utery_18_15.fill.Patterns;
import c_02_utery_18_15.fill.ScanLine;
import c_02_utery_18_15.fill.SeedFill;
import c_02_utery_18_15.fileHandler.FileHandler;
import c_02_utery_18_15.model.Point;
import c_02_utery_18_15.renderer.Renderer;
import c_02_utery_18_15.view.PgrfWindow;
import c_02_utery_18_15.view.Raster;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PgrfController {
    private PgrfWindow pgrfWindow;
    private Raster raster;
    private Renderer renderer;
    private SeedFill seedFill;
    private boolean seedFillSwitch;
    private Patterns patterns;
    private FileHandler fileHandler;
    private final List<Point> polygonPoints = new ArrayList<>();
    private final List<Point> clipPoints = new ArrayList<>();
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


    }

    private void initListeners() {
        pgrfWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                raster.resize(size);
            }
        });

//         raster.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mousePressed(MouseEvent e) {
//                       if(!e.isControlDown()){
//                           polygonPoints.add(new Point(e.getX(), e.getY()));
//                           if(polygonPoints.size() == 1){
//                               polygonPoints.add(new Point(e.getX(), e.getY()));
//                           }else if(SwingUtilities.isRightMouseButton(e)){
//                               linePoints.add(new Point(e.getX(), e.getY()));
//                               linePoints.add(new Point(e.getX(), e.getY()));
//
//                           }
//                           update();
//                       }
//                    }
//                });

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("neco jako");
                if (e.isControlDown()) {
                    seedFill.init(e.getX(), e.getY(), 0xffff00);
                    seedFill.fill();
                } else {
                    raster.drawPixel(e.getX(), e.getY(), 0xffffff);
                }

                //points.add(e.getX());
                //points.add(e.getY());
                //renderer.drawPolygon(points);
            }
        });
//        raster.addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                if(SwingUtilities.isLeftMouseButton(e)){
//                    polygonPoints.get(polygonPoints.size() - 1).x = e.getX();
//                    polygonPoints.get(polygonPoints.size() - 1).y = e.getY();
//                    renderer.drawPolygon(polygonPoints, 0x00ffff);
//
//                }else if(SwingUtilities.isRightMouseButton(e)){
//                   linePoints.get(linePoints.size() - 1).x = e.getX();
//                   linePoints.get(linePoints.size() - 1).y = e.getY();
//                   renderer.drawLines();
//
//                }
//                update();
////                raster.clear();
////                renderer.drawDDA(400, 300, e.getX(), e.getY(), 0x00ffff);
//            }
//        });
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
//                    System.out.println(patterns.findZeroPoint().x);
//                    System.out.println(patterns.findZeroPoint().y);

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
    }

}
