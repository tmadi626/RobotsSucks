
// import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//Directions of the robot
enum Direction {
    U, D, L, R;

    private static final Random PRNG = new Random();

    // Outputs a random direction
    public static Direction randomDirection() {
        Direction[] directions = values();
        return directions[PRNG.nextInt(directions.length)];
    }
};

public class Robot implements Runnable {
    // image that represents the robot's position on the board
    private BufferedImage image;
    // Robot's Name
    String name;
    // if robot alive
    boolean isAlive;
    // Robot's Direction
    Direction direction;
    // Robot's hitWall
    boolean hitWall = false;
    // current position of the robot on the board grid
    private Point pos;
    // keep track of the robot's score
    private int score;

    // controls the delay between each tick in ms
    private final int DELAY = 2000;

    public Robot(String name, int x, int y, Direction direction, boolean isAlive) {
        // load the assets
        loadImage();

        // initialize the state
        setRobotName(name);
        setCoordinates(x, y);
        setDirection(direction);
        setAlive(isAlive);
        score = 0;

    }

    public void run() {
        moveInSpiral();
    }

    public void setRobotName(String robotName) {
        this.name = robotName;
    }

    public String getRobotName() {
        return this.name;
    }

    public void setCoordinates(int x, int y) {
        this.pos = new Point(x, y);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/robot50.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
                image,
                pos.x * Simulation.CELL_SIZE,
                pos.y * Simulation.CELL_SIZE,
                observer);
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the robot.

        // prevent the robot from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
            hitWall = true;
        } else if (pos.x >= Simulation.CELL_LENGTH) {
            pos.x = Simulation.CELL_LENGTH - 1;
            hitWall = true;
        }
        // prevent the robot from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
            hitWall = true;
        } else if (pos.y >= Simulation.CELL_LENGTH) {
            pos.y = Simulation.CELL_LENGTH - 1;
            hitWall = true;
        }
    }

    public void moveInSpiral() {

        int currentStep = 1;
        int distance = 1;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (isAlive) {

            while (!hitWall) {
                // check the spiral direction
                switch (direction) {
                    case U:
                        for (int i = 0; i < distance; i++) {
                            moveUp();
                            clean();
                        }
                        setDirection(Direction.L);
                        break;
                    case D:
                        for (int i = 0; i < distance; i++) {
                            moveDown();
                            clean();
                        }
                        setDirection(Direction.R);
                        break;
                    case L:
                        for (int i = 0; i < distance; i++) {
                            moveLeft();
                            clean();
                        }
                        setDirection(Direction.D);
                        break;
                    case R:
                        for (int i = 0; i < distance; i++) {
                            moveRight();
                            clean();
                        }
                        setDirection(Direction.U);
                        break;
                }

                if (currentStep == 2) {
                    distance++;
                    currentStep = 0;
                }
                currentStep++;
            }

            while (hitWall) {
                switch (direction) {
                    case U:
                        for (int i = pos.y; 0 < i; i--) {
                            moveUp();
                            clean();
                        }
                        setDirection(Direction.L);
                        break;
                    case D:
                        for (int i = 0; i < Simulation.CELL_LENGTH; i++) {
                            moveDown();
                            clean();
                        }
                        setDirection(Direction.R);
                        break;
                    case L:
                        for (int i = pos.x; 0 < i; i--) {
                            moveLeft();
                            clean();
                        }
                        setDirection(Direction.D);
                        break;
                    case R:
                        for (int i = 0; i < Simulation.CELL_LENGTH; i++) {
                            moveRight();
                            clean();
                        }
                        setDirection(Direction.U);
                        break;
                }
            }
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getAlive() {
        return this.isAlive;
    }

    private void moveUp() {
        pos.setLocation(pos.x, pos.y - 1);
    }

    private void moveDown() {
        pos.setLocation(pos.x, pos.y + 1);
    }

    private void moveLeft() {
        pos.setLocation(pos.x - 1, pos.y);
    }

    private void moveRight() {
        pos.setLocation(pos.x + 1, pos.y);
    }

    private void clean() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}