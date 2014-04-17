// The MainWindow class calls the colorize method from the Fractal class to 
// draw the fractal.  It also deals with the mouse listener, the key listener, 
// and the pop-up menu box.

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class MainWindow extends JPanel
{
    private static final int size = 500;
    private double scale = 3;
    private double inita, initb;
    private String str = null;
    private Fractal frac;
    private BufferedImage img = new
            BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
    
    // The draw method converts screen co-ordinates to fractal co-ordinates, 
    // gets the colours from the iterate method in the Fractal class and sets 
    // each pixel in the fractal as a colour

    public void draw(Fractal frac, boolean recentre)
    {
        frac.colorize();
        if (recentre)
        {
            scale = 3;
            inita = -0.25;
            initb = 0;
        }
        double x, y;
        
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                x = (scale / size) * (i-size*0.5) + inita;
                y = (scale / size) * (j - size*0.5) + initb;
                img.setRGB(i,j,frac.iterate(x,y).getRGB());
            }
        }
        repaint();
    }
    
    // The popup method creates the popup menu for the user to choose fractals 
    // and to display instructions of how to use the program

    public void popup()
    {
         String str_cp = str;
         Object[] possibilities =
             {"Mandelbrot", "Quartic Mandelbrot", "Mandelbar",
             "Quartic Mandelbar", "Julia, c = 0.300283 + 0.48857i", "Fish",
             "Burning Ship"};
         
         str = (String)JOptionPane.showInputDialog(this,
             "Press the left and right mouse buttons to zoom in and out.\n" +
             "Press the middle mouse button to reposition the centre point.\n" 
             + "Press any key to choose another fractal.\n\n"
             + "Choose the type of fractal you want:\n\n",
             "Welcome to Fractals",
         JOptionPane.PLAIN_MESSAGE, null, possibilities, str);
         
         if (str_cp == null && str == null)
             System.exit(0);
         else if (str_cp == str) this.draw(frac, true);
         else if(str != null)
         {
             frac = new Fractal(size,str);
             this.draw(frac, true);
         }
         else str = str_cp;
    }
    
    // The MainWindow method contains the mouse listener which allow the user 
    // to zoom in/out and centralise the fractal, and the key listener which 
    // allows the user to press any key to bring back the popup menu

    public MainWindow()
    {
        addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    inita = (scale / size) * (e.getX() - size/2) + inita;
                    initb = (scale / size) * (e.getY() - size/2) + initb;
                    if (e.getButton() == 1)
                        scale *= 0.3;
                    if (e.getButton() == 3)
                        scale /= 0.3;
                    draw(frac, false);
                }
            });
        addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    popup();
                }
            });
    }
    
    // Sets the size of the window that displays the fractal

    public Dimension getPreferredSize() {
        return new Dimension(size,size);
    }
    
    // Sets the properties of the window

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img,0,0,null);
        g.setColor(Color.white);
        g.drawString("Centre: (" + inita + ", " + initb + ")",
            10, size - 25);
        g.drawString("Window width: " + scale, 10, size - 10);
    }
}
