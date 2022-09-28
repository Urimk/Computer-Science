
package geometry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ball.Ball;
import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;

/**
 * A Rectangle has 4 corners represented
 * by point. 4 lines represented by borders.
 * width and height.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Rectangle implements Sprite {

    private static final double DEFAULT_SIZE = 5.0;
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public static final double TINY_CONSTANT = 2 * Math.pow(10.0, -10.0);

    private Point upperLeft;
    private Point upperRight;
    private Point bottomRight;
    private Point bottomLeft;
    private double width;
    private double height;
    private Line[] borders;
    private Color borderC;
    private Color bodyC;


    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft the upperLeft corner of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        setWidth(width);
        setHeight(height);
        setCorners(upperLeft);
        setBorders();
    }

    /**
     * Create a new rectangle with location and width/height
     * and colors.
     * @param upperLeft the upperLeft corner of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param c1 the color of the border.
     * @param c2 the color of the body.
     */
    public Rectangle(Point upperLeft, double width, double height, Color c1, Color c2) {
        this(upperLeft, width, height);
        borderC = c1;
        bodyC = c2;
    }

    /**
     * A setter for the Rectangle width.
     * if it's not a positive number,
     * sets it to a default value.
     * @param width the rec width.
     */
    private void setWidth(double width) {
        if (width > 0) {
            this.width = width;
        } else {
            this.width = DEFAULT_SIZE;
        }
    }

    /**
     * A setter for the Rectangle height.
     * if it's not a positive number,
     * sets it to a default value.
     * @param height the rec height.
     */
    private void setHeight(double height) {
        if (width > 0) {
            this.height = height;
        } else {
            this.height = DEFAULT_SIZE;
        }
    }

    /**
     * A getter for the width of the rectangle.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * A getter for the height of the rectangle.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * A getter for the upperLeft corner of the
     * Rectnagle.
     * @return the upperLeft corner.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * A getter for the borders of the Rectangle.
     * @return the borders of the Rectangle.
     */
    public Line[] getBorders() {
        return borders;
    }

    /**
     * Sets the corners of the rectangle using
     * it's upperLeft corner, width and height.
     * @param upperLeft the upperLeft Corner.
     */
    private void setCorners(Point upperLeft) {
        this.upperLeft = upperLeft;
        upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        bottomRight = new Point(upperRight.getX(), upperLeft.getY() + height);
        bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
    }

    /**
     * Sets the borders of the rectangle
     * to lines starting in one corner and
     * ending in an adjacent one.
     */
    private void setBorders() {
        borders = new Line[4];
        borders[TOP] = new Line(upperLeft, upperRight);
        borders[RIGHT] = new Line(upperRight, bottomRight);
        borders[BOTTOM] = new Line(bottomRight, bottomLeft);
        borders[LEFT] = new Line(bottomLeft, upperLeft);
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     * @param line the specified line.
     * @return the intersection points list.
     */
   public List<Point> intersectionPoints(Line line) {
       List<Point> intersections = new ArrayList<Point>();
       for (int i = 0; i < borders.length; i++) {
           if (line.intersectionWith(borders[i]) != null) {
               intersections.add(line.intersectionWith(borders[i]));
           }
       }
       return intersections;
   }

   /**
    * Checks if the point is on the borders.
    * if it is, returns true. if not - false.
    * @param p the specified point.
    * @return true if the point is on the border,
    * false if not.
    */
   public boolean isPointOnBorders(Point p) {
       if (borders[TOP].isPointOnLine(p) || borders[RIGHT].isPointOnLine(p)
            ||  borders[BOTTOM].isPointOnLine(p)
            || borders[LEFT].isPointOnLine(p)) {
           return true;
       }
       return false;
   }

   /**
    * Returns the number of the corner.
    * 0 for upper left corner.
    * 1 for upper right corner.
    * 2 for bottom left corner.
    * 3 for bottom right corner.
    * -1 if not a corner.
    * @param p the specified point.
    * @return the number of the corner.
    */
   public int pointCornerNum(Point p) {
       if (p.equals(upperLeft)) {
           return 0;
       }
       if (p.equals(upperRight)) {
           return 1;
       }
       if (p.equals(bottomLeft)) {
           return 2;
       }
       if (p.equals(bottomRight)) {
           return 3;
       }
       return -1;
   }

   /**
    * Since when checking if the ball is
    * colliding it checks for intersection
    * points, the point might be a tiny bit
    * in an object, and when it want's to move
    * away, it might trigger the same collision
    * point twice. in order for it to not happen,
    * if the ball very close to the rectangle's
    * borders, put's it away slightly.
    * @param ball the ball hitting the rect.
    */
   public void pushBallOut(Ball ball) {
       double ballX = ball.getX();
       double ballY = ball.getY();
       Point center = new Point(ballX, ballY);
       if (borders[RIGHT].isPointOnLine(center)) {
           ball.setCenter(upperRight.getX() + TINY_CONSTANT, ballY);
       } else {
           if (borders[LEFT].isPointOnLine(center)) {
               ball.setCenter(bottomLeft.getX() - TINY_CONSTANT, ballY);
           }
       }
       if (borders[TOP].isPointOnLine(center)) {
           ball.setCenter(ballX, (upperRight.getY() - TINY_CONSTANT));
       } else {
           if (borders[BOTTOM].isPointOnLine(center)) {
               ball.setCenter(ballX, bottomRight.getY() + TINY_CONSTANT);
           }
       }
   }

   /**
    * Draws the rectangle on a drawing surface.
    * doesn't draw null colors.
    * @param d the drawing surface to draw on.
    */
    public void drawOn(DrawSurface d) {
        if (bodyC != null) {
            d.setColor(bodyC);
            d.fillRectangle((int) upperLeft.getX(), (int) upperRight.getY(),
                            (int) width, (int) height);
        }
        if (borderC != null) {
            d.setColor(borderC);
            d.drawRectangle((int) upperLeft.getX(), (int) upperRight.getY(),
                            (int) width, (int) height);
        }
    }

    /**
     * does nothing when time passes.
     */
    public void timePassed() { }

    /**
     * Adds the rectangle as a sprite to a level.
     * @param game a level to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}