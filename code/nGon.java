package com.jetbrains;

/******************************************************************************
 *  Compilation:  javac Ngon.java
 *  Execution:    java Ngon n
 *  Dependencies: Turtle.java
 *
 *  Plots a regular n-gon.
 *
 *  The side length s of a regular n-gon inscribed in a circle
 *  of diameter 1 is sin(pi/n).
 *
 ******************************************************************************/
/*
Regular n-gons. Ngon_YI.java takes a command-line argument or from a Scanner object n and draws a regular n-gon using turtle graphics or StdDraw. By taking n to a sufficiently large value,
we obtain a good approximation to a circle represented with polygons. Assume the polygon is inscribed in the circle.

Show your images as n increases. The program Ngon_YI.java finds the smallest value for n to get the best approximation to a circle. Recursion is required for full credit.
The input argument's purpose is to determine how many sides you want to start with.

Explain clearly what your approach is in your documentation. This documentation is worth 25% of the assignment's grade.

NOTE: you can use StdDraw or Turtle.

To be clear your assignment is not complete unless you submit three parts:

a. An animation showing how the polygon becomes a circle as the number of sides increases. This part does not require recursion.

b. A recursive computation of the area of the polygon.

c. Justify your final number of sides that approximate the area of the circle. You must showing explicitly why it is in fact the least number of sides.

This site from Khan Academy might help you understand the general idea.
 */

//imports
import java.awt.Color;
import java.util.ArrayList;




public class nGon
{
    /*
        INSTANCE FIELDS
     */
    private double angle; //direction
    private double step; //side length of polygon
    private double x, y; //current position of point on polygon
    private int n; //number of sides of polygon

    /*
        CONSTRUCTOR
     */
    public nGon(double angle, double step, double x, double y, int n)
    {
        this.angle = angle;
        this.step = step;
        this.x = x;
        this.y = y;
        this.n = n;
    }
    /*
        GETTER METHODS
     */
    public double getAngle()
    {
        return angle;
    }
    public double getStep()
    {
        return step;
    }
    public int getN()
    {
        return n;
    }
    /*
        TURN METHOD
        changes the angle, which determines the direction of the next line drawn
    */
    public void turn(double delta)
    {
        this.angle += delta;
    }
    /*
        DRAWING A SINGLE POLYGON METHOD
        DRAWS THE FIRST LINE OF THE POLYGON (singular) , CHANGES DIRECTION, DRAWS ANOTHER LINE, ETC
     */
    public void draw()
    {
        for (int i = 0; i < n; i++) { //draws as many lines as there are sides of the polygon
            StdDraw.pause(120-(i*50)); //pause for some time so that it looks animated, but as the number of sides increases pause for less and less time, until no longer pausing (when .pause() takes a negative, it counts as 0)
            double oldx = x; //keeping track of previous to draw new line
            double oldy = y;
            x += step * Math.cos(Math.toRadians(angle)); // determine where the next coordinate
            y += step * Math.sin(Math.toRadians(angle)); // will go based on current direction (angle)
            StdDraw.line(oldx, oldy, x, y); //draw line
            turn(360.0/n); //change direction for next drawing
        }
    }

    /*
    DRAW ALL THE POLYGONS
     */
    public static void drawNGons(ArrayList<nGon> polygons) //this is static because it utilizes multiple Ngon objects and requires an array list of Ngons as a parameter
    {
        StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(-.6,.6);
        StdDraw.setYscale(-.6,.6);

        StdDraw.clear(new Color(100,100,230));
        StdDraw.setPenColor(new Color(255,255,255)); //set pen color to make the outer circle white
        StdDraw.circle(0,0,.50); //draw the circle encompassing the polygons
        StdDraw.setPenColor(new Color(0,0,0));//make the polygon lines black
        /*
        (ABOVE) SET UP STD.DRAW / THE SCREEN BEFORE DRAWING THE POLYGONS AND CIRCLE
         */

        for (nGon polygon: polygons) //iterate for the number of polygons (for each loop)
            {
                polygon.draw(); //draw the polygon
            }
        StdDraw.pause(100000000);
        //StdDraw.setPenColor(new Color(255,255,100)); //set pen color to make the outer circle white
        //StdDraw.circle(0,0,.50); //draw the circle encompassing the polygons
    }

    /*
    CALCULATE AREA METHOD (recursive ish)

     */
    public static double calculateArea(ArrayList<nGon> polygons, int i) //i is n for each polygon
    { //recursion??? why, do you mean total area of all the polygons?
        if(i < 3)
        {
            return 0;
        }
        //A = (1/4)na^2 cot(π/n) = nr^2 tan(π/n)
        double area = .25*i*Math.pow(polygons.get(i-3).getStep(),2)*(1/(Math.tan(Math.PI/i)));

        return area; //+ calculateArea(polygons, i-1);
    }


    public static void main(String[] args)
    {

        int n = Integer.parseInt("100"); //take n as command line argument
        ArrayList<nGon> polygons = new ArrayList<>();
        for(int i = 3; i<=n; i++) //i only want to start creating turtles which draw object with 3 sides (starting from a triangle, as the others have no area (I don't care about them))
        {
            polygons.add(new nGon(180.0/i, Math.sin(Math.toRadians(180.0/i)), 0,-.5, i)); //create each polygon object then add it to the array list of polygons
            /*
            Description of polygons.add(...) (above)
            @Param 1
            the first parameter is the angle which must be in terms of i, as when iterating over i in this scenario, i is n for that particular polygon
            @Param 2
            The "step" or side length of that polygon based on the equation for determining side length based on n (again i is n for each polygon)
            @Param 3
            X position of the first point on polygon before drawing line then turning
            @Param 4
            Y Position of the first point on polygon before drawing line then turning
            @Param 5
            This is the n (number of sides) for the particular polygon
             */
        }
        //drawGons is static, wouldn't make sense to use a method from a single polygon object which draws all other polygons
        drawNGons(polygons); //draw the polygons
        StdDraw.setPenColor(new Color(255,255,100)); //set pen color to make the outer circle white
        StdDraw.circle(0,0,.50); //draw the circle encompassing the polygons
        System.out.println((Math.PI*.5*.5) / (calculateArea(polygons, n)));
    }
}
/*
OUTPUT:
for less than 1 percent variation to a circles area, smallest n is n = 26
for less than 5 percent variation from the circles area, smallest n is n = 12
for less than 10 percent variation from the circle area, smallest n is n = 9

 */

