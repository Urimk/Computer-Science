package listener;

import ball.Ball;
import blocks.Block;

/**
 * A Listener that is notified when a hit
 * event is occurring.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface HitListener {
    /**
     * Whenever a block which the listener listens to
     * is hit, activates some hitEvent.
     * @param beingHit the block that is being hit.
     * @param hitter the ball that is hitting the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
 }
