
package geometry;

import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;


/**
 * Line is described by 2 points, one is a starting point
 * and one is an ending point. line also has a middle point
 * and if we ignore the starting and ending points we can
 * refer to the line's equation, meaning it has a curvature
 * marked with an a, and an intersection point with the y
 * axis marked with b. Line can be drawn as a sprite if it
 * holds a color.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Line implements Sprite {

    public static final int X_RANGE = 0;
    public static final int Y_RANGE = 1;
    public static final double TINY_CONSTANT = Math.pow(10.0, -10.0);

    private Point start;
    private Point end;
    private Point middle;
    private Double a;
    private Double b;
    private Color color;

   /**
    * A constructor for the line using a start and an end
    * point.
    * @param start the start point  of the line.
    * @param end the end point of the line.
    */
   public Line(Point start, Point end) {
       this.start = start;
       this.end = end;
       double x1 = start.getX();
       double y1 = start.getY();
       double x2 = end.getX();
       double y2 = end.getY();
       this.middle = new Point((x1 + x2) / 2, (y1 + y2) / 2);
       setA(x1, y1, x2, y2);
       setB(x1, y1);

   }

   /**
    * A constructor for the Line using the location of the
    * start point and the location of the end point.
    * @param x1 the x value of the start point.
    * @param y1 the y value of the start point.
    * @param x2 the x value of the end point.
    * @param y2 the y value of the end point.
    */
   public Line(double x1, double y1, double x2, double y2) {
       this(new Point(x1, y1), new Point(x2, y2));
   }

   /**
    * A constructor for the line using a start and an end
    * point, and a color.
    * @param start the start point  of the line.
    * @param end the end point of the line.
    * @param color the color of the line.
    */
   public Line(Point start, Point end, Color color) {
       this(start, end);
       this.color = color;
   }

   /**
    * A setter for the a value of the line's equation.
    * @param x1 the x value of the start point.
    * @param y1 the y value of the start point.
    * @param x2 the x value of the end point.
    * @param y2 the y value of the end point.
    */
   private void setA(double x1, double y1, double x2, double y2) {
       if (x1 == x2) {
           this.a = null;
       } else {
           this.a = ((y2 - y1) / (x2 - x1));
       }
   }

   /**
   * A setter for the b value of the line's equation.
   * @param x a x value of a point on the line.
   * @param y the y value of the same point on the line.
   */
   private void setB(double x, double y) {
       if (this.a != null) {
           this.b = y - a * x;
       } else {
           this.b = 0.0;
       }
   }

   /**
    *  A getter for the length of the line.
    * @return the length of the line
    */
   public double length() {
       return start.distance(end);
   }

   /**
    * A getter for the middle point of the line.
    * @return the middle point of the line
    */
   public Point middle() {
       return this.middle;
   }

   /**
    * A getter for the start point of the line.
    * @return the start point of the line
    */
   public Point start() {
       return start;
   }

   /**
    * A getter for the end point of the line.
    * @return the end point of the line
    */
   public Point end() {
       return end;
   }

   /**
    * Returns true if the point is on a line,
    * false if not.
    * @param point the point to be checked.
    * @return true if on the line, false if not.
    */
   public boolean isPointOnLine(Point point) {
       Line pointAsLine = new Line(point, point);
       return this.isIntersecting(pointAsLine);

   }

   /**
    * Checks if an intersection Point of two endless
    * lines is on the 2 bounded section of the lines.
    * @param other the second line.
    * @param point is the point to be checked. if the
    * line was endless the point was on it somewhere.
    * @return true if the point is on the lines, false
    * if it's not.
    */

   private boolean isIntersectionPointOnLines(Line other, Point point) {
       boolean isPointInRanges = (isPointInRange(point, start, end, Y_RANGE)
               && isPointInRange(point, other.start, other.end, Y_RANGE))
               && (isPointInRange(point, start, end, X_RANGE)
                       && isPointInRange(point, other.start, other.end,
                                         X_RANGE));
       return isPointInRanges;

   }

   /**
    * Checks if a point is between two points.
    * Either if between their X values, or their
    * Y values, depends on the flag.
    * @param point the point to be checked.
    * @param startP the first point for the range.
    * @param endP the second point for the range.
    * @param flag 0 in order for the X values to be
    * checked, otherwise, checks the Y values.
    * @return true if the point is in the range, false
    * if not.
    */
   private boolean isPointInRange(Point point, Point startP, Point endP,
                                  int flag) {
       double t;
       double start;
       double end;
       if (flag == X_RANGE) {
           t = point.getX();
           start = startP.getX();
           end = endP.getX();
       } else {
           t = point.getY();
           start = startP.getY();
           end = endP.getY();
       }
       if (start < end) {
           if (t < start - TINY_CONSTANT || t > end + TINY_CONSTANT) {
               return false;
           }
       } else {
           if (t > start  + TINY_CONSTANT || t < end - TINY_CONSTANT) {
               return false;
           }
       }
       return true;
   }

   /**
    * Checks if the line is intersecting with another line
    * using the line's equation. if the lines have the
    * same a, it must have the same b and their x range
    * must intersect. if they don't have the same a it
    * depends on the x ranges (because endless lines would
    * have to intersect somewhere).
    * @param other an other line.
    * @return true if the lines intersect, false otherwise.
    */
   public boolean isIntersecting(Line other) {
       if (Point.cmpDoubles(this.a, other.a)) {
           return Point.cmpDoubles(this.b - other.b,  0.0)
                  && (isIntersectionPointOnLines(this, other.start)
                  && isIntersectionPointOnLines(other, other.end));
       }

       // Finds the intersection point for endless lines
       Point point = findEndlessLinesIntersection(this, other);
       return isIntersectionPointOnLines(this, point)
              && isIntersectionPointOnLines(other, point);

   }

   /**
    * Finds the intersection of two lines if
    * they were endless. the method expects
    * two lines that will indeed intersect.
    * @param l1 the first line.
    * @param l2 the second line.
    * @return an intersection point.
    */
   private Point findEndlessLinesIntersection(Line l1, Line l2) {
       double x;
       double y;
       if (l1.a == null) {
           x = l1.start.getX();
           y = l2.a * x + l2.b;
       } else {
           if (l2.a == null) {
               x = l2.start.getX();
           } else {
               x = (l2.b - l1.b) / (l1.a - l2.a);
           }
           y = l1.a * x + l1.b;
       }
       return new Point(x, y);
   }

   /**
    * Find an intersection point between this line and an
    * other line and returns it.
    * @param other an other Line.
    * @return the intersection point if the lines intersect
    * in 1 point, null if they don't intersect, the start
    * point if they are equal, and the start or end point
    * of the other line if the have the same a (meaning
    * they have 1 or more intersection. returns the start
    * or end depending on which of them intersects).
    */
   public Point intersectionWith(Line other) {
       if (!this.isIntersecting(other)) {
           return null;
       } else {
           if ((this.a == null && other.a == null)
                || (this.a != null && other.a != null)
                && (this.a - other.a == 0)) {
               if (this.equals(other)) {
                   return this.start;
               } else {
                   if (this.isIntersectionPointOnLines(other, other.start)) {
                       return other.start;
                   } else {
                       return other.end;
                   }
               }
           } else {
               return findEndlessLinesIntersection(this, other);
           }
       }
   }

   /**
    * Checks if this line and another line are equal. if
    * they have the same start/end point, or one's start
    * point is the others end point and the opposite, they
    * are equal.
    * @param other an other Line.
    * @return true is the lines are equal, false otherwise
    */
   public boolean equals(Line other) {
       if (this.start.equals(other.start) && this.end.equals(other.end)) {
               return true;
           } else {
               return this.start.equals(other.end)
                      && this.end.equals(other.start);
           }
   }

   /**
    * If this line does not intersect with the rectangle, return null.
    * Otherwise, return the closest intersection point to the
    * start of the line.
    * @param rect the rectangle to be checked.
    * @return null / an intersection point.
    */
   public Point closestIntersectStartOfLine(Rectangle rect) {
       List<Point> intersections = rect.intersectionPoints(this);
       if (intersections.isEmpty()) {
           return null;
       } else {
           int i = 0;

           /*
            * if the intersection Point is so close,
            * it counts as the line's start is the same
            * as this point, and it's skipped.
            */
           double min = start.distance(intersections.get(i));
           while (min <= 0 + TINY_CONSTANT && min >= 0 - TINY_CONSTANT) {
               if (i + 1 < intersections.size()) {
                       i++;
               } else {
                   return null;
               }
           }
           min = start.distance(intersections.get(i));
           Point closest = intersections.get(i);

           // looks for the closes intersection.
           for (; i < intersections.size(); i++) {
               double distance = start.distance(intersections.get(i));
               if (min > distance && distance != 0.0) {
                   min = distance;
                   closest = intersections.get(i);
               }
           }
           return closest;
       }
   }

    /**
     * Draws the line if the color is not null.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        if (color != null) {
            d.setColor(color);
            d.drawLine((int) start.getX(), (int) start.getY(),
                       (int) end.getX(), (int) end.getY());
        }

    }

    /**
     * Does nothing when time passes.
     */
    public void timePassed() { }

    /**
     * Adds the line as a sprite to a level.
     * @param game the level to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}

