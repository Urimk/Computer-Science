
package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * A pause screen for a stopped game.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class PauseScreen implements Animation {

    private boolean stop;

    /**
     * A constructor. this screen has no ability
     * to stop, and need to be warped.
     * @param k used to have a keyboard sensor
     * in order to stop the pause screen.
     */
    public PauseScreen(KeyboardSensor k) {
       this.stop = false;
    }

    /**
     * Draws the pause screen.
     * @param d the draw surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
       d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * Returns false, since it has no ability
     * to stop by itself.
     * @return false.
     */
    public boolean shouldStop() {
        return this.stop;
    }
 }
