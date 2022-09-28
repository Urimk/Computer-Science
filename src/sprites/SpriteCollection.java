
package sprites;

import java.util.LinkedList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * A Collection of Sprites in a
 * Linked List. can perform actions
 * on all of the sprites at once.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class SpriteCollection {


   private List<Sprite> sprites;

   /**
    * A constructor for the list of Sprites.
    * Creates an empty linked list.
    */
   public SpriteCollection() {
       sprites = new LinkedList<Sprite>();
   }

   /**
    * Adds a Sprite to the Collection.
    * @param s the specified Sprite.
    */
   public void addSprite(Sprite s) {
       sprites.add(s);
   }

   /**
    * Removes a Sprite from the Collection.
    * @param s the specified Sprite.
    */
   public void removeSprite(Sprite s) {
       sprites.remove(s);
   }

   /**
    *  call timePassed() on all sprites.
    */
   public void notifyAllTimePassed() {
       List<Sprite> sprites = new LinkedList<Sprite>(this.sprites);
       for (int i = 0; i < sprites.size(); i++) {
           sprites.get(i).timePassed();
       }
   }

   /**
    *  call drawOn(d) on all sprites.
    * @param d the drawing surface to be
    * drawn on.
    */
   public void drawAllOn(DrawSurface d) {
       for (int i = 0; i < sprites.size(); i++) {
           sprites.get(i).drawOn(d);
       }
   }
}