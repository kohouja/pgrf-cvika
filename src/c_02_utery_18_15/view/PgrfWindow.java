package c_02_utery_18_15.view;

import sun.font.TextLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PgrfWindow extends JFrame {

    private Font font = new Font("Arial", Font.PLAIN, 10);

    private static PgrfWindow instance;
    public static PgrfWindow getInstance(){
        if(instance == null){
            instance = new PgrfWindow();
        }
        return instance;
    }

    private PgrfWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(Raster.WIDTH, Raster.HEIGHT); // velikost okna
        setLocationRelativeTo(null);// vycentrovat okno
        setTitle("PGRF1 cvičení"); // titulek okna

//        TODO set layout to label pyco
        JLabel instructions = new JLabel();
        instructions.setText("instructions");
        instructions.setFont(font);
        instructions.setForeground(Color.WHITE);

        JPanel jPanel = new JPanel();
        JButton jButton = new JButton("kokotina");
        jPanel.add(jButton);
        add(jPanel, BorderLayout.NORTH);

    }

}
