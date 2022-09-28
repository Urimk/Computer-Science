
package game;

import java.util.List;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import general.Counter;
import levels.LevelInformation;

/**
 * A class that controls the flow of the game.
 * switching between levels, pausing, and finishing
 * it. this class also keeps track of the score in
 * the game.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter score;

    /**
     * A constructor. saves the params it got, and
     * also creates a couter from 0.
     * @param ar an animation runner for the game
     * animations.
     * @param ks a keyboardSensor for the player's
     * input.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        score = new Counter(0);
    }

    /**
     * Gets a list of levels and starts running them
     * in order, untill the players loses (no more balls)
     * or until he won (no more levels to play).
     * @param levels the list of levels to play.
     */
    public void runLevels(List<LevelInformation> levels) {
       for (LevelInformation levelInfo : levels) {
          GameLevel level = new GameLevel(levelInfo,
                this.keyboardSensor,
                this.animationRunner);

          level.initialize(score);
          int remainingBalls;
          remainingBalls = level.run(score);
          if (remainingBalls == 0) {
             EndScreen loseScreen = new EndScreen(keyboardSensor,
                                                  score.getValue(), false);
             KeyPressStoppableAnimation lScreen =
             new KeyPressStoppableAnimation(keyboardSensor,
                                            KeyboardSensor.SPACE_KEY,
                                            loseScreen);
             animationRunner.run(lScreen);
             return;
          }
          score.increase(100);
       }
       EndScreen winScreen = new EndScreen(keyboardSensor, score.getValue(),
                                           true);
       KeyPressStoppableAnimation wScreen =
       new KeyPressStoppableAnimation(keyboardSensor,
                                      KeyboardSensor.SPACE_KEY,
                                      winScreen);
       animationRunner.run(wScreen);
    }
 }
