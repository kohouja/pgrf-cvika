package c_02_utery_18_15.main;

import java.awt.image.BufferedImage;

public class SeedFill implements Filler {

    private BufferedImage bi;
    private int x, y, color, background;

    @Override
    public void setBufferedImage(BufferedImage bi) {
        this.bi = bi;
    }

    public void init(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        background = bi.getRGB(x, y);
    }

    @Override
    public void fill() {
        seed(x, y);
    }

    // pozor na rekurzivní volání
    // nutné upravit parametr pro VM "-Xss100m"
    // https://stackoverflow.com/questions/4967885/jvm-option-xss-what-does-it-do-exactly
    private void seed(int ax, int ay) {
        if (ax >= 0 && ay >= 0 && ax < bi.getWidth() && ay < bi.getHeight()) {
            if (background == bi.getRGB(ax, ay)) {
                bi.setRGB(ax, ay, color);
                seed(ax + 1, ay);
                seed(ax - 1, ay);
                seed(ax, ay + 1);
                seed(ax, ay - 1);
            }
        }
    }
}
