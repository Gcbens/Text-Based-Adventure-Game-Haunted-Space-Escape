package model;

import java.util.ArrayList;
import java.util.Random;
import model.Map;
import model.Monster;

// Created by: Eyob
// represents the player in the game
// tracks the player's location, health, attack, items, and equipped weapon
// oxygen is no longer controlled here
public class Player {

    // this variable stores the current room where the player is
    private int currentRoom;

    // this variable stores all the items the player picked up
    private ArrayList<Item> inventory;

    // this variable represents the player's maximum HP
    private int maxHp;

    // this variable represents the player's current HP
    private int hp;

    // this variable represents the player's normal attack power
    private int baseAttack;

    // this variable represents the player's attack power after equipping weapons
    private int currentAttack;

    // oxygen is no longer handled here, but kept for compatibility
    private int oxygenLevel;

    // this variable stores the player's currently equipped weapon
    private Item equipped;

    // this variable is used for random chance events
    private Random rng;

    // constructor for creating a new player
    public Player(int startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<Item>();
        this.maxHp = 100;
        this.hp = 100;
        this.baseAttack = 10;
        this.currentAttack = baseAttack;
        this.oxygenLevel = 100;   // not used anymore
        this.equipped = null;
        this.rng = new Random();
    }

    // sets up the player's stats when the game begins
    public void setStats(String name, int health, int attack) {
        this.maxHp = health;
        this.hp = health;
        this.baseAttack = attack;
        this.currentAttack = attack;
        System.out.println(name + " created with " + hp + " health and " + attack + " attack.");
    }

    // gets the player's current room
    public int getCurrentRoom() {
        return currentRoom;
    }

    // sets the player's current room
    public void setCurrentRoom(int roomNumber) {
        this.currentRoom = roomNumber;
    }

    // moves the player to another room if the map allows it
    public boolean move(String direction, Map map) {
        if (map.canMove(currentRoom, direction)) {
            int nextRoom = map.getNextRoomId(currentRoom, direction);
            if (nextRoom != -1) {
                setCurrentRoom(nextRoom);
                System.out.println("You moved " + direction + " to room " + nextRoom + ".");
                return true;
            }
        }
        System.out.println("You cannot move that way.");
        return false;
    }

    // gets the player's HP
    public int getHp() {
        return hp;
    }

    // gets max HP value
    public int getMaxHp() {
        return maxHp;
    }

    // gets current attack power
    public int getCurrentAttack() {
        return currentAttack;
    }

    // oxygen compatibility only (not used)
    public int getOxygenLevel() {
        return oxygenLevel;
    }

    // unused legacy method
    public void loseOxygen(int amount) {
        oxygenLevel -= amount;
        if (oxygenLevel < 0) oxygenLevel = 0;
    }

    // unused legacy method
    public void restoreOxygen(int amount) {
        oxygenLevel += amount;
        if (oxygenLevel > 100) oxygenLevel = 100;
    }

    // adds an item to the player's inventory
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    // removes an item from the inventory using name
    public boolean removeFromInventory(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(name)) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    // removes a specific item object
    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    // finds an item by name
    public Item getInventoryItem(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    // finds an item by id
    public Item getInventoryItemById(String id) {
        for (Item i : inventory) {
            if (i.getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return null;
    }

    // equips a weapon from your inventory
    public void equip(String name) {
        Item item = getInventoryItem(name);

        if (item == null) {
            System.out.println("You do not have that item.");
            return;
        }

        if (!item.getCategory().equalsIgnoreCase("WEAPON")) {
            System.out.println("That item cannot be equipped.");
            return;
        }

        unEquip();
        equipped = item;
        currentAttack = item.getValue();
        System.out.println(item.getName() + " equipped. Attack is now " + currentAttack + ".");
    }

    // unequips the weapon
    public void unEquip() {
        if (equipped != null) {
            System.out.println("You unequipped " + equipped.getName() + ".");
        }
        equipped = null;
        currentAttack = baseAttack;
    }

    // heals the player
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
        System.out.println("You healed for " + amount + " HP.");
    }

    // takes damage from monsters
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
    }

    // checks if the player is alive
    // oxygen is checked in GameController
    public boolean isAlive() {
        return hp > 0;
    }

    // uses a consumable item
    public boolean useItem(String itemName, Monster monster) {

        if (itemName == null || itemName.trim().isEmpty()) {
            System.out.println("You must type USE followed by an item name.");
            return false;
        }

        Item item = getInventoryItem(itemName);
        if (item == null) {
            item = getInventoryItemById(itemName);
        }

        if (item == null) {
            System.out.println("You do not have that item.");
            return false;
        }

        String name = item.getName();
        String category = item.getCategory();

        // handles all consumable logic
        if (category.equalsIgnoreCase("CONSUMABLE")) {

            if (name.equalsIgnoreCase("Medkit")) {
                heal(50);
                System.out.println("You used the Medkit. You feel better.");
            }
            else if (name.toLowerCase().contains("antidote")) {
                heal(item.getValue());
                System.out.println("You inject the Antidote Syringe.");
                if (monster != null && monster.isAlive()) {
                    monster.reduceAttack(5);
                    System.out.println(monster.getName() + " seems weaker now.");
                }
            }
            else if (name.toLowerCase().contains("poison")) {
                if (monster != null && monster.isAlive()) {
                    monster.takeDamage(item.getValue());
                    System.out.println("You throw the poison. It burns the enemy.");
                } else {
                    System.out.println("There is no target.");
                    return false;
                }
            }
            else if (name.equalsIgnoreCase("Snake Oil")) {
                if (monster != null && monster.isAlive()
                        && monster.getName().toLowerCase().contains("angerfish")) {
                    monster.takeDamage(item.getValue() * 2);
                    System.out.println("Super effective.");
                } else if (monster != null && monster.isAlive()) {
                    monster.takeDamage(item.getValue());
                    System.out.println("You splash Snake Oil on the enemy.");
                } else {
                    System.out.println("There is no target.");
                    return false;
                }
            }
            else {
                heal(item.getValue());
                System.out.println("You use the " + name + ".");
            }

            removeFromInventory(item);
            return true;
        }

        System.out.println("You cannot use that item right now.");
        return false;
    }

    // prints a single line showing inventory, health, and equipped weapon
    public String inventoryLine() {
        if (inventory.isEmpty()) {
            return "Your inventory is empty.";
        }
        String result = "";
        for (int i = 0; i < inventory.size(); i++) {
            result += inventory.get(i).getName();
            if (i < inventory.size() - 1) {
                result += ", ";
            }
        }
        if (equipped != null) {
            result += " | Equipped: " + equipped.getName();
        }
        result += " | HP: " + hp + "/" + maxHp;
        return result;
    }

    // returns all items in inventory
    public ArrayList<Item> getInventoryItems() {
        return inventory;
    }

    // this method now prints oxygen automatically using a value passed in
    public void showStats(int oxy) {
        System.out.println("----- PLAYER STATS -----");
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Oxygen Level: " + oxy + "/100");
        System.out.println("Base Attack: " + baseAttack);
        System.out.println("Current Attack: " + currentAttack);

        if (equipped != null) {
            System.out.println("Equipped Weapon: " + equipped.getName());
        } else {
            System.out.println("Equipped Weapon: None");
        }

        System.out.println("------------------------");
    }
}