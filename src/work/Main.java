package work;

import gui.GUI;
import javax.swing.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final GUI frame = new GUI();
            }
        });
    }
}