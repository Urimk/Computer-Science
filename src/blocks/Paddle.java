
package blocks;

import java.awt.Color;
import ball.Ball;
import ball.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.CollisionInfo;
import game.GameLevel;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;

/**
 * A user controlled paddle. it is a collidable
 * so objects can collide and bounce off it,
 * and a Sprite so it can be displayed on a
 * GUI.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Paddle implements Sprite, Collidable {


   private KeyboardSensor keyboard;
   private Rectangle rect;
   private Color color;
   private double speed;
   private double border1;
   private double border2;

   /**
    * A constructor for the paddle. getting a
    * rectangle for the starting location, a color,
    * keyboard sensor for the user inputs, and the
    * borders it can't pass.
    * @param rect a Rectangle for the starting location
    * and size.
    * @param speed the speed of the paddle.
    * @param color the paddle's color.
    * @param keyboard the keyboard for the user inputs.
    * @param b1 the left border x value.
    * @param b2 the right border x value.
    */
   public Paddle(Rectangle rect, double speed, Color color,
                 KeyboardSensor keyboard, double b1, double b2) {
       this.speed = speed;
       this.rect = rect;
       this.color = color;
       this.keyboard = keyboard;
       border1 = b1;
       border2 = b2;
   }

    /**
    * Returns the Rectangle of the paddle.
    * @return the Rectangle of the paddle
    */
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    /**
     * Since the paddle is not a block, returns
     * null.
     * @return null.
     */
    public Block getCollisionBlock() {
        return null;
    }

    /**
    *  Add this paddle to the game.
    *  @param g the game to be added to.
    */
   public void addToGame(GameLevel g) {
       g.addCollidable(this);
       g.addSprite(this);
   }

    /**
    * Draws the paddle on the drawing surface.
    * @param d the drawing surface.
    */
    public void drawOn(DrawSurface d) {
       int upperLeftX = (int) rect.getUpperLeft().getX();
       int upperLeftY = (int) rect.getUpperLeft().getY();
       int width = (int) rect.getWidth();
       int height = (int) rect.getHeight();
       d.setColor(color);
       d.fillRectangle(upperLeftX, upperLeftY, width, height);
       d.setColor(Color.BLACK);
       d.drawRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
    * Changing the location of the paddle.
    * @param newCornerX the new upperLeft corner's
    * X value for the paddle.
    * @return the new Rectangle of the paddle.
    */
   private Rectangle updateRec(double newCornerX) {
       Point corner = new Point(newCornerX, rect.getUpperLeft().getY());
       return new Rectangle(corner, rect.getWidth(), rect.getHeight());
   }

   /**
    * Moves the paddle left if the user
    * presses the left key and the paddle
    * didn't hit the border.
    */
   public void moveLeft() {
       if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)
           && rect.getUpperLeft().getX() > border1) {
           rect = updateRec(rect.getUpperLeft().getX() - speed);
       }

   }

   /**
    * Moves the paddle right if the user
    * presses the right key and the paddle
    * didn't hit the border.
    */
   public void moveRight() {
       if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
           && rect.getUpperLeft().getX() + rect.getWidth() < border2) {
           rect = updateRec(rect.getUpperLeft().getX() + speed);
       }
   }


   /**
    * Lets the paddle now time passed, meaning
    * it should change. the paddle changes it's
    * location according to the user's input.
    */
   public void timePassed() {
       moveLeft();
       moveRight();
   }



   /**
     * Notify the paddle that we collided with it at
     * collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit
     * (based on the force the object inflicted on us) if
     * it hits the middle part of the paddle.
     * if it hits the other parts, returns a fixed
     * velocity. (still the same as it had, but the
     * angle is changed to a fixed value).
     * @param hitter the ball that hits the paddle.
     * @param collisionPoint the collision Point.
     * @param currentVelocity the velocity of the
     * @return the new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        Line[] borders = rect.getBorders();
        double distance = collisionPoint.getX() - rect.getUpperLeft().getX();
        if (borders[Rectangle.TOP].isPointOnLine(collisionPoint)) {

            //Sends it in a strong angle to the left/right
            if (distance < rect.getWidth() / 5
                || distance > 4 * rect.getWidth() / 5
                || Point.cmpDoubles(distance, rect.getWidth())
                || Point.cmpDoubles(distance, 0.0)) {
                double aSpeed = currentVelocity.angledSpeed();
                currentVelocity = Velocity.fromAngleAndSpeed(300, aSpeed);
                if (distance > 4 * rect.getWidth() / 5) {
                    currentVelocity.setDx(-currentVelocity.getDx());
                }
            } else {

                //sends it in a fixed angle.
                if (distance < 2 * rect.getWidth() / 5
                    || distance > 3 * rect.getWidth() / 5) {
                    double aSpeed = currentVelocity.angledSpeed();
                    currentVelocity = Velocity.fromAngleAndSpeed(330,  aSpeed);
                    if (distance > 3 * rect.getWidth() / 5) {
                        currentVelocity.setDx(-currentVelocity.getDx());
                    }
                } else {

                    //bounces normally.
                    currentVelocity.invertDirection(Velocity.VERTICAL);
                }

           }
        } else {

              //hits the sides of the paddle.
             currentVelocity.invertDirection(Velocity.HORIZONTAL);
         }
          return currentVelocity;
   }

   /**
    * Returns the new velocity of an object
    * it it hit the paddle and another object,
    * meaning it hit the ground and the paddle.
    * if it didn't hit the ground, returns
    * the original velocity.
    * @param hitter the ball that hits the paddle.
    * @param environment the game environment
    * with a list of collidables.
    * @param ci the collision info with a
    * collision point and a collision object.
    * @param currentVelocity the velocity of the
    * object hitting the block at the collision
    * point.
    * @return the new / same velocity.
    */
    public Velocity multipleObjectsHit(Ball hitter,
                                       GameEnvironment environment,
                                       CollisionInfo ci,
                                       Velocity currentVelocity) {
        Velocity newV = currentVelocity.copyV();
        Collidable c1 = ci.collisionObject();
        Collidable c2 = environment.getClosestColOtherObj(ci.collisionPoint(),
                                                          c1);
        if (c2 == null) {
            return currentVelocity;
        }
        newV.invertDirection(Velocity.HORIZONTAL);
        newV.invertDirection(Velocity.VERTICAL);
        return newV;
    }
}
