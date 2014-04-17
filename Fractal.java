// The Fractal class deals with the maths of the program and the colouring of 
// the pixels to display the fractal

import java.awt.*;

class Fractal
{
    private static final int abs_limit = 4;
    private static final int iterations_limit = 255;
    private static int size;                                
    private String type;
    private static Color [] colours = new Color[iterations_limit + 1];

    // Fractal constructor

    Fractal(int sz, String tp)
    {
        size = sz;
        type = tp;
    }
    
    public void colorize()
    {
        float baseBrightness = 0.8f;
        float baseSaturation = 0.9f;
        for (int i=0; i<iterations_limit; i++)
            colours[i]=Color.getHSBColor((float)i/(float)iterations_limit,
                                        baseSaturation,
                                        baseBrightness);
        colours[iterations_limit]=Color.black;
    }
    
    // Iterates a single point in the fractal and returns the colour 
    // corresponding to the number of iterations before the absolute value 
    // diverges.

    public Color iterate(double ca, double cb)
    {
        int iterations = 0;
        double za = 0;
        double zb = 0;
        double zabs = 0;
        while(iterations < iterations_limit && zabs < abs_limit)
        {
            double za_new = ca;
            double zb_new = cb;
            double za2 = za*za;
            double zb2 = zb*zb;
            if (type.equals("Mandelbar"))
            {
                za_new = za2 - zb2 + ca;
                zb_new = -2 * za * zb + cb;
            }
            else if (type.equals("Quartic Mandelbar"))
            {
                za_new = za2*za2 + zb2*zb2 - 6 * za2 * zb2 + ca;
                zb_new = 4 * za * zb * (zb2 - za2) + cb;
            }
            else if (type.equals("Burning Ship"))
            {
                za_new = za2 - zb2 + ca;
                zb_new = 2 * Math.abs(za) * Math.abs(zb) + cb;
            }
            else if (type.startsWith("F"))
            {
                zb_new = -2 * za * zb;
                za_new = za2 - zb2;
                zb_new = 2 * za_new * zb_new;
                za_new = za_new*za_new - zb_new*zb_new;
                zb_new += cb;
                za_new += ca;

            }
            else if (type.startsWith("Julia"))
            {
                if (za2 + zb2 == 0d)
                {
                    za_new = ca*ca - cb*cb + 0.300283;
                    zb_new = 2 * ca * cb + 0.48857;
                }
                else
                {
                    za_new = za2 - zb2 + 0.300283;
                    zb_new = 2 * za * zb + 0.48857;
                }
            }
            else if (type.equals("Quartic Mandelbrot"))
            {
                za_new = za2*za2 + zb2*zb2 - 6 * za2 * zb2 + ca;
                zb_new = 4 * za * zb * (za2 - zb2) + cb;
            }
            else
            {
                za_new = za2 - zb2 + ca;
                zb_new = 2 * za * zb + cb;
            }
                za = za_new;
                zb = zb_new;
                iterations++;
                zabs = za_new*za_new + zb_new*zb_new;
        }
            return colours[iterations];
    }

    // Testing the class

    public static void main(String[] args)
    {
        Fractal frac1 = new Fractal (50, "Mandelbrot");
        frac1.colorize();
        Fractal.test(frac1.iterate(0, 0), Color.black,
            "Error: incorrect colour");
        Fractal.test(frac1.iterate(-1.138, -0.252), Color.black,
            "Error: incorrect colour");
        Fractal.test(frac1.iterate(-0.16, 1.03), Color.black,
            "Error: incorrect colour");
    }

    // Test that an actual value matches an expected value, printing the
    // given message and throwing an error if not

    private static void test(Object actual, Object expected, String message)
    {
        if (actual.equals(expected)) return;
        System.err.print(message);
        System.err.print(": " + actual);
        System.err.println(", not " + expected);
        throw new Error("Testing failed.");
    }
}
