// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102/112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2022T1, Assignment 6
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/** Ball represents a ball that is launched by the mouse towards a direction.
 *    Each time the step() method is called, it will take one step.  
 * For the Completion part, gravity should act on the ball by reducing its vertical speed.
 */

public class Ball{

    // Constants for all balls: size, position of the ground
    public static final double DIAM = 16;  // diameter of the balls.
    public static final double GROUND = BallGame.GROUND;
    public static final double RIGHT_END = BallGame.RIGHT_END;

    // Fields to store state of the ball:
    // x position, height above ground, stepX, stepY, colour
    //   The ball should initially be not moving at all. (step should be 0)
    /*# YOUR CODE HERE */
    
    private double xcoor;
    private double ycoor;
    
    private double right = 0;
    private double up = 0;
    private double gravity = 0;
    
    
    
    // The below code is the unique gravity of each bounce.
    // I could just use one gravity and reset it everytime but I prefer this method to make better sense of it
    private double bounce_up = 0;
    private double bounce_grav = -2;
    private double bounce_grav_2 = -2;
    private double bounce_grav_3 = -2;
    private double bounce_grav_4 = -2;
    private double bounce_grav_5 = -2;
    
    private Color color;

    // Constructor
    /** Construct a new Ball object.
     *    Parameters are the initial position (x and the height above the ground),
     *    Stores the parameters into fields 
     *    and initialises the colour to a random colour
     *  SHOULD NOT DRAW THE BALL!
     */
    public Ball(double x, double h, Color col){
        /*# YOUR CODE HERE */
        
        Color color = col;
        xcoor = x;
        ycoor = h;

    }

    // Methods
    /**
     * Draw the ball on the Graphics Pane in its current position
     * (unless it is past the RIGHT_END )
     */
    public void draw(Color color){
        /*# YOUR CODE HERE */
        
        //UI.setLineWidth(3);
        //Color black = (java.awt.Color.black);
        //UI.setColor(black);
        //UI.drawOval(xcoor - (DIAM * 0.5), GROUND - ycoor - DIAM, DIAM, DIAM);
        UI.setColor(color);       
        UI.fillOval(xcoor - (DIAM * 0.5), GROUND - ycoor - DIAM, DIAM, DIAM);

    }

    /**
     * Move the ball one step (DO NOT REDRAW IT)
     * Core:
     *    Change its height and x position using the vertical and horizonal steps
     * Completion:
     *    Reduce its vertical speed each step (due to gravity), 
     *    If it would go below ground, then change its y position to ground level and
     *      set the  vertical speed back to 0.
     */
    
    public void core_step(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += up;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target. It is also affected by gravity
     */
    public void step(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += up - gravity;
        gravity += 0.25;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target for the first bounce
     */
    public void bounce1(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += 8 - bounce_grav;
        bounce_grav += 0.5;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target for the second bounce. notice y coor is now 7 rather than 8
     */
    public void bounce2(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += 7 - bounce_grav_2;
        bounce_grav_2 += 0.65;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target for the third bounce. again now the y coor is at 6 rather than 7.
     */
    public void bounce3(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += 6 - bounce_grav_3;
        bounce_grav_3 += 0.8;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target for the fourth bounce. The y coor is now at 4 rather than 6
     */
    public void bounce4(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += 4 - bounce_grav_4;
        bounce_grav_4 += 0.8;

    }
    
    /**
     * The method below snapshots the change in location of the ball or target for the fifth bounce. Finally the ycoor is at 2 where it will no longer bounce afterwards.
     */
    public void bounce5(){
        /*# YOUR CODE HERE */
        
        xcoor += right;
        ycoor += 2 - bounce_grav_5;
        bounce_grav_5 += 0.8;

    }
    

    /**
     * Set the horizontal speed of the ball: how much it moves to the right in each step.
     * (negative if ball going to the left).
     */
    public void setXSpeed(double xSpeed){
        /*# YOUR CODE HERE */
        
        right = xSpeed;

    }

    /**
     * Set the vertical speed of the ball: how much it moves up in each step
     * (negative if ball going down).
     */
    public void setYSpeed(double ySpeed){
        /*# YOUR CODE HERE */
        up = ySpeed;
        

    }
    
    /**
     * Set the horizontal speed of the ball: how much it moves to the right in each step.
     * (negative if ball going to the left).
     */
    public void setXCoor(double xCoor){
        /*# YOUR CODE HERE */
        
        xcoor = xCoor;

    }

    /**
     * Set the vertical speed of the ball: how much it moves up in each step
     * (negative if ball going down).
     */
    public void setYCoor(double yCoor){
        /*# YOUR CODE HERE */
        ycoor = yCoor;
        

    }

    /**
     * Return the height of the ball above the ground
     */
    public double getHeight(){
        /*# YOUR CODE HERE */
        return ycoor;

    }

    /**
     * Return the horizontal position of the ball
     */
    public double getX(){
        /*# YOUR CODE HERE */
        return xcoor;

    }
    
    /**
     * Return the height of the ball above the ground
     */
    public double getYSpeed(){
        /*# YOUR CODE HERE */
        return up;

    }

    /**
     * Return the horizontal position of the ball
     */
    public double getXSpeed(){
        /*# YOUR CODE HERE */
        return right;

    }


}
