package model;

import java.util.ArrayList;

// Created by: Keyauna Fuller
// Updated to store exits in the format DIRECTION:ID (example: "NORTH:2")
// Each room has a name, description, exits, items, monsters, and puzzles
public class Room {

    // every room has a number so we can find it later
    private int id;

    // this is the name of the room (example: "Control Room")
    private String name;

    // this is the text that describes what the room looks like
    private String description;

    // this list holds all exits like "NORTH:2" or "EAST:5"
    private ArrayList<String> exits;

    // this list holds all the items that are in the room
    private ArrayList<Item> items;

    // this list holds all the monsters that live in the room
    private ArrayList<Monster> monsters;

    // this list holds all the puzzles that belong to the room
    private ArrayList<Puzzle> puzzles;

    // this tells if the player has already been in the room
    private boolean visited;

    // this makes a new room with an id, name, and description
    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

        this.exits = new ArrayList<String>();
        this.items = new ArrayList<Item>();
        this.monsters = new ArrayList<Monster>();
        this.puzzles = new ArrayList<Puzzle>();

        this.visited = false;
    }

    // gives the room number
    public int getId() {
        return id;
    }

    // gives the room name
    public String getName() {
        return name;
    }

    // gives the room description
    public String getDescription() {
        return description;
    }

    // adds a new exit (must already be in format "NORTH:2")
    public void addExit(String exitText) {
        exits.add(exitText.toUpperCase());
    }

    // gives all exits for this room
    public ArrayList<String> getExits() {
        return exits;
    }

    // finds a matching direction inside an exit string
    public int getExitRoomId(String direction) {
        direction = direction.toUpperCase();

        for (String exit : exits) {
            String[] parts = exit.split(":");
            if (parts.length == 2) {
                String dir = parts[0];
                String id = parts[1];

                if (dir.equals(direction)) {
                    return Integer.parseInt(id);
                }
            }
        }
        return -1; // no matching exit
    }

    // checks if room contains an exit direction
    public boolean hasExit(String direction) {
        direction = direction.toUpperCase();
        for (String exit : exits) {
            if (exit.startsWith(direction + ":")) {
                return true;
            }
        }
        return false;
    }

    // adds an item to the room
    public void addItem(Item item) {
        items.add(item);
    }

    // removes an item from the room
    public void removeItem(Item item) {
        items.remove(item);
    }

    // gives all items currently in the room
    public ArrayList<Item> getItems() {
        return items;
    }

    // finds a single item in the room by name
    public Item getItemByName(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    // finds a single item in the room by id
    public Item getItemById(String id) {
        for (Item i : items) {
            if (i.getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return null;
    }

    // adds a monster into the room
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    // gives all monsters in the room
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    // gives the first monster in the room (used for fighting)
    public Monster getMonster() {
        if (!monsters.isEmpty()) {
            return monsters.get(0);
        }
        return null;
    }

    // adds a puzzle to the room
    public void addPuzzle(Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    // gives all puzzles in this room
    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    // gives the first unsolved puzzle in the room (used for SOLVE command)
    public Puzzle getPuzzle() {
        if (puzzles.isEmpty()) {
            return null;
        }
        for (Puzzle p : puzzles) {
            if (!p.isSolved()) {
                return p;
            }
        }
        return null;
    }

    // makes the room "visited"
    public void markVisited() {
        visited = true;
    }

    // checks if player already visited this room
    public boolean isVisited() {
        return visited;
    }

    // shows room name, description, and exits together
    public String roomInfo() {
        String info = name + " - " + description;
        if (!exits.isEmpty()) {
            info += "\nExits: " + String.join(", ", exits);
        }
        return info;
    }

    // shows all exits in one line (used in GameView)
    public String getExitList() {
        if (exits.isEmpty()) {
            return "None";
        }
        return String.join(", ", exits);
    }

    // builds grammar corrected exit descriptions:
    // “To the north leads a Dark Room.”
    // “To the east is the Utility Closet.”
    public String buildExitSentences(ArrayList<Room> allRooms) {
        StringBuilder sb = new StringBuilder();

        for (String exit : exits) {

            String[] parts = exit.split(":");
            if (parts.length != 2) {
                continue;
            }

            String direction = parts[0].toLowerCase();
            int nextRoomId = Integer.parseInt(parts[1]);

            Room target = null;
            for (Room r : allRooms) {
                if (r.getId() == nextRoomId) {
                    target = r;
                    break;
                }
            }

            if (target == null) {
                continue;
            }

            String roomName = target.getName();
            char first = Character.toLowerCase(roomName.charAt(0));

            boolean startsWithVowel =
                    first == 'a' || first == 'e' ||
                            first == 'i' || first == 'o' ||
                            first == 'u';

            if (startsWithVowel) {
                sb.append("To the ")
                        .append(direction)
                        .append(" is the ")
                        .append(roomName)
                        .append(".\n");
            } else {
                sb.append("To the ")
                        .append(direction)
                        .append(" leads a ")
                        .append(roomName)
                        .append(".\n");
            }
        }

        return sb.toString();
    }
}