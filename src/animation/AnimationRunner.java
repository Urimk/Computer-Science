package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Runs an animation until it states it
 * should stop.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class AnimationRunner {
   private GUI gui;
   private int framesPerSecond;
   private Sleeper sleeper;

   /**
    * A constructor.
    * @param gui the gui that will show the animation.
    * @param fps the frames per second of the animation.
    */
    public AnimationRunner(GUI gui, int fps) {
    this.gui = gui;
    framesPerSecond = fps;
    sleeper = new Sleeper();
}
   /**
    * Runs the animation until it should stop.
    * @param animation the animation to run.
    */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}