package c_02_utery_18_15.view;

import javax.swing.*;
import java.awt.*;

public class LoadDialog extends JDialog{
    public JButton load;
    public JTextField file;

    public LoadDialog() {
        setTitle("Load pattern file");

        JPanel panel = new JPanel(new BorderLayout());

        this.load = new JButton("Load");
        panel.add(load, BorderLayout.EAST);

        this.file = new JTextField();
        panel.add(file, BorderLayout.WEST);
    }
}
