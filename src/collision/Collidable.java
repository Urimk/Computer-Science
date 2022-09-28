
package collision;

import ball.Velocity;
import blocks.Block;
import ball.Ball;
import game.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;

/**
 * An interface for collidable objects in games.
 * Those objects should be able to return the
 * shape of the object that was hit, the
 * velocity of the hitting object after the hit,
 * and the velocity of the hitting object if
 * it hits other objects as well at the same hit.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface Collidable {

   /**
    * Return the "collision shape" of the object.
    * @return the collision shape.
    */
   Rectangle getCollisionRectangle();

   /**
    * Returns the "collision block" of the object,
    * if it's indeed a block. needed for cases
    * when multiple blocks are hit and need to be
    * notify listeners.
    * @return the collision block.
    */
   Block getCollisionBlock();

   /**
    * Notify the object that they were collided with at
    * collisionPoint with a given velocity.
    * The return is the new velocity expected after the hit
    * (based on the force the object inflicted on us).
    * @param hitter the ball that collided.
    * @param collisionPoint the collision Point.
    * @param currentVelocity the velocity of the
    * @return the new velocity
    */
   Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

   /**
    * Gets a the collision info, a velocity,
    * and a game environment. returns the new
    * velocity the object which hit the block
    * should have if the collision point is on
    * other collidables too, meaning it might
    * be a corner.
    * Notifies all of objects that were hit
    * about the collision.
    * @param hitter the ball that collided.
    * @param environment the game environment
    * with a list of collidables.
    * @param ci the collision info with a
    * collision point and a collision object.
    * @param currentVelocity the velocity of the
    * object hitting the block at the collision
    * point.
    * @return The new / same velocity.
    */
   Velocity multipleObjectsHit(Ball hitter, GameEnvironment environment,
           CollisionInfo ci, Velocity currentVelocity);


}