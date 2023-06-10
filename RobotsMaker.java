import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.util.ArrayList;

public class RobotsMaker {
    int numberOfRobots;
    ArrayList<String> robotsInfo;
    ArrayList<Robot> robots;

    public RobotsMaker() throws Exception {
        try {
            readFile();
            createRobots();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void readFile() throws Exception {
        File robotsFile = new File("robots.txt");

        try {
            Scanner myReader = new Scanner(robotsFile);

            if (robotsFile.exists()) {
                if (myReader.hasNextLine()) {
                    int numRobots = Integer.parseInt(myReader.nextLine());
                    setNumberOfRobots(numRobots);
                } else {
                    myReader.close();
                    throw new Exception("INPUT ERROR");
                }

                robotsInfo = new ArrayList<String>(); // initlizing Robots Info

                if (myReader.hasNextLine()) {
                    while (myReader.hasNextLine()) {
                        String line = myReader.nextLine();
                        robotsInfo.add(line);
                    }
                }
                myReader.close();
            } else {
                myReader.close();
                throw new Exception("File robots.txt does not exist");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void setNumberOfRobots(int numberOfRobots) {
        this.numberOfRobots = numberOfRobots;
    }

    public int getNumberOfRobots() {
        return numberOfRobots;
    }

    private void createRobots() throws Exception {
        robots = new ArrayList<Robot>(); // initlizing robots

        if (numberOfRobots > 0) {
            int z = (int) Math.floor(Simulation.CELL_LENGTH / 2);
            robots.add(new Robot("Robot0", z, z, Direction.U, true));
        }

        if (numberOfRobots > 1) {
            for (int i = 0; i < robotsInfo.size(); i++) {
                try {
                    String[] robotInfo = robotsInfo.get(i).split(" ");
                    int x = Integer.parseInt(robotInfo[0]);
                    int y = Integer.parseInt(robotInfo[1]);
                    y = Simulation.CELL_LENGTH - y - 1;
                    Direction dir = stringToDirection(robotInfo[2]);
                    robots.add(new Robot("Robot" + i, x, y, dir, true));
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    private Direction stringToDirection(String dir) {
        Direction direction;
        switch (dir) {
            case "U":
                direction = Direction.U;
                break;
            case "R":
                direction = Direction.R;
                break;
            case "D":
                direction = Direction.D;
                break;
            case "L":
                direction = Direction.L;
                break;
            default:
                direction = Direction.U;
                break;
        }
        return direction;
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

}
