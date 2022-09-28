
package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * An end screen for the game, either a win or a
 * game over screen, showing the score.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class EndScreen implements Animation {

    private boolean stop;
    private int score;
    private boolean didWin;

    /**
     * A constructor.
     * @param k the keyboard for exiting the screen.
     * @param score the score of the game.
     * @param didWin true if won, false if lost.
     */
    public EndScreen(KeyboardSensor k, int score, boolean didWin) {
       this.stop = false;
       this.score = score;
       this.didWin = didWin;
    }

    /**
     * Draws the end screen, which has a message for
     * won games, and a message for lost ones. both
     * messages show the score of the game.
     * @param d the draw surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
       if (didWin) {
           d.setColor(Color.GREEN);
           d.drawText(100, d.getHeight() / 2, "You Win! Your score is "
           + score, 48);
       } else {
           d.setColor(Color.RED);
           d.drawText(100, d.getHeight() / 2, "Game Over. Your score is "
           + score, 48);
       }
    }

    /**
     * Returns false. this behavior was extracted
     * from this class. Should be decorated to stop.
     * @return the value of this.stop, which is false.
     */
    public boolean shouldStop() {
        return this.stop;
    }
 }
