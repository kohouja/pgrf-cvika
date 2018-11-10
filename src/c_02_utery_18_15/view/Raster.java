package c_02_utery_18_15.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Raster extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final int FPS = 1000 / 30;
    private Integer resizableWidth = WIDTH;
    private Integer resizableHeight = HEIGHT;
    private BufferedImage bi;

    public Raster() {
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        setLoop();
    }

    public int getResizableWidth() {
        return resizableWidth;
    }

    public int getResizableHeight() {
        return resizableHeight;
    }

    public void resize(Dimension size){
        this.bi = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        this.resizableWidth = size.width;
        this.resizableHeight = size.height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bi, 0, 0, null);
    }

    private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // říct plátnu, aby zobrazil aktuální img
                if (getGraphics() == null) return; // kontrola, protože graphics je null, dokud se nezavolá window.setVisible(true)
                getGraphics().drawImage(bi, 0, 0, null);
                // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
            }
        }, 0, FPS);
    }

    public void clear() {
        // https://stackoverflow.com/a/5843470
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        if(!resizableWidth.equals(null) && !resizableHeight.equals(null)){
            g.clearRect(0, 0, resizableWidth, resizableHeight);
        }else{
            g.clearRect(0, 0, WIDTH, HEIGHT);
        }

    }

    public void drawPixel(int x, int y, int color) {
        // nastavit pixel do img
        if (x < 0 || x >= this.getResizableWidth()) return;
        if (y < 0 || y >= this.getResizableHeight()) return;
        bi.setRGB(x, y, color);
    }

    public int getPixel(int x, int y) {
        return bi.getRGB(x, y);
    }

}
