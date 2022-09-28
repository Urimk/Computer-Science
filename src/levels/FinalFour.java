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
 * A medium level of Arkanoid. has a lot of blocks.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class FinalFour implements LevelInformation {

    private static final int BLOCK_HEIGHT = 25;

    private int width;
    private int height;
    private int borderSize;

    /**
     * A constructor.
     * @param width the width of the GUI.
     * @param height the height of the GUI.
     * @param borderSize the width of the borders.
     */
    public FinalFour(int width, int height, int borderSize) {
        this.width = width;
        this.height = height;
        this.borderSize = borderSize;
    }

    /**
     * Returns the number of balls in this level.
     * @return 3, as this level has 3 balls.
     */
    public int numberOfBalls() {
        return 3;
    }

    /**
     * Returns a list of the velocities of the balls
     * in this level.
     * @return A list of velocities, aiming for the
     * paddle if the balls are placed in a spesific
     * formation.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(225, 3));
        velocities.add(Velocity.fromAngleAndSpeed(135, 3));
        velocities.add(Velocity.fromAngleAndSpeed(180, 4));
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
        return "Final Four";
    }

    /**
     * Returns a sprite of the background of the level.
     * @return the sprite of the background, 2 raining
     * clouds.
     */
    public Sprite getBackground() {
        Background background = new Background();
        Rectangle bgColor = new Rectangle(new Point(0, 0), width, height, null,
             Color.getHSBColor((float) 202 / 360, (float) 0.73, (float) 0.76));
        background.addSprite(bgColor);

        rain(new Point(80, height), new Point(105, 410), background);
        rain(new Point(580, height), new Point(610, 530), background);

        // Left Cloud
        cloudPart(new Point(100, 380), 25, 3, background);
        cloudPart(new Point(120, 400), 25, 3, background);
        cloudPart(new Point(140, 370), 30, 2, background);
        cloudPart(new Point(185, 380), 33, 1, background);
        cloudPart(new Point(160, 400), 22, 1, background);

        // Right Cloud
        cloudPart(new Point(600, 500), 25, 3, background);
        cloudPart(new Point(625, 540), 28, 3, background);
        cloudPart(new Point(640, 510), 30, 2, background);
        cloudPart(new Point(680, 520), 33, 1, background);
        cloudPart(new Point(660, 530), 23, 1, background);
        return background;
    }

    /**
     * Creates a part (circle) of a cloud, adn adds
     * it to the background.
     * @param p the center point of the part.
     * @param r the radius of the part.
     * @param brightness the brightness of the part
     * (expected 1 to 3)
     * @param bg the background to be added to.
     */
    private void cloudPart(Point p, int r, int brightness, Background bg) {
        float partBrightness = (float) (0.6 + brightness * 0.05);
        Color color = Color.getHSBColor(1, 0, partBrightness);
        Circle cloudPart = new Circle(r, p, null, color);
        bg.addSprite(cloudPart);
    }

    /**
     * Creates the rain from the clouds.
     * @param p1 the bottom point of the most left
     * rain line.
     * @param p2 the upper point of the most left
     * rain line.
     * @param bg the background to be added to.
     */
    private void rain(Point p1, Point p2, Background bg) {
        Point startP = p1.copyPoint();
        Point endP = p2.copyPoint();
        for (int i = 0; i < 10; i++) {
            Line line = new Line(startP, endP, Color.WHITE);
            bg.addSprite(line);
            startP = new Point(startP.getX() + 10, startP.getY());
            endP = new Point(endP.getX() + 10, endP.getY());
        }
    }

    /**
     * Returns a list of the blocks in this level.
     * @return a list of blocks, in 7 rows.
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        int bWidth = (width - 2 * borderSize) / 15;
        int bHeight = BLOCK_HEIGHT;
        double cornerX = borderSize;
        double cornerY = (float) height / 8;
        for (int i = 0; i < 7; i++) {
            cornerY += bHeight;
            double rowCornerX = cornerX;
            for (int j = 0; j < 15; j++) {
                Point corner = new Point(rowCornerX, cornerY);
                Rectangle rect = new Rectangle(corner, bWidth, bHeight);
                Block block = new Block(rect, getColors()[i]);
                blocks.add(block);
                rowCornerX += bWidth;
            }
        }
        return blocks;
    }

    /**
     * Returns the color pattern of the blocks.
     * @return the color pattern of the blocks.
     */
    private Color[] getColors() {
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.GREEN,
                          Color.WHITE, Color.PINK, Color.CYAN};
        return colors;
        }

    /**
     * Returns the number of blocks to remove
     * in this level.
     * @return 15*7 as this level has 7 rows of 15
     * blocks to remove.
     */
    public int numberOfBlocksToRemove() {
        return 15 * 7;
    }
}
