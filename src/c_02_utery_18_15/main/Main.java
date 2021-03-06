package c_02_utery_18_15.main;

import c_02_utery_18_15.controller.PgrfController;
import c_02_utery_18_15.view.PgrfWindow;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PgrfWindow window = PgrfWindow.getInstance();
            new PgrfController(window);
            window.setExtendedState(Frame.MAXIMIZED_BOTH);
            window.setVisible(true);


        });

        // https://www.google.com/search?q=SwingUtilities.invokeLater
        // https://www.javamex.com/tutorials/threads/invokelater.shtml
    }
}
