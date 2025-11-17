package model;

// Created by: Andy
// This class represents any item the player can pick up or use.
// Items can heal, unlock rooms, help with puzzles, or be needed for story events.
public class Item {

    // the ID of the item (example: "I1")
    private String id;

    // the name of the item (example: "Oxygen Tank")
    private String name;

    // a short text that describes what the item is
    private String description;

    // which room this item belongs to (example: 3 means Room 3)
    private int roomId;

    // what type of item it is (example: "WEAPON", "HEAL", "OXYGEN")
    private String category;

    // how strong or useful the item is (example: damage, healing power)
    private int value;

    // makes a new item
    // this matches the format from Items.txt exactly
    public Item(String id, String name, String description, int roomId, String category, int value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomId = roomId;
        this.category = category;
        this.value = value;
    }

    // gives the id
    public String getId() {
        return id;
    }

    // gives the name
    public String getName() {
        return name;
    }

    // gives the description
    public String getDescription() {
        return description;
    }

    // gives the room number where the item should spawn
    public int getRoomId() {
        return roomId;
    }

    // gives the category
    public String getCategory() {
        return category;
    }

    // gives the value
    public int getValue() {
        return value;
    }

    // shows a short line about the item
    public String itemInfo() {
        return name + " (" + category + ") - " + description;
    }
}
