
import java.util.ArrayList;
import java.util.List;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import game.GameLevel;
import levels.DirectHitLevel;
import levels.FinalFour;
import levels.GreenThree;
import levels.LevelInformation;
import levels.WideEasyLevel;

/**
 * Creates an Arkanoid game with a given set of levels
 * or with the default value of 4 levels.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 *
 */
public class Arkanoid {

    /**
     * The Program starts here.
     * @param args an Array of string for the levels
     * of the games to play, levels can be 1 to 4,
     * ignores any other string.
     */
    public static void main(String[] args) {
            int height = 600;
            int width = 800;
            List<LevelInformation> levels = new ArrayList<LevelInformation>();
            if (args.length != 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].compareTo("1") == 0) {
                        levels.add(new DirectHitLevel(width, height));
                        continue;
                    }
                    if (args[i].compareTo("2") == 0) {
                        levels.add(new WideEasyLevel(width, height,
                                   GameLevel.BORDER_SIZE));
                        continue;
                    }
                    if (args[i].compareTo("3") == 0) {
                        levels.add(new GreenThree(width, height,
                                   GameLevel.BORDER_SIZE));
                        continue;
                    }
                    if (args[i].compareTo("4") == 0) {
                        levels.add(new FinalFour(width, height,
                                   GameLevel.BORDER_SIZE));
                        continue;
                    }
            }
            if (levels.isEmpty()) {
                levels.add(new DirectHitLevel(width, height));
                levels.add(new WideEasyLevel(width, height,
                           GameLevel.BORDER_SIZE));
                levels.add(new GreenThree(width, height,
                           GameLevel.BORDER_SIZE));
                levels.add(new FinalFour(width, height,
                           GameLevel.BORDER_SIZE));
            }
            GUI gui = new GUI("Arkanoid", width, height);
            KeyboardSensor keyboard =  gui.getKeyboardSensor();
            int fps = 60;
            AnimationRunner runner = new AnimationRunner(gui, fps);
            GameFlow game = new GameFlow(runner, keyboard);
            game.runLevels(levels);
            gui.close();
        }
    }
}
