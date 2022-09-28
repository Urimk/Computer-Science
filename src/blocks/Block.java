
package blocks;

import biuoop.DrawSurface;

import collision.Collidable;
import collision.CollisionInfo;
import game.GameLevel;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import listener.HitListener;
import listener.HitNotifier;
import sprites.Sprite;

import java.awt.Color;
import java.util.List;

import ball.Ball;
import ball.Velocity;

import java.util.LinkedList;

/**
 * A Block for a game. implements Collidable
 * and Sprite, since it should be collidable
 * and shown on a gui. also implements HitNotifier
 * that notify a list of listeners the block has been
 * hit. the block has a color and a Rectangle that
 * describes it's location.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private List<HitListener> hitListeners;
    private Rectangle rect;
    private Color color;

    /**
     * A Constructor for the block. creates a new
     * empty list for the listneners.
     * @param rect a Rectangle for the block location.
     * @param color the color of the block.
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
        hitListeners = new LinkedList<HitListener>();
    }

    /**
     * A Consturctor for a transperent block.
     * @param rect a Rectangle for the block location.
     */
    public Block(Rectangle rect) {
        this(rect, null);
    }


    /**
     * Returns the rectangle of the block, which
     * holds all the information on the block's
     * location.
     * @return the rectangle of the block.
     */
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    /**
     * Returns the Block it self. needed for
     * a spesific case, when a ball hits multiple
     * collidables and needs to remove them from
     * the game, hence needs their block to be
     * removed.
     * @return the block itslef.
     */
    public Block getCollisionBlock() {
        return this;
    }

    /**
     * Adds the block to a Game, adding it
     * as a collidable and as a sprite.
     * @param game the game to be added to.
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Draws the block on a drawboard.
     * if it's color is null, doesn't draw it
     * (a transparent block)/.
     * @param d the draw surface to be drawn on.
     */
    public void drawOn(DrawSurface d) {
        if (color == null) {
            return;
        }
        int upperLeftX = (int) rect.getUpperLeft().getX();
        int upperLeftY = (int) rect.getUpperLeft().getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        d.setColor(color);
        d.fillRectangle(upperLeftX, upperLeftY, width, height);
        d.setColor(Color.BLACK);
        d.drawRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
     * Gets a collision point and a velocity,
     * and returns the new velocity the object
     * which hit the block should have.
     * Also notify the block that were hit.
     * @param hitter the ball which hit the blocks.
     * @param collisionPoint the collision Point.
     * @param currentVelocity the velocity of the
     * object hitting the block at  the collision
     * point.
     * @return the new velocity.
     */
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        Line[] borders = rect.getBorders();

        //checks which border was hit
        if (borders[Rectangle.TOP].isPointOnLine(collisionPoint)) {

            /*
             *  checks if a problematic corner was hit.
             *  the top right and left corners could be
             *  problematic since changing and object's
             *  velocity as if it hit the top border could
             *  send it back into the block.
             */
            Line left = borders[Rectangle.LEFT];
            Line right = borders[Rectangle.RIGHT];
            boolean doesHitBadCorner = ((left.isPointOnLine(collisionPoint)
                    && currentVelocity.getDx() > 0
                    && currentVelocity.getDy() < 0))
                    || ((right.isPointOnLine(collisionPoint)
                    && currentVelocity.getDx() < 0
                    && currentVelocity.getDy() < 0));
            if (doesHitBadCorner) {
                currentVelocity.invertDirection(Velocity.HORIZONTAL);

            } else {
                currentVelocity.invertDirection(Velocity.VERTICAL);

            }
        } else {
            if (borders[Rectangle.BOTTOM].isPointOnLine(collisionPoint)) {

                /*
                 *  checks if a problematic corner was hit.
                 *  the bottom right and left corners could be
                 *  problematic since changing and object's
                 *  velocity as if it hit the bottom border could
                 *  send it back into the block.
                 */
                Line left = borders[Rectangle.LEFT];
                Line right = borders[Rectangle.RIGHT];
                boolean doesHitBadCorner = ((left.isPointOnLine(collisionPoint)
                        && currentVelocity.getDx() > 0
                        && currentVelocity.getDy() > 0))
                        || ((right.isPointOnLine(collisionPoint)
                        && currentVelocity.getDx() < 0
                        && currentVelocity.getDy() > 0));
                if (doesHitBadCorner) {
                    currentVelocity.invertDirection(Velocity.HORIZONTAL);
                } else {
                    currentVelocity.invertDirection(Velocity.VERTICAL);
                }
            } else {

                // Hit the right or left border
                currentVelocity.invertDirection(Velocity.HORIZONTAL);
            }
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     * Gets a the collision info, a velocity,
     * and a game environment. returns the new
     * velocity the object which hit the block
     * should have if the collision point is on
     * other collidables too, meaning it might
     * be a corner.
     * Notifies all the blocks that were hit.
     * @param hitter hitter the ball which hit the
     * blocks.
     * @param environment the game environment
     * with a list of collidables.
     * @param ci the collision info with a
     * collision point and a collision object.
     * @param currentVelocity the velocity of the
     * object hitting the block at the collision
     * point.
     * @return the new / same velocity.
     */
    public Velocity multipleObjectsHit(Ball hitter,
                                       GameEnvironment environment,
                                       CollisionInfo ci,
                                       Velocity currentVelocity) {
        Velocity newV = currentVelocity.copyV();
        Collidable c1 = ci.collisionObject();
        Collidable c2 = environment.getClosestColOtherObj(ci.collisionPoint(),
                                                          c1, null);
        if (c2 == null) {
            // No other objects were hit.
            return currentVelocity;
        }
        Collidable c3 = environment.getClosestColOtherObj(ci.collisionPoint(),
                                                          c1, c2);
        if (c3 != null) {
            // 3 objects were hit, meaning a corner was hit.
            newV.invertDirection(Velocity.HORIZONTAL);
            newV.invertDirection(Velocity.VERTICAL);
            this.notifyHit(hitter);
            c2.getCollisionBlock().notifyHit(hitter);
            c3.getCollisionBlock().notifyHit(hitter);
            return newV;
        }
        Rectangle rect1 = c1.getCollisionRectangle();
        Rectangle rect2 = c2.getCollisionRectangle();
        int cornerNumObj1 = rect1.pointCornerNum(ci.collisionPoint());
        int cornerNumObj2 = rect2.pointCornerNum(ci.collisionPoint());
        if  (cornerNumObj1 == -1 || cornerNumObj2 == -1
             || (cornerNumObj1 + cornerNumObj2) % 2 == 0) {

            /*
             * the hit point is either with a corner
             * of an object and a border of another,
             * or with 2 opposite corners of objects
             * - meaning the hit point is a corner.
             */
            newV.invertDirection(Velocity.HORIZONTAL);
            newV.invertDirection(Velocity.VERTICAL);
            this.notifyHit(hitter);
            c2.getCollisionBlock().notifyHit(hitter);
            return newV;
        }

        /*
         * The hit point is of two objects side by
         * side, so it acts like it hit one big object.
         */
        Line[] borders1 = c1.getCollisionRectangle().getBorders();
        Line[] borders2 = c2.getCollisionRectangle().getBorders();
        if (borders1[0].isPointOnLine(ci.collisionPoint())
            && borders2[0].isPointOnLine(ci.collisionPoint())
            || (borders1[2].isPointOnLine(ci.collisionPoint())
            && borders2[2].isPointOnLine(ci.collisionPoint()))) {
            newV.invertDirection(Velocity.VERTICAL);
        } else {
            newV.invertDirection(Velocity.HORIZONTAL);
        }
        this.notifyHit(hitter);
        c2.getCollisionBlock().notifyHit(hitter);
        return newV;
    }

    /**
     * Nothing happens to the block when time
     * passes.
     */
    public void timePassed() {
        return;
    }

    /**
     * Removes the block from the game, meaning
     * from the collidables list, and from the
     * sprites list.
     * @param game the game holding the block.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Adds a listener to the list of listeners of
     * the block.
     * @param hl the listener to be added.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Removes a listener to the list of listnenres
     * of the block.
     * @param hl the listener to be removed.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notifies all of the listeners that the block
     * was hit, and they should call their hitEvent.
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {

        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new LinkedList<HitListener>(hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }

}
