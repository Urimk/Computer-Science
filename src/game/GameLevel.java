
package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import ball.Ball;
import ball.BallRemover;
import blocks.Block;
import blocks.BlockRemover;
import blocks.Paddle;
import collision.Collidable;
import general.Counter;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import listener.HitListener;
import listener.ScoreTrackingListener;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;
import java.util.List;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import ball.Velocity;

/**
* A level in the game of Arkanoid.
* The game has an animation runner that
* animates the sprites of the game, counters
* for the objects left in the game, a ref to
* the game environment and a keyboard sensor
* for input. the level will run all time running
* is true.
* @author Uri Knoll
* @version  %I%, %G%
* @since 17.0.2
**/
public class GameLevel implements Animation {

   public static final int GUI_WIDTH = 800;
   public static final int GUI_HEIGHT = 600;
   public static final int BORDER_SIZE = 25;
   public static final int NUM_OF_ROWS = 6;
   public static final int SCORE_WIDTH = 20;

   private AnimationRunner runner;
   private boolean running;
   private SpriteCollection sprites;
   private GameEnvironment environment;
   private Counter remainingBlocks;
   private Counter remainingBalls;
   private KeyboardSensor keyboard;
   private LevelInformation level;

   /**
    * A consturcor for the game level, creating
    * a sprites and environments collections,
    * as well as counters from 0 for the
    * blocks and balls in the game, and
    * setting the level, animation runner and
    * keybaord sensor.
    * @param level the level to run.
    * @param ar the animation runner that will animate
    * the level.
    * @param ks the keyboard sensor for the player's input.
    */
   public GameLevel(LevelInformation level, KeyboardSensor ks,
                    AnimationRunner ar) {
       sprites = new SpriteCollection();
       environment = new GameEnvironment();
       remainingBlocks = new Counter(0);
       remainingBalls = new Counter(0);
       this.level = level;
       this.keyboard = ks;
       this.runner = ar;

   }

/**
    * Sets the border to the game.
    * the top and side borders are collidable blocks so
    * the ball won't be able too go through them.
    * the bottom border is an invisiable deathzone which
    * lies at the bottom of the screen, and removes balls
    * that hit it.
    */
   private void setBorders() {
       Color bColor = Color.gray;
       Block border1 = newBlock(-1, SCORE_WIDTH, GUI_WIDTH, BORDER_SIZE,
                                bColor);
       Block border2 = newBlock(GUI_WIDTH - BORDER_SIZE,
                                BORDER_SIZE + SCORE_WIDTH,
                                    BORDER_SIZE, GUI_HEIGHT, bColor);
       Block deathZone = newBlock(BORDER_SIZE, GUI_HEIGHT - 0.1,
               GUI_WIDTH - 2 * BORDER_SIZE, BORDER_SIZE, null);
       Block border3 = newBlock(-1, BORDER_SIZE + SCORE_WIDTH, BORDER_SIZE,
                                GUI_HEIGHT, bColor);
       border1.addToGame(this);
       border2.addToGame(this);
       deathZone.addToGame(this);
       HitListener ballR = new BallRemover(this, remainingBalls);
       deathZone.addHitListener(ballR);
       border3.addToGame(this);
   }

   /**
    * Creates the blocks for the game in a
    * from the blocks list of the level.
    * Adds listeners to each block, one which
    * prints a message for each hit, one which
    * removes blocks which were hit, and one
    * which updates the score after a hit.
    * @param br a listener that removes a block
    * for every hitEvent.
    * @param sl a listener that updates the score
    * for every hitEvent.
    */
    private void setBlocks(HitListener br, HitListener sl) {
        for (Block b : level.blocks()) {
            b.addToGame(this);
            remainingBlocks.increase(1);
            b.addHitListener(br);
            b.addHitListener(sl);
        }
    }

    /**
     * Initialize a new game: create the borders,
     * Blocks, ball and Paddle. and adds them to the
     * game. also adds listeners for the blocks and
     * bottom border (death zone).
     * @param score a counter that keeps track of
     * the score in the game.
     */
    public void initialize(Counter score) {
        level.getBackground().addToGame(this);
        ScoreIndicator scoreBoard =
                       new ScoreIndicator(score, level.levelName());
        scoreBoard.addToGame(this);
        createPaddle();
        createBall(4, Color.WHITE, level.initialBallVelocities());
        setBorders();
        HitListener blockR = new BlockRemover(this, remainingBlocks);
        HitListener sl = new ScoreTrackingListener(score);
        setBlocks(blockR, sl);
    }

    /**
     * Creates a user controlled paddle and
     * adds it to the game.
     */
    private void createPaddle() {
           double width = level.paddleWidth();
           double height = BORDER_SIZE * 0.5;
           double cornerX = GUI_WIDTH / 2 - level.paddleWidth() / 2;
           double cornerY = GUI_HEIGHT - height - 15;
           Point corner = new Point(cornerX, cornerY);
           Rectangle padRec = new Rectangle(corner, width, height);
           Paddle pad = new Paddle(padRec, level.paddleSpeed(), Color.ORANGE,
                                   keyboard,
                                   BORDER_SIZE, GUI_WIDTH - BORDER_SIZE);
           pad.addToGame(this);
       }

