
package blocks;

import ball.Ball;
import game.GameLevel;
import general.Counter;
import listener.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from
 * the game, as well as keeping count of the blocks
 * left in the game.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * A consturctor, gets the game and the counter
     * for the blocks and initialize BlockRemover.
     * @param game the game it opporates on.
     * @param removedBlocks the counter of blocks the
     * game has.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        remainingBlocks = removedBlocks;
    }

    /**
     * When the BlockRemover is notified that a block
     * was hit, removes itself from the listner list
     * of the block, removed the block from the game,
     * and decreases the number of blocks by 1.
     * @param beingHit the block that were hit.
     * @param hitter the ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
        remainingBlocks.decrease(1);
    }
}
