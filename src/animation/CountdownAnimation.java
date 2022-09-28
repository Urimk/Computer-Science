
package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;

/**
 * The CountdownAnimation displays the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */

public class CountdownAnimation implements Animation {

    private int countFrom;
    private SpriteCollection gameScreen;
    private double timePerNum;
    private boolean isFirstCall;

    /**
     * A Constructor. saves the information it gets,
     * and sets the firstCall to true, in order for the
     * game to not pause a bit before the countdown.
     * @param numOfSeconds the number of seconds the countdown
     * will be active.
     * @param countFrom the number to count down from.
     * @param gameScreen the sprites of the game to be drawn
     * before the countdown.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        timePerNum = numOfSeconds / countFrom;
        isFirstCall = true;

    }

    /**
     * Draws a number of the countdown on the draw surface.
     * @param d the drawing surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
            long startTime = System.currentTimeMillis();
            gameScreen.drawAllOn(d);
            String str = Integer.toString(countFrom);
            d.setColor(Color.RED);
            d.drawText(d.getWidth() / 2 - 12, d.getHeight() / 2, str, 54);
            if (isFirstCall) {
                isFirstCall = false;
            } else {
                Sleeper sleeper = new Sleeper();
                long usedTime = System.currentTimeMillis() - startTime;
                long milliSecondLeftToSleep = (long) (timePerNum * 1000)
                                              - usedTime;
                if (milliSecondLeftToSleep > 0) {
                    sleeper.sleepFor(milliSecondLeftToSleep);
                }
            }
            countFrom--;
    }

    /**
     * Tells the countdown animation it should stop if it
     * already reached 0.
     * @return true if the countdown came down to 0, false
     * otherwise.
     */
    public boolean shouldStop() {
        return (countFrom == -1);
    }
}

