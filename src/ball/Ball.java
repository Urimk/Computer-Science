
package ball;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import game.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import java.awt.Color;

/**
 * The Ball class describes a 2D ball (circle) which is
 * has a center point, a radius, a color and velocity.
 * with this properties, the ball can be drawn on a
 * draw surface and appear to be moving around.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Ball implements Sprite {

   private static final double TINY_CONSTANT = Math.pow(10.0, -10.0);

   private int r;
   private Point center;
   private Color color;
   private Velocity v;
   private GameEnvironment game;

   /**
    * A constructor for the ball, using a center
    * point, radius and color to create it, and also
    * a game environment to be added to. sets
    * the ball's velocity to 0, and if the radius
    * is not a positive number, sets it to 1.
    * @param center the center point of the ball.
    * @param r the radius of the ball.
    * @param color the color of the ball.
    * @param game the game environment the ball will
    * be added to.
    */
   public Ball(Point center, int r, Color color, GameEnvironment game) {
       if (r <= 0) {
           this.r = 1;
       } else {
           this.r = r;
       }
       this.center = center;
       this.color = color;
       setVelocity(0, 0);
       this.game = game;
   }

   /**
    * A constructor for a the ball, using locations
    * in the x and y axis, radius, color and a
    * reference to the game Environment.
    * Sets the ball's velocity to 0.
    * @param x the location in the x axis.
    * @param y the location in the y axis.
    * @param r the radius of the ball.
    * @param color the color of the ball.
    * @param game the game environment.
    */
   public Ball(int x, int y, int r, Color color, GameEnvironment game) {
       this(new Point(x, y), r, color, game);
   }

   /**
    * A  setter for the ball's velocity.
    * @param v a velocity with horizontal and
    * vertical speed.
    */
   public void setVelocity(Velocity v) {
       this.v = v;
   }

   /**
    * A setter for the ball's velocity using
    * horizontal and vertical speeds.
    * @param dx the ball's horizontal speed.
    * @param dy the ball's vertical speed.
    */
   public void setVelocity(double dx, double dy) {
       this.v = new Velocity(dx, dy);
   }

   /**
    * A setter for the ball's center point using
    * values for location's x and y.
    * @param x the horizontal location.
    * @param y the vertical location.
    */
   public void setCenter(double x, double y) {
       this.center = new Point(x, y);
   }

   /**
    * Returns the trajectory of the ball, as a line
    * while the starting point is where the ball is now
    * and the end point is where it will be after it
    * moves.
    * @return a Line for the ball trajectory.
    */
   private Line getTrajectory() {
       Point endPoint = new Point(getX() + v.getDx(),
                                  getY() + v.getDy());
       return new Line(center, endPoint);
   }

   /**
    * A getter for the x value of the center.
    * @return the x value of the center point.
    */
   public double getX() {
       return this.center.getX();
   }

   /**
    * A getter for the y value of the center.
    * @return the y value of the center point.
    */
   public double getY() {
       return this.center.getY();
   }

   /**
    * A getter for the ball's radius.
    * @return the ball's radius.
    */
   public int getSize() {
       return r;
   }

   /**
    * A getter for the ball's Color.
    * @return the ball's Color.
    */
   public Color getColor() {
       return color;
   }

   /**
    * A getter for the ball's Velocity.
    * @return the ball's Velocity.
    */
   public Velocity getVelocity() {
       return v;
   }

   /**
    * Adds the ball to a Game.
    * @param game the game environment.
    */
   public void addToGame(GameLevel game) {
       game.addSprite(this);
   }

   /**
    * Removed the ball from the Game.
    * @param game the game enviroment.
    */
   public void removeFromGame(GameLevel game) {
       game.removeSprite(this);
   }



/**
    * Draws the ball on the draw surface.
    * @param surface the draw surface to draw on.
    */
   public void drawOn(DrawSurface surface) {
       surface.setColor(this.color);
       int x = (int) this.getX();
       int y = (int) this.getY();
       int r = this.getSize();
       surface.fillCircle(x, y, r);
       surface.setColor(Color.BLACK);
       surface.drawCircle(x, y, r);
   }

   /**
    * Moves the ball in the game 1 step. the step
    * size is determined by the ball's velocity.
    * if the ball would hit a collidable when moving,
    * changes it's velocity and moves it in a way it
    * would seem like it bounced off the collidable.
    */
   private void moveOneStep() {

       // recursion base case
       if (v.angledSpeed() == 0) {
           return;
       }

       /*
        * Since the paddle could have moved into the
        * ball, move the ball out of it.
        */
       game.moveBallOutOfPaddle(this);
       Line trajectory = getTrajectory();
       CollisionInfo nextCollision = game.getClosestCollision(trajectory);

       // Checks if the ball will collide
       if (nextCollision == null) {
           center = trajectory.end();
       } else {

           /* if the ball will collide with a corner,
            * meaning with more then 1 collidable, it
            * would need to change differently than if
            * it only hit one block, so the next part
            * checks that.
            */
           Collidable cObject = nextCollision.collisionObject();
           Velocity tempV = cObject.multipleObjectsHit(this, game,
                                                       nextCollision, v);
           Point collision = nextCollision.collisionPoint();
           if (Point.cmpDoubles(v.getDx(), tempV.getDx())
               && Point.cmpDoubles(v.getDy(), tempV.getDy())) {

               // The ball didn't hit a corner
               v = cObject.hit(this, nextCollision.collisionPoint(), v);
               center = collision;

               /*
                * Since we work with doubles, the coliision
                * point might be slightly in the collidable,
                * and it may want to collide with it again
                * moving from it, so pushes it away enough
                * so it won't detect the same collidable again.
                */
               Rectangle rect = cObject.getCollisionRectangle();
               rect.pushBallOut(this);
           } else {

               //the ball hit a corner
               double angleSpeed = v.angledSpeed() - 2 * TINY_CONSTANT;
               v = Velocity.fromAngleAndSpeed(v.angle() + 180, angleSpeed);
               v.applyToPoint(center);
               v = tempV;
           }
           double distanceLeft = collision.distance(trajectory.end());
           if (distanceLeft > TINY_CONSTANT) {

               /*
                *  Moves using the rest of the ball Velocity
                *  after the collision.
                */
               Velocity saveV = v.copyV();
               this.setVelocity(Velocity.fromAngleAndSpeed(v.angle(),
                                                           distanceLeft));
               this.moveOneStep();
               this.setVelocity(saveV);
           }
       }
   }

   /**
    * Simulate change to the ball after time
    * passes, for the ball it means it should
    * move.
    */
   public void timePassed() {
       moveOneStep();
   }
}