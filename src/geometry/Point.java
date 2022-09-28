
package geometry;


/**
 * Point describes a point 2D point in space with x and y values.
 * This class can find the distance between two points, and check
 * if two points are the same.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Point {

    public static final double TINY_CONSTANT = Math.pow(10.0, -10.0);

    private double x;
    private double y;

   /**
    * A constructor for the point values.
    * @param x the horizontal location.
    * @param y the vertical location.
    */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * a setter for the x Value.
     * @param newX the new x Value.
     */
    public void setX(double newX) {
        x = newX;
    }

    /**
     * a setter for the y Value.
     * @param newY the new y Value.
     */
    public void setY(double newY) {
        y = newY;
    }

    /**
     * A getter for the x value.
     * @return the x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * A getter for the y value.
     * @return the y value of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns a new copy of the point.
     * @return a new copy of the point.
     */
    public Point copyPoint() {
        return new Point(x, y);
    }

    /**
     * Finds the distance between the point to another point.
     * @param other another point object.
     * @return the distance of this point to the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2)
               + Math.pow(this.y - other.y, 2));
    }
    /**
     * Return true is the points are equal, false otherwise.
     * @param other another point object.
     * @return true is the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return cmpDoubles(x, other.getX()) && cmpDoubles(y, other.getY());
    }

    /**
     * Compares doubles, if they are almost the same value,
     * returns true. if not, returns false.
     * @param d1 the first double.
     * @param d2 the second double.
     * @return true if they are almost the same, otherwise - false.
     */
    public static boolean cmpDoubles(double d1, double d2) {
        if (d1 <= d2 + TINY_CONSTANT && d1 >= d2 - TINY_CONSTANT) {
            return true;
        }
    return false;
    }

    /**
     * Compares Doubles, if they are almost the same value,
     * returns true. if not, returns false.
     * @param d1 the first Double.
     * @param d2 the second Double.
     * @return true if they are almost the same, otherwise - false.
     */
    public static boolean cmpDoubles(Double d1, Double d2) {
        if (d1 == null || d2 == null) {
            if (d1 == d2) {
                return true;
            }
            return false;
        }
        return cmpDoubles((double) d1, (double) d2);
    }
}