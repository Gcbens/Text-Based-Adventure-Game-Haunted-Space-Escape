package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Item;
import model.Monster;
import model.Puzzle;
import model.Room;

// Created by Gabens
// This class reads game data from text files such as rooms, items, and monster info.
// It sends the data to the model classes so the game can build the world.
public class TextFileManager {

    // this builds a file path inside src directory
    private static File makeFile(String filePath) {
        return new File("src/" + filePath);
    }

    // this reads room ids like R1 or R12
    private static int parseRoomId(String raw) {
        raw = raw.trim();
        if (raw.matches("R\\d+")) {
            return Integer.parseInt(raw.substring(1));
        }
        return -1;
    }

    // loads rooms from Rooms.txt
    public static ArrayList<Room> loadRooms(String filePath) {
        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            Scanner scanner = new Scanner(makeFile(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;

                int id = parseRoomId(parts[0]);
                if (id == -1) {
                    System.out.println("Invalid room ID, " + parts[0]);
                    continue;
                }

                String name = parts[1];
                String description = parts[2];

                Room room = new Room(id, name, description);

                for (int i = 3; i < parts.length; i++) {
                    String exitText = parts[i].trim();
                    if (!exitText.isEmpty()) {
                        room.addExit(exitText.toUpperCase());
                    }
                }

                rooms.add(room);
            }

            scanner.close();
            System.out.println("Rooms loaded, " + rooms.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error, Rooms file not found, " + filePath);
        }

        return rooms;
    }

    // loads items from Items.txt
    public static ArrayList<Item> loadItems(String filePath) {
        ArrayList<Item> items = new ArrayList<Item>();

        try {
            Scanner scanner = new Scanner(makeFile(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                String itemId = parts[0];
                String name = parts[1];
                String description = parts[2];

                int roomId = parseRoomId(parts[3]);
                if (roomId == -1) {
                    System.out.println("Invalid room ID in item line: " + line);
                    continue;
                }

                String category = parts[4];
                int value = Integer.parseInt(parts[5]);

                items.add(new Item(itemId, name, description, roomId, category, value));
            }

            scanner.close();
            System.out.println("Items loaded, " + items.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error, Items file not found, " + filePath);
        }

        return items;
    }

    // loads monsters
    public static ArrayList<Monster> loadMonsters(String filePath) {
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        try {
            Scanner scanner = new Scanner(makeFile(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 8) {
                    System.out.println("Invalid monster line (needs at least 8 fields): " + line);
                    continue;
                }

                String name = parts[1];

                int roomId = parseRoomId(parts[2]);
                if (roomId == -1) {
                    System.out.println("Invalid monster room ID, " + parts[2]);
                    continue;
                }

                String description = parts[3];
                int hp = Integer.parseInt(parts[4]);
                int attack = Integer.parseInt(parts[5]);
                String weakness = parts[6];

                String dropItemId = parts[7];

                monsters.add(new Monster(name, description, hp, attack, weakness, dropItemId));
            }

            scanner.close();
            System.out.println("Monsters loaded, " + monsters.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error, Monsters file not found, " + filePath);
        }

        return monsters;
    }

    // loads puzzles
    public static ArrayList<Puzzle> loadPuzzles(String filePath) {
        ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();

        try {
            Scanner scanner = new Scanner(makeFile(filePath));

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");

                if (parts.length < 11) {
                    System.out.println("Invalid puzzle line (needs 11 fields): " + line);
                    continue;
                }

                int id = Integer.parseInt(parts[0].substring(1));
                String name = parts[1];
                String question = parts[2];
                String description = parts[3];
                String answer = parts[4];
                int maxAttempts = Integer.parseInt(parts[5]);
                String success = parts[6];
                String failure = parts[7];
                String hint = parts[8];
                String whySolve = parts[9];

                int roomId = parseRoomId(parts[10]);
                if (roomId == -1) {
                    System.out.println("Invalid puzzle room ID, " + parts[10]);
                    continue;
                }

                // must match the Puzzle constructor exactly as written in your code
                Puzzle puzzle = new Puzzle(
                        id,
                        name,
                        question,
                        description,
                        answer,
                        maxAttempts,
                        success,
                        failure,
                        hint,
                        whySolve,
                        roomId
                );

                puzzles.add(puzzle);
            }

            scanner.close();
            System.out.println("Puzzles loaded, " + puzzles.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error, Puzzles file not found, " + filePath);
        }

        return puzzles;
    }
}
