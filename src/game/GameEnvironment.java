
package game;

import java.util.LinkedList;
import java.util.List;

import ball.Ball;
import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
 * A Game environment holding the list of  the
 * collidable objects in the game.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class GameEnvironment {

    public static final double TINY_CONSTANT = 2 * Math.pow(10.0, -10.0);

    private List<Collidable> collidables;

    /**
     * a constructor for the environment, creating
     * a new one by creating a new list of collidables.
     */
    public GameEnvironment() {
        collidables = new LinkedList<Collidable>();
    }


    /**
     * add the given collidable to the environment.
     * @param c the given collidable.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * removes the given collidable from the environment.
     * @param c the given collidable.
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory the trajectory of an object.
     * @return the collision information.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        LinkedList<Double> distances = new LinkedList<Double>();
        LinkedList<Integer> indexes = new LinkedList<Integer>();
        for (int i = 0; i < collidables.size(); i++) {
            Rectangle rect = collidables.get(i).getCollisionRectangle();
            Point closestPoint = trajectory.closestIntersectStartOfLine(rect);
            if (closestPoint != null) {
                distances.add(closestPoint.distance(trajectory.start()));
                indexes.add(i);
            }
        }
        if (indexes.isEmpty()) {
            return null;
        }
        Double min = distances.get(0);
        int index = indexes.get(0);

        // Finds the closest collision
        for (int i = 1; i < distances.size(); i++) {
            if (min > distances.get(i)) {
                min = distances.get(i);
                index = indexes.get(i);
            }
        }
        Rectangle rect = collidables.get(index).getCollisionRectangle();
        Point collisionPoint = trajectory.closestIntersectStartOfLine(rect);
        CollisionInfo cInfo = new CollisionInfo(collisionPoint,
                                                collidables.get(index));
        return cInfo;
    }

    /**
     * Returns the index of a collidable in the list.
     * @param c the collidable.
     * @return the index of the collidable.
     */
    public int getIndex(Collidable c) {
        return collidables.indexOf(c);
    }

    /**
     * Checks if a point is on an object. if it
     * is, returns the object. if not, returns null.
     * used to find other objects that are hit when
     * one or 2 are already hit, meaning the hitting object
     * hits multiple objects at once.
     * @param collisionPoint the point of the collision.
     * @param skipIndex1 the index of one of the objects that
     * were hit, will be skipped when looking for other objects.
     * @param skipIndex2 the index of another object that was hit,
     * might be null. will be skipped when lookinf for other
     * objects.
     * @return the object the collision is with or null.
     */
    public Collidable pointOnObject(Point collisionPoint, int skipIndex1,
                                    int skipIndex2) {
        for (int i = 0; i < collidables.size(); i++) {
            if (i == skipIndex1 || i == skipIndex2) {
                continue;
            }
            Rectangle cObject = collidables.get(i).getCollisionRectangle();
            if (cObject.isPointOnBorders(collisionPoint)) {
                return collidables.get(i);
            }
        }
        return null;
    }

    /**
     * Gets a collision point and an object
     * and looks for other objects with the same
     * point on the list. returns null if none found,
     * or the first object that was found.
     * @param collisionPoint the collision point
     * @param c the collidable that was hit.
     * @return null / an other collidable.
     */
    public Collidable getClosestColOtherObj(Point collisionPoint,
                                            Collidable c) {
        int index = collidables.indexOf(c);
        return (pointOnObject(collisionPoint, index, -1));
    }

    /**
     * Gets a collision point and an 2 objects
     * and looks for other objects with the same
     * point on the list. returns null if none found,
     * or the first object that was found.
     * @param collisionPoint the collision point
     * @param c1 one of the collidables that were hit.
     * @param c2 the other collidable that was hit.
     * @return null / an other collidable.
     */
    public Collidable getClosestColOtherObj(Point collisionPoint,
                                            Collidable c1, Collidable c2) {
        int index1 = collidables.indexOf(c1);
        int index2 = collidables.indexOf(c2);
        return (pointOnObject(collisionPoint, index1, index2));
    }

    /**
     * Since the paddle can move after the ball,
     * and the ball is not a collidable, the
     * paddle could move in the ball, and then
     * when the ball will want to move, it won't
     * behave as expected. so in this spesific case,
     * pushes the ball out of the paddle.
     * @param ball
     */
    public void moveBallOutOfPaddle(Ball ball) {
        Rectangle rect = collidables.get(0).getCollisionRectangle();
        double rectCronerX = rect.getUpperLeft().getX();
        if (ball.getY() > rect.getUpperLeft().getY()) {

            /*
             * the ball is either close to the right border,
             * or close to the left border of the paddle.
             * moves it out of the closest border, enough
             * so the ball would collide with it for sure
             * (if needs to).
             */
            if (ball.getX() > rectCronerX + TINY_CONSTANT
                && ball.getX() < rectCronerX + rect.getWidth() / 2) {
                ball.setCenter(rectCronerX - TINY_CONSTANT, ball.getY());
            }
            if (ball.getX() < rectCronerX + rect.getWidth() - TINY_CONSTANT
                    && ball.getX() > rectCronerX + rect.getWidth() / 2) {
                    ball.setCenter(rectCronerX + rect.getWidth()
                    + TINY_CONSTANT, ball.getY());
            }
        }
    }
}
