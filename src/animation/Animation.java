
package animation;

import biuoop.DrawSurface;

/**
 * An interface for animations.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface Animation {

    /**
     * Draws 1 frame of the animation on a drawing surface.
     * @param d a drawing interface to draw the frame on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns true if the animation should stop, false if
     * it shouldn't.
     * @return true or false.
     */
    boolean shouldStop();
 }
