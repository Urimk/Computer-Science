
package geometry;

import java.awt.Color;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;

/**
 * A Circle with sprite functions.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Circle implements Sprite {

    private int r;
    private Point center;
    private Color borderC;
    private Color bodyC;

    /**
     * A constructor.
     * @param r the radius of the circle.
     * @param center the center point of the circle.
     * @param c1 the color of it's border.
     * @param c2 the color of it's body.
     */
    public Circle(int r, Point center, Color c1, Color c2) {
        this.r = r;
        this.center = center;
        borderC = c1;
        bodyC = c2;
    }

    /**
     * Draws the circle on a drawing surface.
     * doesn't draw null colors.
     * @param d the drawing surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        if (bodyC != null) {
            d.setColor(bodyC);
            d.fillCircle((int) center.getX(), (int) center.getY(), r);
        }
        if (borderC != null) {
            d.setColor(borderC);
            d.drawCircle((int) center.getX(), (int) center.getY(), r);
        }
    }

    /**
     * Does nothing when time passes.
     */
    public void timePassed() { }

    /**
     * Adds the circle as a Sprite to a game.
     * @param game the game to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}
