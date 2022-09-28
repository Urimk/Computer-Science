package levels;

import java.util.List;

import ball.Velocity;
import blocks.Block;
import sprites.Sprite;

/**
 * An interface for almost all the information
 * of a level. (doesn't have the ball's starting
 * locations).
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface LevelInformation {
    /**
     * Returns the number of balls in the level.
     * @return the number of balls in the level.
     */
    int numberOfBalls();

    /**
     * Returns a list with the ball's velocities.
     * @return a list with the ball's velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the paddle's speed.
     * @return the paddle's speed.
     */
    int paddleSpeed();

    /**
     * Returns the paddle's width.
     * @return the paddle's width.
     */
    int paddleWidth();

    /**
     * Returns the level's name.
     * @return the level's name.
     */
    String levelName();

    /**
     * Returns a Sprite of the background of the level.
     * @return a Sprite of the background of the level.
     */
    Sprite getBackground();

    /**
     * Returns a list of blocks in the level.
     * @return a list of blocks in the level.
     */
    List<Block> blocks();

    /**
     * Returns the number of blocks that should
     * be removed in the level.
     * @return the number of blocks that should
     * be removed in the level.
     */
    int numberOfBlocksToRemove();
 }
