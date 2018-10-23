package c_02_utery_18_15.controller;

import c_02_utery_18_15.renderer.Renderer;
import c_02_utery_18_15.fill.SeedFill;
import c_02_utery_18_15.view.PgrfWindow;
import c_02_utery_18_15.view.Raster;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PgrfController {

    private Raster raster;
    private Renderer renderer;
    private SeedFill seedFill;

    public PgrfController(PgrfWindow window) {
        initObjects(window);
        initListeners();
    }

    private void initObjects(PgrfWindow window) {
        raster = new Raster();
        window.add(raster); // vložit plátno do okna

        renderer = new Renderer(raster);

        seedFill = new SeedFill();
        seedFill.setRaster(raster);
    }

    private void initListeners() {

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                raster.clear();
                renderer.drawDDA(400, 300, e.getX(), e.getY(), 0x00ffff);
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
        // chceme, aby canvas měl focus hned při spuštění
        raster.requestFocus();
    }

}
