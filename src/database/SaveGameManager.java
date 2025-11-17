package database;

import model.Monster;
import model.Puzzle;
import model.Room;
import model.OxygenSystem;
import model.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

// Created by Gabens
// This class handles saving the player's progress into a text file and loading it back into the game.
// It stores things like room location, health, oxygen, and inventory.
public class SaveGameManager {

    // this variable holds the paths to the save files
    // three total save slots allowed
    private static final String[] SAVE_SLOTS = {
            "src/SaveSlot1.txt",
            "src/SaveSlot2.txt",
            "src/SaveSlot3.txt"
    };

    // small inner class stores everything read from the save file
    public static class SaveSnapshot {

        // this variable stores the room ID the player was in
        public int playerRoomId;

        // this variable stores the player oxygen level
        public int oxygenLevel;

        // this holds the IDs of puzzles the player solved
        public HashSet<Integer> solvedPuzzleIds;

        // this holds the names of monsters the player defeated
        public HashSet<String> defeatedMonsterNames;

        // story flag, was the hidden dark room path unlocked
        public boolean darkRoomHiddenDoorUnlocked;

        // story flag, was the cargo tunnel opened
        public boolean cargoTunnelUnlocked;

        // story flag, was the escape pod door armed
        public boolean escapePodDoorArmed;

        // constructor sets up empty containers
        public SaveSnapshot() {
            solvedPuzzleIds = new HashSet<Integer>();
            defeatedMonsterNames = new HashSet<String>();
        }
    }

    // returns true if slot number is allowed
    private static boolean isValidSlot(int slot) {
        return slot >= 1 && slot <= 3;
    }

    // this method writes the current game state into the chosen save slot
    public static void saveGameToSlot(int slot,
                                      Player player,
                                      OxygenSystem oxygenSystem,
                                      ArrayList<Room> rooms,
                                      ArrayList<Monster> monsters,
                                      ArrayList<Puzzle> puzzles,
                                      boolean darkDoorUnlocked,
                                      boolean cargoTunnelUnlocked,
                                      boolean escapePodDoorArmed) {

        if (!isValidSlot(slot)) {
            System.out.println("Invalid save slot. Choose one, two, or three.");
            return;
        }

        String filePath = SAVE_SLOTS[slot - 1];

        try {
            File file = new File(filePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // store player current room ID
            writer.write("PLAYER_ROOM|" + player.getCurrentRoom());
            writer.newLine();

            // store current oxygen level
            writer.write("OXYGEN|" + oxygenSystem.getOxygenLevel());
            writer.newLine();

            // store solved puzzles
            StringBuilder solvedBuilder = new StringBuilder();
            for (Puzzle p : puzzles) {
                if (p.isSolved()) {
                    if (solvedBuilder.length() > 0) {
                        solvedBuilder.append(",");
                    }
                    solvedBuilder.append(p.getId());
                }
            }
            writer.write("PUZZLES_SOLVED|" + solvedBuilder.toString());
            writer.newLine();

            // store defeated monsters
            StringBuilder defeatedBuilder = new StringBuilder();
            for (Monster m : monsters) {
                if (!m.isAlive() || m.isGoneForever()) {
                    if (defeatedBuilder.length() > 0) {
                        defeatedBuilder.append(",");
                    }
                    defeatedBuilder.append(m.getName());
                }
            }
            writer.write("MONSTERS_DEFEATED|" + defeatedBuilder.toString());
            writer.newLine();

            // store flags
            StringBuilder flagsBuilder = new StringBuilder();
            flagsBuilder.append("darkDoor=").append(darkDoorUnlocked);
            flagsBuilder.append(",cargoTunnel=").append(cargoTunnelUnlocked);
            flagsBuilder.append(",escapePod=").append(escapePodDoorArmed);

            writer.write("FLAGS|" + flagsBuilder.toString());
            writer.newLine();

            writer.flush();
            writer.close();

            System.out.println("Game saved to slot " + slot + ".");

        } catch (Exception e) {
            System.out.println("Error saving game.");
        }
    }

    // this method loads a snapshot from a chosen save slot
    public static SaveSnapshot loadSnapshotFromSlot(int slot) {

        if (!isValidSlot(slot)) {
            System.out.println("Invalid slot. Choose one, two, or three.");
            return null;
        }

        String filePath = SAVE_SLOTS[slot - 1];
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Save slot " + slot + " is empty.");
            return null;
        }

        SaveSnapshot snapshot = new SaveSnapshot();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            // read each line
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) {
                    continue;
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                if (key.equalsIgnoreCase("PLAYER_ROOM")) {
                    snapshot.playerRoomId = Integer.parseInt(value);
                }
                else if (key.equalsIgnoreCase("OXYGEN")) {
                    snapshot.oxygenLevel = Integer.parseInt(value);
                }
                else if (key.equalsIgnoreCase("PUZZLES_SOLVED")) {
                    if (!value.isEmpty()) {
                        String[] ids = value.split(",");
                        for (String idText : ids) {
                            idText = idText.trim();
                            if (!idText.isEmpty()) {
                                int id = Integer.parseInt(idText);
                                snapshot.solvedPuzzleIds.add(id);
                            }
                        }
                    }
                }
                else if (key.equalsIgnoreCase("MONSTERS_DEFEATED")) {
                    if (!value.isEmpty()) {
                        String[] names = value.split(",");
                        for (String name : names) {
                            name = name.trim();
                            if (!name.isEmpty()) {
                                snapshot.defeatedMonsterNames.add(name);
                            }
                        }
                    }
                }
                else if (key.equalsIgnoreCase("FLAGS")) {

                    String[] flagParts = value.split(",");
                    for (String fp : flagParts) {
                        fp = fp.trim();
                        if (fp.isEmpty()) {
                            continue;
                        }
                        String[] kv = fp.split("=");
                        if (kv.length != 2) {
                            continue;
                        }

                        String flagName = kv[0].trim();
                        boolean flagValue = kv[1].trim().equalsIgnoreCase("true");

                        if (flagName.equalsIgnoreCase("darkDoor")) {
                            snapshot.darkRoomHiddenDoorUnlocked = flagValue;
                        } else if (flagName.equalsIgnoreCase("cargoTunnel")) {
                            snapshot.cargoTunnelUnlocked = flagValue;
                        } else if (flagName.equalsIgnoreCase("escapePod")) {
                            snapshot.escapePodDoorArmed = flagValue;
                        }
                    }
                }
            }

            reader.close();
            System.out.println("Loaded save slot " + slot + ".");

        } catch (Exception e) {
            System.out.println("Error reading save slot.");
            return null;
        }

        return snapshot;

    }
}