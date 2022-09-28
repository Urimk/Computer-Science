
package ball;

import geometry.Point;

/**
 *  Velocity specifies the change of  in position on the
 *  x and the y axes. Can be viewed as a speed of an
 *  object in each axes, or as speed towards a specific
 *  angel.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Velocity {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private double dx;
    private double dy;


   /**
    * A constructor for velocity using the horizontal
    * and the vertical velocity.
    * axis and speed on the y axis.
    * @param dx horizontal velocity (positive is to the
    * right).
    * @param dy vertical velocity (positive is down).
    */
   public Velocity(double dx, double dy) {
       setDx(dx);
       setDy(dy);
   }

   /**
    * A setter for the horizontal velocity.
    * @param dx the horizontal velocity (positive is
    * to the right).
    */
   public void setDx(double dx) {
       this.dx = dx;
   }

   /**
   * A setter for the vertical velocity.
   * @param dy the vertical velocity (positive is
   * down).
   */
   public void setDy(double dy) {
       this.dy = dy;
   }

   /**
    * A getter to the horizontal velocity.
    * @return the horizontal velocity.
    */
   public double getDx() {
       return dx;
   }

   /**
    * A getter to the vertical velocity.
    * @return the vertical velocity.
    */
   public double getDy() {
       return dy;
   }

   /**
    * Returns a new copy of the Velocity.
    * @return the new copy.
    */
   public Velocity copyV() {
       return new Velocity(dx, dy);
   }
   /**
    * Calculates the angle of the combined velocity,
    * while 0 is up and 90 is right. returns the result.
    * @return the angle of the velocity.
    */
   public double angle() {
       if (dx > 0 && dy > 0) {
           return (Math.atan((dy / dx)) * (180 / Math.PI)) + 90;
       } else {
           if (dx < 0 && dy > 0) {
               return 270 + (Math.atan((dy / dx)) * (180 / Math.PI));
           } else {
               if (dx < 0 && dy < 0) {
                   return (Math.atan((dy / dx)) * (180 / Math.PI)) + 270;
               }
           }
       }
       return  90 + (Math.atan((dy / dx)) * (180 / Math.PI));

   }

   /**
    * Inverts the direction of the velocity.
    * if flag is 1, inverts the horizontal Velocity.
    * if flag is 2, inverts the vertical Velocity.
    * @param flag determines horizontal or vertical
    * inversion.
    */
   public void invertDirection(int flag) {
       if (flag == HORIZONTAL) {
           setDx(-dx);
       } else {
           setDy(-dy);
       }
   }

   /**
    * Calculates the velocity to the angle using
    * both vertical and horizontal velocities. returns
    * the result.
    * @return the velocity to an angle.
    */
   public double angledSpeed() {
       return Math.sqrt(dx * dx + dy * dy);
   }

   /**
    * Create a new Velocity object using speed in an angle
    * (by calculating the horizontal and vertical velocity).
    * @param angle the angle of the velocity.
    * @param speed the velocity to the angel.
    * @return the new velocity object.
    */
   public static Velocity fromAngleAndSpeed(double angle, double speed) {
       angle = angle % 360;
       angle = (angle * Math.PI) / 180;
       double dx = Math.sin(angle) * speed;
       double dy = -Math.cos(angle) * speed;
       return new Velocity(dx, dy);
   }

   /**
    *  Take a point with position (x,y) and return a new point
    *  with position (x+dx, y+dy), simulating movement over time.
    * @param p a point with x and y values.
    * @return the new point in it's new position.
    */
   public Point applyToPoint(Point p) {
       double x = p.getX();
       double y = p.getY();
       Point newPoint = new Point(x + dx, y + dy);
       return newPoint;
   }
}