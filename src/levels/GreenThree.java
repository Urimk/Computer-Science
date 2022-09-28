package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ball.Velocity;
import blocks.Block;
import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import sprites.Background;
import sprites.Sprite;

/**
 * A medium level of Arkanoid. has a medium amount
 * of blocks.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class GreenThree implements LevelInformation {
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
    public GreenThree(int width, int height, int bs) {
        this.width = width;
        this.height = height;
        borderSize = bs;
    }

    /**
     * Returns the number of balls in this level.
     * @return 2, as this level has 2 balls.
     */
    public int numberOfBalls() {
        return 2;
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
        velocities.add(Velocity.fromAngleAndSpeed(225, 4));
        velocities.add(Velocity.fromAngleAndSpeed(135, 4));
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
        return "Green 3";
    }

    /**
     * Returns a sprite of the background of the level.
     * @return the sprite of the background, with a
     * building in it.
     */
    public Sprite getBackground() {
        Background background = new Background();
        Rectangle bgColor = new Rectangle(new Point(0, 0), width, height, null,
                            Color.getHSBColor((float) 112 / 360, (float) 1,
                                              (float) 0.55));
        background.addSprite(bgColor);
        Rectangle buildingCorner = addBuilding(background);
        Rectangle antennaRect = addAntenna(background, buildingCorner);
        addLight(background, antennaRect);
        return background;
    }

    /**
     * Adds the sprite of the building and it's
     * windows.
     * @param bg the background to be added to.
     * @return the rectangle of the building.
     */
    private Rectangle addBuilding(Background bg) {
        Point p = new Point(2.5 * borderSize, height * ((float) 5 / 7));
        Rectangle building = new Rectangle(p, (float) width / 8,
                                           height * ((float) 2 / 5), null,
                                                      Color.BLACK);
        bg.addSprite(building);
        double betweenWindows = 0.08 * building.getWidth();
        double windowWidth = 0.104 * building.getWidth();
        double windowHeight = 0.12 * building.getHeight();
        double cornerX = building.getUpperLeft().getX() + betweenWindows;
        double cornerY = building.getUpperLeft().getY() + betweenWindows;
        for (int i = 0; i < 5; i++) {
            double rowCornerX = cornerX;
            for (int j = 0; j < 5; j++) {
                Point corner = new Point(rowCornerX, cornerY);
                Rectangle window = new Rectangle(corner, windowWidth,
                                                 windowHeight, null,
                                                 Color.WHITE);
                bg.addSprite(window);
                rowCornerX += windowWidth + betweenWindows;
            }
            cornerY += windowHeight + betweenWindows;
        }
        return building;
    }

    /**
     * Adds the antenna of the building to the background.
     * @param bg the background to be added to.
     * @param building the rectangle of the building.
     * @return the long part of the antenna.
     */
    private Rectangle addAntenna(Background bg, Rectangle building) {
        double bWidth =  building.getWidth();
        Point p1 = new Point(building.getUpperLeft().getX() + bWidth * 0.35,
                             building.getUpperLeft().getY() - height
                             * ((float) 1 / 10));
        Color color1 = Color.getHSBColor((float) 1, (float) 0, (float) 0.2);
        Rectangle antennaBase = new Rectangle(p1, (float) bWidth * 0.3,
                                height * ((float) 1 / 10), null, color1);
        Point p2 = new Point(p1.getX() + antennaBase.getWidth() * 0.35,
                             p1.getY() - height * ((float) 1 / 4));
        Color color2 = Color.getHSBColor((float) 18 / 360, (float) 0.1,
                                         (float) 0.25);
        Rectangle antenna = new Rectangle(p2, (float) antennaBase.getWidth()
                                          * 0.3, height * ((float) 1 / 4),
                                          null, color2);
        bg.addSprite(antenna);
        bg.addSprite(antennaBase);
        return antenna;
    }

    /**
     * Adds the light on top of the antenna to the background.
     * @param bg bg the background to be added to.
     * @param antenna the rectangle of the long part of the
     * antenna.
     */
    private void addLight(Background bg, Rectangle antenna) {
        Point p = new Point(antenna.getUpperLeft().getX()
                            + antenna.getWidth() / 2,
                            antenna.getUpperLeft().getY());
        Color[] colors = {Color.getHSBColor((float) 56 / 360, (float) 0.4,
                                            (float) 1),
                          Color.getHSBColor((float) 11 / 360, (float) 0.85,
                                            (float) 1),
                          Color.WHITE};

        int r = 10;
        for (int i = 0; i < 3; i++, r -= 4) {
            Circle c = new Circle(r, p, null, colors[i]);
            bg.addSprite(c);
        }

    }

    /**
     * Returns a list of the blocks in this level.
     * @return a list of blocks, in "stair-like"
     * formation.
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        int firstRowNumOfBlocks = numberOfBlocksToRemove() / 5 + 2;
        int bWidth = (width - 2 * borderSize) / 15;
        int bHeight = BLOCK_HEIGHT;
        double cornerX = width - borderSize
                - firstRowNumOfBlocks * bWidth;
        double cornerY = (float) height / 4;
        for (int i = 0; i < 5; i++) {
            cornerY += bHeight;
            double rowCornerX = cornerX;
            for (int j = i; j < firstRowNumOfBlocks; j++) {
                Point corner = new Point(rowCornerX, cornerY);
                Rectangle rect = new Rectangle(corner, bWidth, bHeight);
                Block block = new Block(rect, getColors()[i]);
                blocks.add(block);
                rowCornerX += bWidth;
            }
            cornerX += bWidth;
        }
        return blocks;
    }

    /**
     * Returns the number of blocks to remove in
     * this level.
     * @return 40 as this level has 40 blocks to
     * remove.
     */
    public int numberOfBlocksToRemove() {
        return 40;
    }

    /**
     * Returns the color pattern of the blocks.
     * @return the color pattern of the blocks.
     */
    private Color[] getColors() {
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE,
                          Color.WHITE};
        return colors;
    }
}
