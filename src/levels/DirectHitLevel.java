package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ball.Velocity;
import blocks.Block;
import geometry.Circle;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import sprites.Background;
import sprites.Sprite;

/**
 * An easy level of Arkanoid. has only 1 block,
 * if the ball is below the block, plays itself.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class DirectHitLevel implements LevelInformation {

    private static final double LINE_LEN = 140;

    private int width;
    private int height;

    /**
     * A constructor.
     * @param width the width of the GUI.
     * @param height the height of the GUI.
     */
    public DirectHitLevel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the number of balls in this level.
     * @return 1, as this level has one ball.
     */
    public int numberOfBalls() {
        return 1;
    }

    /**
     * Returns a list of the velocities of the balls
     * in this level.
     * @return A velocity uppwards.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(0, 4));
        return velocities;
    }

    /**
     * Returns the speed of the paddle.
     * @return 7 for a fast paddle.
     */
    public int paddleSpeed() {
        return 7;
    }

    /**
     * Returns the width of the paddle.
     * @return 80 for a small paddle.
     */
    public int paddleWidth() {
        return 80;
    }

    /**
     * Returns the name of the level.
     * @return the name of the level.
     */
    public String levelName() {
        return "Direct Hit";
    }

    /**
     * Returns a sprite of the background of the level.
     * @return the sprite of the background, with a
     * blue target around where the block is.
     */
    public Sprite getBackground() {
        Background background = new Background();
        Rectangle bgColor = new Rectangle(new Point(0, 0), width, height, null,
                            Color.BLACK);
        background.addSprite(bgColor);

        // The Target Circles
        Point p = new Point(width / 2, 150);
        int r = 60;
        for (int i = 0; i < 3; i++, r += 30) {
            Circle c = new Circle(r, p, Color.BLUE, null);
            background.addSprite(c);
        }

        // The Target Lines
        Line line1 = new Line(p, new Point(p.getX() + LINE_LEN, p.getY()),
                     Color.BLUE);
        Line line2 = new Line(p, new Point(p.getX() - LINE_LEN, p.getY()),
                     Color.BLUE);
        Line line3 = new Line(p, new Point(p.getX(), p.getY() + LINE_LEN),
                     Color.BLUE);
        Line line4 = new Line(p, new Point(p.getX(), p.getY() - LINE_LEN),
                     Color.BLUE);
        background.addSprite(line1);
        background.addSprite(line2);
        background.addSprite(line3);
        background.addSprite(line4);
        return background;
    }

    /**
     * Returns a list of the blocks in this level.
     * @return a list of one block, located where
     * the target is.
     */
    public List<Block> blocks() {
        Point corner = new Point(width / 2 - 20, 130);
        Rectangle rect = new Rectangle(corner, 40, 40);
        Block block = new Block(rect, Color.RED);
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(block);
        return blocks;
    }

    /**
     * Returns the number of blocks to remove
     * in this level.
     * @return 1 as there is only 1 block to remove.
     */
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
