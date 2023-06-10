import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner; // Import the Scanner class to read text files

public class Room {
    String roomName;
    public int roomCellsLength; // length x width of the room

    // List of all dust objects in the room
    private ArrayList<Dust> dusts;
    // controls how many dusts appear in the room
    //
    public int NUM_DUSTS;

    // read the room.txt and set the room cells
    public Room(String name) throws Exception {
        setRoomName(name);
        try {
            readFile();
            populateDusts();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    private void setRoomCellsLength(int cells) {
        this.roomCellsLength = cells;
    }

    public int getRoomCellsLength() {
        return roomCellsLength;
    }

    private void readFile() throws Exception {
        File roomFile = new File("room.txt");

        try {
            Scanner myReader = new Scanner(roomFile);

            if (roomFile.exists()) {
                if (myReader.hasNextLine()) {
                    setRoomCellsLength(Integer.parseInt(myReader.nextLine()));
                } else {
                    myReader.close();
                    throw new Exception("INPUT ERROR");
                }

            } else {
                myReader.close();
                throw new Exception("File room.txt does not exist");
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void populateDusts() {
        NUM_DUSTS = (int) Math.floor(roomCellsLength * roomCellsLength / 2);
        Random rand = new Random();
        this.dusts = new ArrayList<>();

        // create the given number of dusts in random positions on the board.
        // note that there is not check here to prevent two dusts from occupying the
        // same
        // spot, nor to prevent dusts from spawning in the same spot as the player
        for (int i = 0; i < NUM_DUSTS; i++) {
            int dustX = rand.nextInt(roomCellsLength);
            int dustY = rand.nextInt(roomCellsLength);
            this.dusts.add(new Dust(dustX, dustY));
        }
    }

    public ArrayList<Dust> getDustsList() {
        return this.dusts;
    }
}
