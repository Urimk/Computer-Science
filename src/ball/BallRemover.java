
package ball;

import blocks.Block;
import game.GameLevel;
import general.Counter;
import listener.HitListener;

/**
 * A BallRemover is a HitListener that removed
 * balls when it is notified of a hitEvent.
 * keeps track of the remaining balls in the game.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class BallRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBalls;

    /**
     * A constructor, gets the game which balls
     * should be removed from, and the counter for
     * the remaining balls in the game.
     * @param game the game with the balls.
     * @param remainingBalls the number of balls the game
     * has.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Removes a ball from the game, and decreases
     * the counter of balls by 1.
     * @param beingHit the block which hitting it
     * caused the ball removal.
     * @param hitter the ball to remove.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}
