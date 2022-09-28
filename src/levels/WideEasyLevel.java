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
 * An easy level of Arkanoid. with some blocks
 * a lot of balls and a giant paddle.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class WideEasyLevel implements LevelInformation {

    private static final int BLOCK_HEIGHT = 25;

    private int width;
    private int height;
    private int borderSize;

    /**
     * A constructor.
     * @param width the width of the GUI.
     * @param height the height of the GUI.
     * @param bs the width of the borders.
     */
    public WideEasyLevel(int width, int height, int bs) {
        this.width = width;
        this.height = height;
        borderSize = bs;
    }

    /**
     * Returns the number of balls in this level.
     * @return 10, as this level has 10 balls.
     */
    public int numberOfBalls() {
        return 10;
    }

    /**
     * Returns a list of the velocities of the balls
     * in this level.
     * @return A list of velocities, in a spesific
     * formation the balls will go away from a point.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        for (int i = 0; i < numberOfBalls() / 2; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(i * 9, 4));
            velocities.add(Velocity.fromAngleAndSpeed(-i * 9, 4));
        }
        return velocities;
    }

    /**
     * Returns the speed of the paddle.
     * @return 4 for a slow paddle.
     */
    public int paddleSpeed() {
        return 4;
    }

    /**
     * Returns the width of the paddle.
     * @return 550 for a giant paddle.
     */
    public int paddleWidth() {
        return 550;
    }

    /**
     * Returns the name of the level.
     * @return the name of the level.
     */
    public String levelName() {
        return "Wide Easy";
    }

    /**
     * Returns a sprite of the background of the level.
     * @return the sprite of the background, with a
     * sun in it.
     */
    public Sprite getBackground() {
        Background background = new Background();
        Rectangle bgColor = new Rectangle(new Point(0, 0), width, height, null,
                                          Color.WHITE);
        background.addSprite(bgColor);
        Point p = new Point(150, 150);
        Color[] colors = {Color.getHSBColor((float) 56 / 360, (float) 0.3,
                          (float) 0.95),
                          Color.getHSBColor((float) 56 / 360, (float) 1,
                          (float) 0.90),
                          Color.getHSBColor((float) 56 / 360, (float) 1,
                          (float) 0.95)};

        // Sun light rays.
        double x = 0;
        int y = 250;
        for (int i = 0; i < 106; i++, x += 6.5) {
            Line line = new Line(p, new Point(x, y), colors[0]);
            background.addSprite(line);
        }

        // Sun circles.
        int r = 60;
        for (int i = 0; i < 3; i++, r -= 10) {
            Circle c = new Circle(r, p, null, colors[i]);
            background.addSprite(c);
        }

        return background;
    }

    /**
     * Returns a list of the blocks in this level.
     * @return a list of blocks placed in a row.
     */
    public List<Block> blocks() {
        double blockWidth = (width - 2 * borderSize) / 15;
        Color[] colors = getColors();
        List<Block> blocks = new ArrayList<Block>();
        int i = 0;
        double secondHalf = 0;
        for (i = 0; i < 14; i++) {
            if (i == 7) {
                secondHalf = blockWidth;
            }
            Point corner = new Point(borderSize + i * blockWidth + secondHalf,
                                     250);
            Rectangle rect = new Rectangle(corner, blockWidth, BLOCK_HEIGHT);
            Block block = new Block(rect, colors[i / 2]);
            blocks.add(block);
        }
        Point corner = new Point(width / 2 - blockWidth / 2, 250);
        Rectangle rect = new Rectangle(corner, blockWidth, BLOCK_HEIGHT);
        Block block = new Block(rect, colors[3]);
        blocks.add(block);
        return blocks;
    }

    /**
     * Returns the number of blocks to remove in
     * this level.
     * @return 17 as this level has 17 blocks to
     * remove.
     */
    public int numberOfBlocksToRemove() {
        return 17;
    }

    /**
     * Returns the color pattern of the blocks.
     * @return the color pattern of the blocks.
     */
    private Color[] getColors() {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                          Color.BLUE, Color.PINK, Color.CYAN};
        return colors;
        }
}
