package model;

import java.util.ArrayList;

// represents a single room in the game
// each room has a name, description, exits, items, monsters, and puzzles
public class Room {

    // this variable stores the unique room id number
    private int id;

    // this variable stores the room name (example: "Control Room")
    private String name;

    // this variable stores the room description text shown to the player
    private String description;

    // this variable holds all exits from this room (example: "north", "south", etc.)
    private ArrayList<String> exits;

    // this variable holds all items currently in this room
    private ArrayList<Item> items;

    // this variable holds all monsters located in this room
    private ArrayList<Monster> monsters;

    // this variable holds all puzzles that belong to this room
    private ArrayList<Puzzle> puzzles;

    // this variable keeps track of whether the player has visited this room before
    private boolean visited;

    // constructor for creating a room
    // id = unique room number, name = name of the room, description = room description
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

    // gets the room id number
    public int getId() {
        return id;
    }

    // gets the room name
    public String getName() {
        return name;
    }

    // gets the room description
    public String getDescription() {
        return description;
    }

    // adds a new exit direction to the room (example: "north")
    public void addExit(String direction) {
        exits.add(direction.toLowerCase());
    }

    // gets all exits for this room
    public ArrayList<String> getExits() {
        return exits;
    }

    // adds an item into the room
    public void addItem(Item item) {
        items.add(item);
    }

    // removes an item from the room by name
    public boolean removeItemByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    // gets all items currently in the room
    public ArrayList<Item> getItems() {
        return items;
    }

    // finds an item by its name in this room
    public Item getItemByName(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    // adds a monster to the room
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    // gets all monsters in this room
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    // returns the first monster in this room (used for combat)
    public Monster getMonster() {
        if (!monsters.isEmpty()) {
            return monsters.get(0);
        }
        return null;
    }

    // clears all monsters when defeated
    public void clearMonster() {
        monsters.clear();
    }

    // adds a puzzle to the room
    public void addPuzzle(Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    // gets all puzzles in this room
    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    // marks the room as visited
    public void markVisited() {
        visited = true;
    }

    // checks if player has already visited this room
    public boolean isVisited() {
        return visited;
    }

    // shows the room’s basic info line
    public String roomInfo() {
        String info = name + " - " + description;
        if (!exits.isEmpty()) {
            info += "\nExits: " + String.join(", ", exits);
        }
        return info;
    }
}