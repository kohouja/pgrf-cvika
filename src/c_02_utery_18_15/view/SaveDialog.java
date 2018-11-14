package c_02_utery_18_15.view;

import javax.swing.*;
import java.awt.*;

public class SaveDialog extends JDialog {
    public JButton save;
    public JTextField file;
    public boolean instance = false;
    public SaveDialog() {
        setTitle("Load pattern file");

        this.save = new JButton("Save");
        add(save, BorderLayout.EAST);

        this.file = new JTextField();
        file.setPreferredSize(new Dimension(250, 20));
        add(file, BorderLayout.WEST);

        this.instance = true;
        pack();
        setVisible(true);

    }

}

