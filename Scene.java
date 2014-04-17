// Creates a JFrame, and a MainWindow object, and initialises the program.

import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class Scene
{
    private String str = null;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
                {
                    makeGUI(); 
                }
        });
    }

    private static void makeGUI()
    {
        JFrame f =
            new JFrame("SuperDuper Amazingly Magical Kingdom of Fractals");
        MainWindow panel = new MainWindow();
        panel.setFocusable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(panel);
        f.pack();
        f.setVisible(true);
        panel.popup();
    }
}
