package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// Created by: Gabens
// handles saving and loading the game data
// everything about file input and output for saving progress is handled here
public class GameFile {

    // this variable stores the name of the save file
    private String fileName;

    // constructor for creating a game file object
    // fileName = name of the file used for saving and loading game progress
    public GameFile(String fileName) {
        this.fileName = fileName;
    }

    // saves the player's current state to the file
    // player = the player object whose data will be saved
    public void saveGame(Player player) {
        try {
            PrintWriter writer = new PrintWriter(fileName);

            // write basic player stats
            writer.println(player.getCurrentRoom());
            writer.println(player.getHp());
            writer.println(player.getMaxHp());
            writer.println(player.getOxygenLevel());

            // for now, just count how many items are in the player’s backpack
            ArrayList<Item> inventory = player.getInventoryItems();
            writer.println(inventory.size());

            // write inventory items by name
            for (Item item : inventory) {
                writer.println(item.getName() + "|" + item.getCategory() + "|" + item.getValue());
            }

            writer.close();
            System.out.println("Game progress saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not save the game file.");
        }
    }

    // loads the player's state from the save file
    // player = the player object that will be restored
    // itemList = the list of all items in the game (used to rebuild inventory)
    public void loadGame(Player player, ArrayList<Item> itemList) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // read player basic stats
            int room = Integer.parseInt(scanner.nextLine());
            int hp = Integer.parseInt(scanner.nextLine());
            int maxHp = Integer.parseInt(scanner.nextLine());
            int oxygen = Integer.parseInt(scanner.nextLine());
            int itemCount = Integer.parseInt(scanner.nextLine());

            // update player info
            player.setCurrentRoom(room);
            player.heal(maxHp - hp); // bring HP to saved level
            player.restoreOxygen(oxygen - player.getOxygenLevel());

            // rebuild inventory
            for (int i = 0; i < itemCount; i++) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String itemName = parts[0];

                // find matching item in itemList
                for (Item item : itemList) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        player.addToInventory(item);
                        break;
                    }
                }
            }

            scanner.close();
            System.out.println("Game loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Save file not found.");
        } catch (Exception e) {
            System.out.println("Error: Could not load game data correctly.");
        }
    }
}
