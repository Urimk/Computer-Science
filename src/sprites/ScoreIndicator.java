
package sprites;

import java.awt.Color;

import biuoop.DrawSurface;
import game.GameLevel;
import general.Counter;

/**
 * A Score indicatore, keeping track of the
 * score and displaying it on a draw sruface.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class ScoreIndicator implements Sprite  {

    public static final int SCORE_WIDTH = 20;

    private String levelName;
    private Counter score;

    /**
     * A constructor.
     * @param score the starting score.
     * @param level the name of the level.
     */
    public ScoreIndicator(Counter score, String level) {
        this.score = score;
        levelName = level;
    }

    /**
     * Draws a rectangle with the score and level
     * name in it.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.lightGray);
        d.fillRectangle(0, 0, d.getWidth(), SCORE_WIDTH);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 4, SCORE_WIDTH / 2 + 6,
                   "Score: " + score.getValue(), 15);
        d.drawText(d.getWidth() * 3 / 5, SCORE_WIDTH / 2 + 6,
                   "Level Name: " + levelName, 15);
    }

    /**
     * Does nothing, shouldn't change when time passes.
     */
    public void timePassed() { }

    /**
     * Adds the scoreIndicator to a game.
     * @param game the game to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);

    }
}
