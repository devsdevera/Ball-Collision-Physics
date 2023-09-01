// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102/112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2022T1, Assignment 6
 * Name: Emmanuel De Vera
 * Username: Deveremma
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.awt.Color;

/** Runs a simulation of launching balls at a target.
 */

public class BallGame{

    public static final double GROUND = 450;
    public static final double LAUNCHER_X = 20;      // The initial position of the ball being launched
    public static final double LAUNCHER_HEIGHT = 20; // The initial height of the ball being launched
    public static final double RIGHT_END = 600;
    public static final double SHELF_X = 400;
    public static final double MAX_SPEED = 14;
    public static final double LEFT = 100;  // the left side of the flags
    public static final double TOP = 50;

    private Ball ball; // the ball that is being launched towards the target
    // needs to be in a field because two different methods need to access it.

    /** Setup the mouse listener and the buttons */

    public void setupGUI(){
        UI.setMouseListener(this::launch); 
        UI.addButton("Clear", this::clear);// the mouse will launch the ball
        UI.addButton("Core 1T", this::runGameOneTarget);   // Initialises the game and runs the simulation loop
        UI.addButton("Completion 2T", this::runGameTwoTargets);   // Initialises the game and runs the simulation loop
        UI.addButton("Challenge", this::runGameChallenge);   // Initialises the game and runs the simulation loop
        UI.addButton("Quit", UI::quit);
        UI.setWindowSize(650,500);
        UI.setDivider(0);
    }    
    
    public void clear(){
        UI.clearGraphics();
        
    }
    /**
     * Main loop for the Core version of the game
     * It creates a ball (to be launched) and a target ball (on a shelf)
     * It has a loop that repeatedly
     *   - Makes a new ball if the old one has gone off the right end.
     *   - Makes the ball and the target ball take one step
     *     (unless they are still on the launcher or shelf)
     *   - Checks whether the ball is hitting the target
     *   - redraws the state of the game
     * The loop stops when the target has gone off the right end and the ball is on the launcher.
     */
    public void runGameOneTarget(){
        UI.printMessage("Click Mouse to launch the ball");
        
        Color color = (java.awt.Color.black);
        this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color);

        double shelfHeight = 50+Math.random()*400;
        Ball target = new Ball(SHELF_X, shelfHeight, color);

        this.drawGameOneTarget(this.ball, target, color, shelfHeight);
        int count = 0; 

        // run until the target is gone (ie, off the right end)
        while (ball.getX()!=LAUNCHER_X || target.getX()<RIGHT_END){

            //if the ball is over the right end, make a new one.
            if (ball.getX()>=RIGHT_END) {
                this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color);
                count++;
            }

            //move the ball, if it isn't on the launcher
            if (ball.getX() > LAUNCHER_X){
                this.ball.core_step();
            }            

            // move target if it isn't on the shelf
            if (target.getX()!= SHELF_X){
                target.core_step();
            }

            //if ball is hitting the target ball on the shelf, then make it start moving too
            double dist = Math.hypot(target.getX()-this.ball.getX(), target.getHeight()-this.ball.getHeight());
            if (target.getX()==SHELF_X && dist <= Ball.DIAM){
                target.setXSpeed(2);
                target.core_step();
            }

            //redraw the game and pause
            this.drawGameOneTarget(this.ball, target, color, shelfHeight);

