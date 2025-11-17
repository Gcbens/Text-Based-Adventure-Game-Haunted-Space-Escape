package model;

import java.util.ArrayList;

// Created by: Keyauna
// This class loads all the rooms from the text files and connects them together.
// It helps the game know which rooms link to each other.
public class Map {

    // this list holds every room in the whole game
    private ArrayList<Room> rooms;

    // this makes the map ready to store rooms
    public Map() {
        rooms = new ArrayList<Room>();
    }

    // this adds a room to the map
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // this gives all rooms in the map
    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

    // this finds a room by its number
    public Room getRoom(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null; // not found
    }

    // this checks if the player can move in a direction
    public boolean canMove(int currentRoomId, String direction) {
        Room room = getRoom(currentRoomId);

        // if room is not null, ask the room if the exit exists
        if (room != null) {
            return room.hasExit(direction);
        }

        return false;
    }

    // this gets the room number the player will move into
    public int getNextRoomId(int currentRoomId, String direction) {
        Room room = getRoom(currentRoomId);

        if (room != null) {
            return room.getExitRoomId(direction);
        }

        return -1; // no room found
    }
}
