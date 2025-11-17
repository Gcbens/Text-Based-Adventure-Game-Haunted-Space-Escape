package model;
// Created by: Andy
// represents an item that can exist in the game
// items can be weapons, consumables, or miscellaneous key items
public class Item {

        // this variable stores the item’s unique id number (used for reference)
        private int id;

        // this variable stores the item’s name (example: “Plasma Cutter”)
        private String name;

        // this variable stores the item’s category (example: “WEAPON”, “CONSUMABLE”, “MISC”)
        private String category;

        // this variable stores the item’s description text
        private String description;

        // this variable represents how strong or useful the item is (attack damage, healing amount, etc.)
        private int value;

        // this variable is used when item type is “CONSUMABLE” to determine if it heals HP or restores oxygen
        private String effectType; // “HP” or “OXYGEN”

        // constructor for creating an item
        // id = item id, name = item name, category = type of item, description = description text, value = attack/heal amount
        public Item(int id, String name, String category, String description, int value) {
            this.id = id;
            this.name = name;
            this.category = category.toUpperCase();
            this.description = description;
            this.value = value;
            this.effectType = "";
        }

        // overloaded constructor for consumable items that affect HP or oxygen
        public Item(int id, String name, String category, String description, int value, String effectType) {
            this.id = id;
            this.name = name;
            this.category = category.toUpperCase();
            this.description = description;
            this.value = value;
            this.effectType = effectType.toUpperCase();
        }

        // gets the item’s id number
        public int getId() {
            return id;
        }

        // gets the item’s name
        public String getName() {
            return name;
        }

        // gets the item’s category type
        public String getCategory() {
            return category;
        }

        // gets the item’s description
        public String getDescription() {
            return description;
        }

        // gets the item’s value (damage, heal, or restore amount)
        public int getValue() {
            return value;
        }

        // gets the effect type (used only for consumables)
        public String getEffectType() {
            return effectType;
        }

        // checks if this item is equippable (true if category = WEAPON)
        public boolean isEquippable() {
            return category.equalsIgnoreCase("WEAPON");
        }

        // checks if this item is consumable (true if category = CONSUMABLE)
        public boolean isConsumable() {
            return category.equalsIgnoreCase("CONSUMABLE");
        }

        // checks if this item is a key or miscellaneous type
        public boolean isKeyItem() {
            return category.equalsIgnoreCase("MISC") || category.equalsIgnoreCase("KEY");
        }

        // shows the basic info for this item
        // return = string containing name, category, and value
        public String itemInfo() {
            String info = name + " (" + category + ")";
            if (isEquippable()) {
                info += " | Attack: " + value;
            } else if (isConsumable()) {
                if (effectType.equalsIgnoreCase("HP")) {
                    info += " | Restores " + value + " HP";
                } else if (effectType.equalsIgnoreCase("OXYGEN")) {
                    info += " | Restores " + value + "% oxygen";
                }
            }
            return info;
        }

        // simulates using the item
        // this method will be called by the player when they use an item
        public void use(Player player, OxygenSystem oxygenSystem) {
            // if item is a consumable
            if (isConsumable()) {
                if (effectType.equalsIgnoreCase("HP")) {
                    player.heal(value);
                    System.out.println("You used " + name + " and restored " + value + " HP!");
                } else if (effectType.equalsIgnoreCase("OXYGEN")) {
                    oxygenSystem.restoreOxygen(value);
                    System.out.println("You used " + name + " and restored " + value + "% oxygen!");
                }
            } else if (isEquippable()) {
                player.equip(name);
            } else {
                System.out.println("You examine " + name + ". " + description);
            }
        }

        // shows full details about this item (for inspection)
        public void inspect() {
            System.out.println("----- ITEM INFO -----");
            System.out.println("Name: " + name);
            System.out.println("Type: " + category);
            System.out.println("Description: " + description);
            System.out.println("Value: " + value);
            if (isConsumable()) {
                System.out.println("Effect: Restores " + effectType);
            }
            System.out.println("----------------------");
        }
    }




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
