import java.util.ArrayList;
// Created by: Keyauna
// represents the entire game map
// holds all rooms in the game and helps manage player movement between rooms
public class Map {

    // this variable stores a list of all rooms in the game
    private ArrayList<Room> rooms;

    // constructor for creating the game map
    public Map() {
        rooms = new ArrayList<Room>();
    }

    // adds a new room to the map
    // room = the room object to be added
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // gets all rooms from the map
    // return = arraylist of all rooms
    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

    // finds a room by its id number
    // id = the room id to search for
    // return = the room object if found, otherwise null
    public Room getRoom(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    // checks if a room with a specific id exists in the map
    // id = the room id to check
    // return = true if room exists, false otherwise
    public boolean roomExists(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // checks if player can move from current room in a specific direction
    // currentRoom = the id number of the player’s current room
    // direction = the direction the player wants to move
    // return = true if exit exists, false otherwise
    public boolean canMove(int currentRoom, String direction) {
        Room room = getRoom(currentRoom);
        if (room != null) {
            ArrayList<String> exits = room.getExits();
            for (String exit : exits) {
                if (exit.equalsIgnoreCase(direction)) {
                    return true;
                }
            }
        }
        return false;
    }

    // gets the next room id when player moves in a certain direction
    // currentRoom = the id of the player’s current room
    // direction = the direction player is moving (north, south, etc.)
    // return = next room id or -1 if no valid room found
    public int getNextRoomId(int currentRoom, String direction) {
        Room room = getRoom(currentRoom);
        if (room != null) {
            // currently a simple placeholder logic
            // later this can be expanded to link actual room connections from Rooms.txt
            for (String exit : room.getExits()) {
                if (exit.equalsIgnoreCase(direction)) {
                    // move to next room in list (temporary logic)
                    int currentIndex = rooms.indexOf(room);
                    if (currentIndex + 1 < rooms.size()) {
                        return rooms.get(currentIndex + 1).getId();
                    }
                }
            }
        }
        return -1; // no valid exit
    }
}