    /**
     * creates a new block, using it's upperLeft
     * corner, width, height and color.
     * @param cornerX it's upperLeft corner's X value.
     * @param cornerY it's upperLeft corner's Y value.
     * @param width it's width.
     * @param height it's height.
     * @param color it's color.
     * @return the new Block
     */
    public Block newBlock(double cornerX, double cornerY, double width,
                          double height, Color color) {
           Point corner = new Point(cornerX, cornerY);
           return newBlock(corner, width, height, color);
       }

    /**
     * creates a new block, using it's upperLeft
     * corner, width, height and color.
     * @param point it's upperLeft corner.
     * @param width it's width.
     * @param height it's height.
     * @param color it's color.
     * @return the new Block
     */
    public Block newBlock(Point point, double width, double height,
                          Color color) {
           Rectangle rect = new Rectangle(point, width, height);
           return new Block(rect, color);
       }

    /**
     * Creates the balls to the game. has 4 options
     * for ball lcotitons, depending on the number
     * of balls in the level.
     * @param size the radius of the balls.
     * @param color the color of the balls.
     * @param v a list of the ball's velocities
     */
    public void createBall(int size, Color color, List<Velocity> v) {
           if (v.size() == 1) {
               Point center = new Point(GUI_WIDTH / 2, 550);
               Ball ball = new Ball(center, size, color, environment);
               ball.setVelocity(v.get(0));
               ball.addToGame(this);
               remainingBalls.increase(1);
           }
           if (v.size() == 10) {
               int sign = 1;
               for (int i = 0; i < v.size(); i++) {
                   int j = (i + 2) / 2;
                   Point center = new Point(GUI_WIDTH / 2 + j * sign * 40,
                                            300 + 3 * Math.pow(j, 2));
                   Ball ball = new Ball(center, size, color, environment);
                   ball.setVelocity(v.get(i));
                   ball.addToGame(this);
                   remainingBalls.increase(1);
                   sign *= -1;
                   }
               }
           if (v.size() == 2 || v.size() == 3) {
               Point center1 = new Point(GUI_WIDTH / 2 + 80,
                                         GUI_HEIGHT * ((float) 4 / 5));
               Ball ball1 = new Ball(center1, size, color, environment);
               ball1.setVelocity(v.get(0));
               ball1.addToGame(this);
               Point center2 = new Point(GUI_WIDTH / 2 - 80,
                               GUI_HEIGHT * ((float) 4 / 5));
               Ball ball2 = new Ball(center2, size, color, environment);
               ball2.setVelocity(v.get(1));
               ball2.addToGame(this);
               remainingBalls.increase(2);
               if (v.size() == 3) {
                   Point center3 = new Point(GUI_WIDTH / 2,
                                   GUI_HEIGHT * ((float) 3 / 4));
                   Ball ball3 = new Ball(center3, size, color, environment);
                   ball3.setVelocity(v.get(2));
                   ball3.addToGame(this);
                   remainingBalls.increase(1);
               }
           }


           }


    /**
     * adds a collidable to the game's collection.
     * @param c the collidable to be added.
     */
    public void addCollidable(Collidable c) {
       environment.addCollidable(c);
    }

    /**
     * adds a a sprite to the game's collection.
     * @param s the sprite to be added.
     */
   public void addSprite(Sprite s) {
       sprites.addSprite(s);
   }

   /**
    * Removes a collidable from the enviroment.
    * @param c the collidable to remove.
    */
   public void removeCollidable(Collidable c) {
       environment.removeCollidable(c);
   }

   /**
    * Removes a sprite from the sprites list.
    * @param s the sprite to remove.
    */
   public void removeSprite(Sprite s) {
       sprites.removeSprite(s);
   }


   /**
    * runs the level all time running is stil true.
    * Before the level starts, counts down from 3.
    * @param score a counter that keeps track of
    * the score in the game.
    * @return the number of remaining balls.
    */
   public int run(Counter score) {
       this.runner.run(new CountdownAnimation(2, 3, sprites));
       running = true;
       runner.run(this);
       return remainingBalls.getValue();
   }

   /**
    * Draws one frame of the game. pauses it if
    * the player presses p. if there are no more
    * balls or blocks in the game, changes the value
    * of running to false.
    * @param d the draw surface to draw on.
    */
   public void doOneFrame(DrawSurface d) {
       this.sprites.drawAllOn(d);
       this.sprites.notifyAllTimePassed();
       if (this.keyboard.isPressed("p")) {
           PauseScreen ps = new PauseScreen(keyboard);
           this.runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, ps));
       }
       running = (remainingBlocks.getValue() != 0 && remainingBalls.getValue() != 0);
   }

   /**
    * Returns true if the level should finish,
    * false if it shouldn't.
    * @return true if the level should finish,
    * false if it shouldn't.
    */
    public boolean shouldStop() {
        return !running;
}

}
