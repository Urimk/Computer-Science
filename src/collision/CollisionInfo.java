
package collision;

import geometry.Point;

/**
 * Information about a collision.
 * Holds the point of the collision, and the
 * object that was collided with.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * A constructor for the collision info, gets
     * the info creates a new object to hold it.
     * @param collisionPoint the point of the collision.
     * @param collisionObject the object that was
     * collided with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * return the point where the collision occurred.
     * @return the point where the collision occurred
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * returns the collidable object involved in the
     * collision.
     * @return the collided object.
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}