            UI.sleep(20); // pause of 40 milliseconds

        }
        UI.setFontSize(40);
        UI.drawString(count+" tries", 200, 200);
    }

    /**
     * Launch the current ball, if it is still in the catapult,
     * Speed is based on the position of the mouse relative to the ground.
     */
    public void launch(String action, double x, double y){
        if (action.equals("released")){
            
            //UI.println(ball.getX());
            //UI.println(LAUNCHER_X);
            
            //UI.println();
            
            //UI.println(ball.getHeight());
            //UI.println(LAUNCHER_HEIGHT);
            
            
            if (this.ball==null) {
                UI.printMessage("Press Core/Completion button first to create a ball");
                return;  // the ball hasn't been constructed yet.
            }            
            if (this.ball.getX()==LAUNCHER_X && this.ball.getHeight()==LAUNCHER_HEIGHT){
                double speedX = (x-LAUNCHER_X)/10;
                double speedUp = (GROUND - LAUNCHER_HEIGHT - y)/10;
                double speed = Math.hypot(speedUp, speedX);
                //scale down if over the maximum allowed speed
                if (speed> MAX_SPEED){
                    speedUp = speedUp * MAX_SPEED/speed;
                    speedX = speedX * MAX_SPEED/speed;
                }
                //UI.println(speedX);
                //UI.println(speedUp);
                this.ball.setXSpeed(speedX);
                this.ball.setYSpeed( speedUp);
                this.ball.step();
            }
        }
    }

    /**
     * Draw the game: ball, target, ground, launcher and shelf.
     */
    public void drawGameOneTarget(Ball ball, Ball target, Color col, double shelfHeight){        
        UI.clearGraphics();
        ball.draw(col);
        target.draw(col);

        // draw ground, wall, launcher, and shelf
        UI.setColor(Color.black);
        UI.setLineWidth(2);
        UI.eraseRect(RIGHT_END, 0, RIGHT_END+100, GROUND);
        UI.drawLine(LAUNCHER_X, GROUND, RIGHT_END, GROUND);
        UI.drawLine(RIGHT_END, GROUND, RIGHT_END, 0);
        UI.drawLine(LAUNCHER_X, GROUND, LAUNCHER_X, GROUND-LAUNCHER_HEIGHT);
        UI.drawLine(LAUNCHER_X-Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT, LAUNCHER_X+Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT);
        UI.drawLine(SHELF_X-Ball.DIAM/2, GROUND-shelfHeight, SHELF_X+Ball.DIAM/2, GROUND-shelfHeight);
    }

    /** Version of the game with two targets.
     *  Hint: drawGameTwoTargets has been written for you. 
     */
    public void runGameTwoTargets(){
        UI.printMessage("Click Mouse to launch the ball");
        /*# YOUR CODE HERE */
        
        Color color = (java.awt.Color.black);
        this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color);

        double shelfHeight = 50+Math.random()*200;
        Ball target1 = new Ball(SHELF_X, shelfHeight, color);
        
        // Created a new target with its own new Shelf Height 
        
        double shelfHeight2 = 50+Math.random()*200;
        Ball target2 = new Ball(SHELF_X, shelfHeight2, color);

        this.drawGameTwoTargets(this.ball, target1, target2, color, shelfHeight, shelfHeight2);
        int count = 0; 
        
        // run until the target is gone (ie, off the right end)
        while (ball.getX()!=LAUNCHER_X || ((GROUND - target1.getHeight()) <= GROUND) || ((GROUND - target2.getHeight()) <= GROUND)){

            //if the ball is over the right end, make a new one.
            if (ball.getX()>=RIGHT_END) {
                this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color);
                count++;
            }
            
            //move the ball, if it isn't on the launcher
            if (ball.getX() > LAUNCHER_X){
                this.ball.step();
            }            
            
            // move target if it isn't on the shelf
            if (target1.getX()!= SHELF_X){
                
                
                if ((GROUND - target1.getHeight()) <= GROUND){
                    target1.step();
                }
                
            }
            
            if (target2.getX()!= SHELF_X){
                
                if ((GROUND - target2.getHeight()) <= GROUND){
                    target2.step();
                }
                
            }

            //if ball is hitting the target ball on the shelf, then make it start moving too
            double dist = Math.hypot(target1.getX()-this.ball.getX(), target1.getHeight()-this.ball.getHeight());
            if (target1.getX()==SHELF_X && dist <= Ball.DIAM){
                target1.setXSpeed(2);
                target1.step();
            }
            
            double dist2 = Math.hypot(target2.getX()-this.ball.getX(), target2.getHeight()-this.ball.getHeight());
            if (target2.getX()==SHELF_X && dist2 <= Ball.DIAM){
                target2.setXSpeed(2);
                target2.step();
            }

            //redraw the game and pause
            this.drawGameTwoTargets(this.ball, target1,target2, color, shelfHeight, shelfHeight2);

            UI.sleep(20); // pause of 40 milliseconds

        }
        UI.setFontSize(40);
        UI.drawString(count+" tries", 200, 200);

    }

    /**
     * Draw the game: ball, two targets, ground, launcher and shelves.
     */
    public void drawGameTwoTargets(Ball ball, Ball target1, Ball target2, Color col, double shelf1Ht, double shelf2Ht){        
        UI.clearGraphics();
        ball.draw(col);
        target1.draw(col);
        target2.draw(col);

        UI.setColor(Color.black);
        UI.setLineWidth(2);
        // draw ground, wall, launcher, and shelf
        UI.eraseRect(RIGHT_END, 0, RIGHT_END+100, GROUND);
        UI.drawLine(LAUNCHER_X, GROUND, RIGHT_END, GROUND);
        UI.drawLine(RIGHT_END, GROUND, RIGHT_END, 0);
        UI.drawLine(LAUNCHER_X, GROUND, LAUNCHER_X, GROUND-LAUNCHER_HEIGHT);
        UI.drawLine(LAUNCHER_X-Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT, LAUNCHER_X+Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT);
        UI.drawLine(SHELF_X-Ball.DIAM/2, GROUND-shelf1Ht, SHELF_X+Ball.DIAM/2, GROUND-shelf1Ht);
        UI.drawLine(SHELF_X-Ball.DIAM/2, GROUND-shelf2Ht, SHELF_X+Ball.DIAM/2, GROUND-shelf2Ht);
    }
    
    /**
     * Function for any object to fall. 
     */
    public int fall(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.step();                 
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
                //Set the y coordinate so that it must complete one bounce cycle
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Function for any object to undergo the first bounce
     */
    public int bounce1(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.bounce1();                    
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Function for any object to undergo the second bounce
     */
    public int bounce2(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.bounce2();                    
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Function for any object to undergo the third bounce
     */
    public int bounce3(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.bounce3();                    
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Function for any object to undergo the fourth bounce
     */
    public int bounce4(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.bounce4();                    
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Function for any object to undergo the fifth bounce
     */
    public int bounce5(int bounce_count, int num, Ball target){
        if (bounce_count == num){
            target.bounce5();                    
                    //if ((GROUND - target1.getHeight()) >= GROUND){
            if (target.getHeight() <= 0){
                bounce_count += 1;
                target.setYCoor(1);
            }
                    
        }
        return bounce_count;
    }
    
    /**
     * Very cool function that determines when the ball hits the target
     */
    public void hit(Ball target, double shelf){
        if (target.getX()==shelf && (Math.hypot(target.getX()-this.ball.getX(), target.getHeight()-this.ball.getHeight())) <= Ball.DIAM){
                target.setXSpeed(ball.getXSpeed() * 0.7);
                target.step();
                // noitce how the speed is multiplied by a negative. it makes it more realistic by going the other way after collision
                ball.setXSpeed(ball.getXSpeed() * -0.5);
            }
    }
    
    /**
     * Super super cool function that determines when a target hits another target
     */
    public void collide(Ball target_stay, Ball target_move, double shelf){
        if (target_stay.getX()==shelf && (Math.hypot(target_stay.getX()-target_move.getX(), target_stay.getHeight()-target_move.getHeight())) <= Ball.DIAM){
                target_stay.setXSpeed(target_move.getXSpeed() * 0.7);
                target_stay.step();
                // the speed is multiplied by a negative to create a more realistic collision between the targets.
                target_move.setXSpeed(target_move.getXSpeed() * -0.5);
            }
    }
    
    /**
     * This is just the function so that I can make a three star system similar to the one seen in angry birds.
     */
    public void star(double height, double width, double flag_height, double flag_width, double y, double LEFT) {
        // Setting the colour as red
        UI.setColor(java.awt.Color.orange);
        
        // Below is the code to make one star. It takes width, height, flag_width, and height, alongside the most important parameter being y (its vertical position)
        
        // A lot of numbers and calculations below but essentially it draws lines of the star's perimeter going clockwise. 
        
        // The tricky / challenge part is that in order to draw the lines, the verticies must be given an x and y coordinate, which is trickier since its a star (5 sides) diagonals compared to a quadrilateral with vertical / horizontal lines
        
        // I did many calculations on paper to derive the numbers (coordinates) below and I believe I have managed to draw a pretty accurate normal looking star :)
        
        UI.fillPolygon(new double[]{((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 0.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 8.75/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 11.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 13.25/22.0)) , ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 22.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + 
            (width * 15.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 18.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 11.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 4.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 7.0/22.0))}, new double[]{((TOP + (flag_height * y/7.0)) + 
                (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 0.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + 
                (height * 10.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 16.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 12.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 16.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 10.0/16.0))}, 10);
                
        
    }
    
    /**
     * This is the same as the star above but instead it is just the outline of the star. This will be used if the 'gamer' does not achieve all three stars
     */
    public void star_outline(double height, double width, double flag_height, double flag_width, double y, double LEFT) {
        // Setting the colour as red
        UI.setColor(java.awt.Color.orange);
        
        // Below is the code to make one star. It takes width, height, flag_width, and height, alongside the most important parameter being y (its vertical position)
        
        // A lot of numbers and calculations below but essentially it draws lines of the star's perimeter going clockwise. 
        
        // The tricky / challenge part is that in order to draw the lines, the verticies must be given an x and y coordinate, which is trickier since its a star (5 sides) diagonals compared to a quadrilateral with vertical / horizontal lines
        
        // I did many calculations on paper to derive the numbers (coordinates) below and I believe I have managed to draw a pretty accurate normal looking star :)
        
        UI.drawPolygon(new double[]{((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 0.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 8.75/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 11.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 13.25/22.0)) , ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 22.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + 
            (width * 15.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 18.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 11.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 4.0/22.0)), ((LEFT + ((flag_width / 2.0) - (width /2.0))) + (width * 7.0/22.0))}, new double[]{((TOP + (flag_height * y/7.0)) + 
                (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 0.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 7.0/16.0)), ((TOP + (flag_height * y/7.0)) + 
                (height * 10.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 16.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 12.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 16.0/16.0)), ((TOP + (flag_height * y/7.0)) + (height * 10.0/16.0))}, 10);
                
        
    }
    
    /** Version of the game with two targets.
     *  Hint: drawGameTwoTargets has been written for you. 
     */
    public void runGameChallenge(){
        UI.printMessage("Click Mouse to launch the ball");
        /*# YOUR CODE HERE */
        
        
        Color color1 = Color.getHSBColor((float)Math.random(),1,1);
        this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color1);
        
        // determines the shelf and colour of the first target, second target etc.
        double shelfHeight = 50+Math.random()*60;
        double SHELF_X1 = 275+Math.random()*200;
        Color color2 = Color.getHSBColor((float)Math.random(),1,1);
        Ball target1 = new Ball(SHELF_X1, shelfHeight, color2);
        
        double shelfHeight2 = 125+Math.random()*60;
        double SHELF_X2 = 275+Math.random()*200;
        Color color3 = Color.getHSBColor((float)Math.random(),1,1);
        Ball target2 = new Ball(SHELF_X2, shelfHeight2, color3);
        
        double shelfHeight3 = 200+Math.random()*60;
        double SHELF_X3 = 275+Math.random()*200;
        Color color4 = Color.getHSBColor((float)Math.random(),1,1);
        Ball target3 = new Ball(SHELF_X3, shelfHeight3, color4);
        
        double shelfHeight4 = 275+Math.random()*60;
        double SHELF_X4 = 275+Math.random()*200;
        Color color5 = Color.getHSBColor((float)Math.random(),1,1);
        Ball target4 = new Ball(SHELF_X4, shelfHeight4, color5);
        
        // draws the game for the first instance.

        this.drawGameChallenge(this.ball, target1, target2, target3, target4, color1, color2, color3, color4, color5, shelfHeight, shelfHeight2, shelfHeight3, shelfHeight4, SHELF_X1, SHELF_X2, SHELF_X3, SHELF_X4);
        int count = 0; 
        
        int bounce_count_1 = 0;
        int ball_bounce_count = 0;
        int bounce_count_2 = 0;
        int bounce_count_3 = 0;
        int bounce_count_4 = 0;
        
        // I used bounce counts to determine the end of the game, a notably strange method but it works
        
        // run until the target is gone (ie, off the right end) or when bounces have reached 24.
        while (ball.getX()!=LAUNCHER_X || (bounce_count_1 + bounce_count_2 + bounce_count_3 + bounce_count_4) != 24){

            //if the ball is over the right end, make a new one.
            if (ball.getX()>=RIGHT_END || ball_bounce_count == 4) {
                this.ball = new Ball(LAUNCHER_X, LAUNCHER_HEIGHT, color1);
                count++;
                ball_bounce_count = 0;
            }
            
            //move the ball, if it isn't on the launcher
            if (ball.getX() > LAUNCHER_X || ball.getX() < LAUNCHER_X || ball_bounce_count >= 1){
                
                // if bounce count is greater than or equal to one it MUST move until it completes 3 bounces.
                
                ball_bounce_count = this.fall(ball_bounce_count, 0, ball);
                ball_bounce_count = this.bounce1(ball_bounce_count, 1, ball);
                ball_bounce_count = this.bounce2(ball_bounce_count, 2, ball);
                ball_bounce_count = this.bounce3(ball_bounce_count, 3, ball);
                ball_bounce_count = this.bounce4(ball_bounce_count, 4, ball);
                ball_bounce_count = this.bounce5(ball_bounce_count, 5, ball);
                
                // basically bounce 5 times slowly reducing in height to simulate real physics.
                
            }            
            
            // move target if it isn't on the shelf
            // REPLACE THE IF STATEMENT WITH A COUNTER: counter for bounce. counter bounce 1, counter bounce 2
                // We can replace the top while loop condition with if counter bounce is equal to num targets * say 3 bounces. 
                
                //if ((GROUND - target2.getHeight()) >= GROUND){
                    //bounce_count_2 += 1;
                //}
                
            if (target1.getX()!= SHELF_X1){
                
                bounce_count_1 = this.fall(bounce_count_1, 0, target1);
                bounce_count_1 = this.bounce1(bounce_count_1, 1, target1);
                bounce_count_1 = this.bounce2(bounce_count_1, 2, target1);
                bounce_count_1 = this.bounce3(bounce_count_1, 3, target1);    
                bounce_count_1 = this.bounce4(bounce_count_1, 4, target1);
                bounce_count_1 = this.bounce5(bounce_count_1, 5, target1); 
                
            }
            
            // although I have made an attempt to reduce repetition by having parameterised functions. There is still more repetition!
            
            if (target2.getX()!= SHELF_X2){
                
                bounce_count_2 = this.fall(bounce_count_2, 0, target2);
                bounce_count_2 = this.bounce1(bounce_count_2, 1, target2);
                bounce_count_2 = this.bounce2(bounce_count_2, 2, target2);
                bounce_count_2 = this.bounce3(bounce_count_2, 3, target2);
                bounce_count_2 = this.bounce4(bounce_count_2, 4, target2);
                bounce_count_2 = this.bounce5(bounce_count_2, 5, target2);

            }
            
            // I could make another function and pass through parameters but this here shows a better view of how the code works. 
            
            if (target3.getX()!= SHELF_X3){
                
                bounce_count_3 = this.fall(bounce_count_3, 0, target3);
                bounce_count_3 = this.bounce1(bounce_count_3, 1, target3);
                bounce_count_3 = this.bounce2(bounce_count_3, 2, target3);
                bounce_count_3 = this.bounce3(bounce_count_3, 3, target3);
                bounce_count_3 = this.bounce4(bounce_count_3, 4, target3);
                bounce_count_3 = this.bounce5(bounce_count_3, 5, target3);
                
            }
            
            if (target4.getX()!= SHELF_X4){
                
                bounce_count_4 = this.fall(bounce_count_4, 0, target4);
                bounce_count_4 = this.bounce1(bounce_count_4, 1, target4);
                bounce_count_4 = this.bounce2(bounce_count_4, 2, target4);
                bounce_count_4 = this.bounce3(bounce_count_4, 3, target4);
                bounce_count_4 = this.bounce4(bounce_count_4, 4, target4);
                bounce_count_4 = this.bounce5(bounce_count_4, 5, target4);
                
            }
            
            
            //if ball is hitting the target ball on the shelf, then make it start moving too
            //double dist = (Math.hypot(target1.getX()-this.ball.getX(), target1.getHeight()-this.ball.getHeight()));
            
            // THis is when the ball hits the target
            
            this.hit(target1, SHELF_X1);
            this.hit(target2, SHELF_X2);
            this.hit(target3, SHELF_X3);
            this.hit(target4, SHELF_X4);
            
            
            // This is when targets collide with other targets
            
            this.collide(target1, target2, SHELF_X1);
            this.collide(target1, target3, SHELF_X1);
            this.collide(target1, target4, SHELF_X1);
            
            this.collide(target2, target1, SHELF_X2);
            this.collide(target2, target3, SHELF_X2);
            this.collide(target2, target4, SHELF_X2);
            
            this.collide(target3, target1, SHELF_X3);
            this.collide(target3, target2, SHELF_X3);
            this.collide(target3, target4, SHELF_X3);
            
            this.collide(target4, target1, SHELF_X4);
            this.collide(target4, target2, SHELF_X4);
            this.collide(target4, target3, SHELF_X4);
            
            
            //if (target1.getX()==SHELF_X1 && (Math.hypot(target1.getX()-target2.getX(), target1.getHeight()-target2.getHeight())) <= Ball.DIAM){
                //target1.setXSpeed(target2.getXSpeed() * 0.7);
                //target1.step();
                //target2.setXSpeed(-4);
            //}
            
            //double dist2 = (Math.hypot(target2.getX()-this.ball.getX(), target2.getHeight()-this.ball.getHeight()));
            //if (target2.getX()==SHELF_X2 && (Math.hypot(target2.getX()-this.ball.getX(), target2.getHeight()-this.ball.getHeight())) <= Ball.DIAM){
                //target2.setXSpeed(ball.getXSpeed() * 0.7);
                //target2.step();
                //ball.setXSpeed(-4);
            //}
            
            //double dist3 = (Math.hypot(target3.getX()-this.ball.getX(), target3.getHeight()-this.ball.getHeight()));
            //if (target3.getX()==SHELF_X3 && (Math.hypot(target3.getX()-this.ball.getX(), target3.getHeight()-this.ball.getHeight())) <= Ball.DIAM){
                //target3.setXSpeed(ball.getXSpeed() * 0.7);
                //target3.step();
                //ball.setXSpeed(-4);
            //}
            
            //double dist4 = (Math.hypot(target4.getX()-this.ball.getX(), target4.getHeight()-this.ball.getHeight()));
            //if (target4.getX()==SHELF_X4 && (Math.hypot(target4.getX()-this.ball.getX(), target4.getHeight()-this.ball.getHeight())) <= Ball.DIAM){
                //target4.setXSpeed(ball.getXSpeed() * 0.7);
                //target4.step();
                //ball.setXSpeed(-4);
            //}



            //redraw the game and pause
            this.drawGameChallenge(this.ball, target1, target2, target3, target4, color1, color2, color3, color4, color5, shelfHeight, shelfHeight2, shelfHeight3, shelfHeight4, SHELF_X1, SHELF_X2, SHELF_X3, SHELF_X4);

            UI.sleep(20); // pause of 40 milliseconds

        }
        UI.setFontSize(14);
        UI.setColor(java.awt.Color.gray);
        UI.drawString("STAGE COMPLETED", 20, 230);
        UI.setFontSize(24);
        UI.drawString("Launches: " + count, 20, 260);
        
        UI.setColor(java.awt.Color.lightGray);
        
        UI.drawLine(0, 150, 190, 150);
        UI.drawLine(0, 290, 190, 290);
        UI.drawLine(190, 150, 190, 290);
        
        // the above just draws a little box to show the end game statistics
        
        // the code above is a condition that checks the performace of the 'gamer'
        
        // it bases it off of angry birds where it consists of 3 stars.
        
        if (count <= 4) {
            star(40, 40, 20, 20, 40, 30);
            star(40, 40, 20, 20, 40, 80);
            star(40, 40, 20, 20, 40, 130);
        }
        if (count > 4 && count <= 6) {
            star(40, 40, 20, 20, 40, 30);
            star(40, 40, 20, 20, 40, 80);
            star_outline(40, 40, 20, 20, 40, 130);
        }
        if (count > 6 && count <= 10) {
            star(40, 40, 20, 20, 40, 30);
            star_outline(40, 40, 20, 20, 40, 80);
            star_outline(40, 40, 20, 20, 40, 130);
        }
        if (count > 10) {
            star_outline(40, 40, 20, 20, 40, 30);
            star_outline(40, 40, 20, 20, 40, 80);
            star_outline(40, 40, 20, 20, 40, 130);
        }
        
        
        // Now I can grade the graders based on their epic gaming skills.  ;)
        

    }

    /**
     * Draw the game: ball, two targets, ground, launcher and shelves.
     */
    public void drawGameChallenge(Ball ball, Ball target1, Ball target2, Ball target3, Ball target4, Color col1, Color col2, Color col3, Color col4, Color col5, double shelf1Ht, double shelf2Ht, double shelf3Ht, double shelf4Ht, double SHELF_X1, double SHELF_X2, double SHELF_X3, double SHELF_X4){        
        UI.clearGraphics();
        
        ball.draw(col1);
        target1.draw(col2);
        target2.draw(col3);
        target3.draw(col4);
        target4.draw(col5);
        
        
        Color black = (java.awt.Color.black);
        UI.setColor(black);
        UI.setLineWidth(2);
        // draw ground, wall, launcher, and shelf
        UI.eraseRect(RIGHT_END, 0, RIGHT_END+100, GROUND);
        UI.drawLine(LAUNCHER_X, GROUND, RIGHT_END, GROUND); // Ground
        UI.drawLine(RIGHT_END, GROUND, RIGHT_END, 0);
        UI.drawLine(LAUNCHER_X, GROUND, LAUNCHER_X, GROUND-LAUNCHER_HEIGHT);
        UI.drawLine(LAUNCHER_X-Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT, LAUNCHER_X+Ball.DIAM/2, GROUND-LAUNCHER_HEIGHT);
        
        UI.drawLine(SHELF_X1-Ball.DIAM/2, GROUND-shelf1Ht, SHELF_X1+Ball.DIAM/2, GROUND-shelf1Ht);
        UI.drawLine(SHELF_X2-Ball.DIAM/2, GROUND-shelf2Ht, SHELF_X2+Ball.DIAM/2, GROUND-shelf2Ht);
        UI.drawLine(SHELF_X3-Ball.DIAM/2, GROUND-shelf3Ht, SHELF_X3+Ball.DIAM/2, GROUND-shelf3Ht);
        UI.drawLine(SHELF_X4-Ball.DIAM/2, GROUND-shelf4Ht, SHELF_X4+Ball.DIAM/2, GROUND-shelf4Ht);
        
        // The printed string below is just like a mini diary to write down the order of the progress. 
        
        Color gray = (java.awt.Color.gray);
        UI.setColor(gray);
        UI.setFontSize(9);
        UI.drawString("Patch Notes:", 5, 15);
        UI.drawString("V1.1: created bounce mechanics", 5, 30);
        UI.drawString("V1.2: added 2 more targets", 5, 45);
        UI.drawString("V1.3: improved collision physics (bounce back)", 5, 60);
        UI.drawString("V1.4: targets can knock off other targets", 5, 75);
        UI.drawString("V1.5: evolved from 1950's black and white", 5, 90);
        UI.drawString("V1.6: slghtly improved system UI", 5, 105);
        
        // it is also hopefully very useful for the marker so they know what bits of the Challenge I have implemented into this game. 
    }

    // Main
    /** Create a new BallGame object and setup the interface */
    public static void main(String[] arguments){
        BallGame bg = new BallGame();
        bg.setupGUI();
    }

}
