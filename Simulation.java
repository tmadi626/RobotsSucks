import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Simulation extends JPanel implements ActionListener, KeyListener {

    // controls the delay between each tick in ms
    private final int DELAY = 500;

    public static Room room; // Creating a room for this simulation
    public static int CELL_LENGTH; // Cell length
    public final static int CELL_SIZE = 50; // A cell is 0.5m

    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;

    // objects that appear on the game board
    private RobotsMaker robotsMaker;
    ArrayList<Thread> robotsThreads;
    ArrayList<Robot> robots;
    ArrayList<Dust> dusts;

    public Simulation() {
        // Create the room
        try {
            Simulation.room = new Room("Dirty Room");
            Simulation.CELL_LENGTH = room.getRoomCellsLength();

            // set the game board size
            setPreferredSize(new Dimension(CELL_SIZE * room.roomCellsLength, CELL_SIZE * room.roomCellsLength));
            // set the game board background color
            setBackground(new Color(232, 232, 232));

            // initialize the game state
            this.dusts = room.getDustsList();
            this.robotsMaker = new RobotsMaker();
            this.robots = robotsMaker.getRobots(); // initialize the robots

            startSimulation();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            stopSimulation();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // prevent the robots from disappearing off the board
        for (Robot robot : robots) {
            robot.tick();
            // give the robot points for collecting dusts
            collectDusts(robot);
            // Check if a robot hit another robot
            if (checkRobotsCollision(robot)) {
                break;
            }
        }
        // Check if all dust is gone & room is clean
        isRoomClean();

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Simulation instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        drawBackground(g);
        for (Dust dust : dusts) {
            dust.draw(g, this);
        }
        for (Robot robot : robots) {
            robot.draw(g, this);
        }

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < room.roomCellsLength; row++) {
            for (int col = 0; col < room.roomCellsLength; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * CELL_SIZE,
                            row * CELL_SIZE,
                            CELL_SIZE,
                            CELL_SIZE);
                }
            }
        }
    }

    private void collectDusts(Robot robot) {
        // allow robot to pickup dusts
        ArrayList<Dust> collectedDusts = new ArrayList<>();
        for (Dust dust : dusts) {
            // if the robot is on the same tile as a dust, collect it
            if (robot.getPos().equals(dust.getPos())) {
                // give the player some points for picking this up
                robot.addScore(100);
                collectedDusts.add(dust);
            }
        }
        // remove collected dusts from the board
        dusts.removeAll(collectedDusts);
    }

    private boolean checkRobotsCollision(Robot r0) {
        int r0Index = robots.indexOf(r0);
        for (int i = 0; i < robots.size(); i++) {
            if (i == r0Index) {
                continue;
            }
            Robot r1 = robots.get(i);
            if ((r0.getPos().x == r1.getPos().x) && (r0.getPos().y == r1.getPos().y)) {
                stopSimulation();
                System.out.println("COLLISION AT CELL (" + (int)r0.getPos().getX() + ","
                        + (int)(CELL_LENGTH - r0.getPos().getY()-1) + ")");
                
                return true;
            }
        }
        return false;
    }

    private void isRoomClean() {
        if (dusts.isEmpty()) {
            stopSimulation();
            System.out.println("ROOM CLEAN");
        }
    }

    public void startSimulation() {
        // this timer will call the actionPerformed() method every DELAY ms
        this.timer = new Timer(DELAY, this);
        this.timer.start();

        this.robotsThreads = new ArrayList<>();
        // Start the robots
        for (Robot robot : robots) {

            Thread robotThread = new Thread(robot);
            robotsThreads.add(robotThread);
            robotThread.start();
        }
    }

    public void stopSimulation() {
        // Stop the robots
        for (Robot robot : robots) {
            robot.setAlive(false);
        }
        // kill the simulation
        timer.stop();
    }
}