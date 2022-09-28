package sprites;

import biuoop.DrawSurface;
import game.GameLevel;

/**
 * A Sprite should be able to be drawn
 * on a drawing surface, added to a game,
 * and notified that time is passed and it
 * should change.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface Sprite {

   /**
    * draw the sprite to the screen.
    * @param d the drawing surface to
    * be drawn on.
    */
   void drawOn(DrawSurface d);

   /**
    * notify the sprite that time has passed,
    * and it might need to change.
    */
   void timePassed();

   /**
    * Adds the Sprite to a game.
    * @param game the specified game.
    */
   void addToGame(GameLevel game);
}