package view;

import model.Room;
import model.Map;
import model.Puzzle;

// Created by: Gabens
// This class formats and shows all text to the player.
public class GameView {

    // wrap text so descriptions do not run off the screen
    public String wrapText(String text) {
        int wrapAt = 80;
        StringBuilder wrapped = new StringBuilder();

        String[] words = text.split(" ");
        int lineLength = 0;

        for (String w : words) {
            if (lineLength + w.length() > wrapAt) {
                wrapped.append("\n");
                lineLength = 0;
            }
            wrapped.append(w).append(" ");
            lineLength += w.length() + 1;
        }

        return wrapped.toString().trim();
    }

    // LOOK command that only shows exits with room names
    public void showLookDescription(Room room, Map map) {

        if (room.getExits().isEmpty()) {
            System.out.println("There are no exits here.");
            return;
        }

        for (String exit : room.getExits()) {

            String[] parts = exit.split(":");
            String direction = parts[0];
            int id = Integer.parseInt(parts[1]);

            Room dest = map.getRoom(id);
            String destName = (dest != null) ? dest.getName() : "Unknown Room";

            if (direction.equals("NORTH")) {
                System.out.println("To the north leads " + destName + ".");
            } else if (direction.equals("SOUTH")) {
                System.out.println("To the south leads " + destName + ".");
            } else if (direction.equals("EAST")) {
                System.out.println("To the east is the " + destName + ".");
            } else if (direction.equals("WEST")) {
                System.out.println("To the west is the " + destName + ".");
            }
        }
    }

    // EXPLORE command shows environment objects only
    public void showExplore(Room room) {

        System.out.println();

        // ITEMS
        if (!room.getItems().isEmpty()) {
            System.out.print("Items here: ");
            for (int i = 0; i < room.getItems().size(); i++) {
                System.out.print(room.getItems().get(i).getName());
                if (i < room.getItems().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(".");
        } else {
            System.out.println("Items here: none.");
        }

        // MONSTER
        if (room.getMonster() != null && room.getMonster().isAlive()) {
            System.out.println("A hostile presence lurks here: " + room.getMonster().getName() + ".");
        } else {
            System.out.println("No hostile presence detected.");
        }

        // PUZZLE
        Puzzle p = room.getPuzzle();
        if (p != null && !p.isSolved()) {
            System.out.println("You notice a puzzle here: " + p.getName() + ".");
        } else {
            System.out.println("No puzzles found in this area.");
        }

        System.out.println();
    }

    // help menu
    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("LOOK");
        System.out.println("EXPLORE");
        System.out.println("N / NORTH");
        System.out.println("S / SOUTH");
        System.out.println("E / EAST");
        System.out.println("W / WEST");
        System.out.println("PICKUP <item>");
        System.out.println("DROP <item>");
        System.out.println("INSPECT <item>");
        System.out.println("INSPECT MONSTER");
        System.out.println("INSPECT PUZZLE");
        System.out.println("EQUIP <item>");
        System.out.println("UNEQUIP");
        System.out.println("USE <item>");
        System.out.println("SOLVE (start puzzle in room)");
        System.out.println("ATTACK (in combat)");
        System.out.println("RUN (in combat, only before you attack)");
        System.out.println("INVENTORY");
        System.out.println("OXYGEN");
        System.out.println("STATS");
        System.out.println("SAVE (write progress to file)");
        System.out.println("LOAD (resume from save file)");
        System.out.println("EXIT");
    }
}
