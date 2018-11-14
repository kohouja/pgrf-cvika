package c_02_utery_18_15.view;

import sun.font.TextLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PgrfWindow extends JFrame {
    public JButton drawMode, save, load, clipPolygonMode, fillByPattern, scanLineFill, clear, totalClear;
    public LoadDialog loadDialog = new LoadDialog();
    public SaveDialog saveDialog = new SaveDialog();

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

        this.drawMode = new JButton("DrawMode");
        jPanel.add(drawMode);

        this.save = new JButton("Save");
        this.save.setEnabled(false);
        jPanel.add(save);

        this.load = new JButton("Load");
        this.load.setEnabled(false);
        jPanel.add(load);


        this.clipPolygonMode = new JButton("ClipPolygonMode");
        jPanel.add(clipPolygonMode);

        this.fillByPattern = new JButton("FillByPattern");
        this.fillByPattern.setEnabled(false);
        jPanel.add(fillByPattern);

        this.scanLineFill = new JButton("ScanLineFill");
        this.scanLineFill.setEnabled(false);
        jPanel.add(scanLineFill);

        this.clear = new JButton("Clear");
        jPanel.add(clear);

        this.totalClear = new JButton("TotalClear");
        totalClear.setEnabled(false);
        jPanel.add(totalClear);

        saveDialog.setVisible(false);
        loadDialog.setVisible(false);




        add(jPanel, BorderLayout.NORTH);




    }

}
