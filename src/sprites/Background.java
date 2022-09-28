package sprites;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import game.GameLevel;

/**
 * A sprite made out of a list of Sprites, that
 * will be the background of a level.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Background implements Sprite {
    private List<Sprite> sprites;

    /**
     * A constructor, creates an empty list of Sprites.
     */
    public Background() {
        sprites = new ArrayList<Sprite>();
    }

    /**
     * A constructor.
     * @param sprites the list of sprites for the
     * background.
     */
    public Background(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    /**
     * Adds a sprite to the background.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Draws the entire background on a draw surface.
     * @param d the draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        for (Sprite sprite : sprites) {
            sprite.drawOn(d);
        }
    }

    /**
     * Does nothing when time passes.
     */
    public void timePassed() { }

    /**
     * Adds the background as a sprite to a game.
     * @param game the game to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}
