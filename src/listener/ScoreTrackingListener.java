
package listener;

import ball.Ball;
import blocks.Block;
import general.Counter;

/**
 * A listener that changes the score counter for each
 * hitEvent.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * The constructor, sets the Listener Counter to
     * the counter it gets.
     * @param scoreCounter the counter it gets.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
       this.currentScore = scoreCounter;
    }

    /**
     * Increases the score after each hit.
     * @param beingHit the block that was hit.
     * @param hitter the ball that hit the ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
 }
