import java.util.ArrayList;
import java.util.Random;

// represents the player in the game
// tracks the player's current location, health, oxygen level, equipped items, and inventory
public class Player {

    // this variable stores the current room number where the player is located
    private int currentRoom;

    // this variable holds all items that the player picked up
    private ArrayList<Item> inventory;

    // this variable represents the player's maximum health
    private int maxHp;

    // this variable represents the player's current health
    private int hp;

    // this variable represents the player's base attack power
    private int baseAttack;

    // this variable represents the player's current attack power (changes when weapon equipped)
    private int currentAttack;

    // this variable stores the player's current oxygen level
    private int oxygenLevel;

    // this variable holds the currently equipped weapon
    private Item equipped;

    // this variable helps to generate random numbers for battle or chance events
    private Random rng;

    // constructor for creating a player
    // startingRoom = the room number where the player starts the game
    public Player(int startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<Item>();
        this.maxHp = 100;
        this.hp = 100;
        this.baseAttack = 10;
        this.currentAttack = baseAttack;
        this.oxygenLevel = 100;
        this.equipped = null;
        this.rng = new Random();
    }

    // gets the player's current room number
    // return = current room id
    public int getCurrentRoom() {
        return currentRoom;
    }

    // sets the player's current room number
    // roomNumber = new room id
    public void setCurrentRoom(int roomNumber) {
        this.currentRoom = roomNumber;
    }

    // gets the player's current HP
    public int getHp() {
        return hp;
    }

    // gets the player's maximum HP
    public int getMaxHp() {
        return maxHp;
    }

    // gets the player's current oxygen level
    public int getOxygenLevel() {
        return oxygenLevel;
    }

    // reduces the player's oxygen level by a specific amount
    // amount = how much oxygen to remove
    public void loseOxygen(int amount) {
        oxygenLevel -= amount;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

    // restores the player's oxygen level by a specific amount
    // amount = how much oxygen to add
    public void restoreOxygen(int amount) {
        oxygenLevel += amount;
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

    // adds an item to the player's inventory
    // item = the item to add
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    // removes an item from the player's inventory by name
    // name = name of the item to remove
    // return = true if item removed, false if not found
    public boolean removeFromInventory(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(name)) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    // gets all items in the player's inventory
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    // finds a specific item in the player's inventory by name
    // name = name of the item
    // return = the item if found, otherwise null
    public Item getInventoryItem(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    // equips a weapon from the inventory
    // name = the name of the weapon to equip
    public void equip(String name) {
        Item item = getInventoryItem(name);
        if (item == null) {
            System.out.println("You don’t have that item.");
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

    // unequips the current weapon and restores base attack
    public void unEquip() {
        if (equipped != null) {
            System.out.println("You unequipped " + equipped.getName() + ".");
        }
        equipped = null;
        currentAttack = baseAttack;
    }

    // heals the player by a specific amount
    // amount = how much health to restore
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
        System.out.println("You healed for " + amount + " HP!");
    }

    // decreases the player's HP by a specific damage amount
    // damage = how much health to lose
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    // checks if the player is alive
    // return = true if player has HP and oxygen left
    public boolean isAlive() {
        return hp > 0 && oxygenLevel > 0;
    }

    // shows a summary of the player's inventory and equipped weapon
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
        result += " | HP: " + hp + "/" + maxHp + " | Oxygen: " + oxygenLevel + "%";
        return result;
    }

    // displays the player's current stats
    public void showStats() {
        System.out.println("----- PLAYER STATS -----");
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Oxygen: " + oxygenLevel + "%");
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