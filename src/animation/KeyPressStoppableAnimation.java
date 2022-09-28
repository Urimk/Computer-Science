
package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * A decorator for animations which adds the ability
 * to stop them by pressing a key.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class KeyPressStoppableAnimation implements Animation {

    private boolean isAlreadyPressed;
    private Animation animation;
    private KeyboardSensor sensor;
    private String stopKey;
    private boolean stop;

    /**
     * A constructor. saves the params, and init
     * isAlreadyPressed to true in order to fix a bug,
     * and stop to false, since the animation won't
     * stop if nothing stops it.
     * @param sensor
     * @param key
     * @param animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key,
                                      Animation animation) {
        this.animation = animation;
        this.sensor = sensor;
        stopKey = key;
        stop = false;
        isAlreadyPressed = true;
    }

    /**
     * Does one frame of the animation. if a key is
     * pressed and it wasn't pressed to stop an other
     * animation, stops the animation.
     * @param d the draw surface to draw the animation on.
     */
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        if (sensor.isPressed(stopKey)) {
            if (isAlreadyPressed) {
                return;
            }
            stop = true;
        } else {
            isAlreadyPressed = false;
        }
    }

    /**
     * Returns stop, which holds true if the animation
     * should stop, and false if it shouldn't.
     * @return true or false.
     */
    public boolean shouldStop() {
        return stop;
    }
 }
