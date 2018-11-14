package c_02_utery_18_15.view;

import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;

public class LoadDialog extends JDialog{
    public JButton load;
    public JTextField file;

    public LoadDialog() {
        setTitle("Load pattern file");



        this.load = new JButton("Load");
        add(load, BorderLayout.EAST);

        this.file = new JTextField();
        file.setToolTipText("neco");
        file.setPreferredSize(new Dimension(250, 20));
        add(file, BorderLayout.WEST);




        pack();
        setVisible(true);
    }

}
