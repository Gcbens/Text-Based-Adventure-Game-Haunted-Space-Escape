package controller;

import java.util.Scanner;
// Created by: Gabens
// This class reads the player input, cleans it up, and turns it into commands for the game.
// It helps the GameController understand what the player wants to do.
public class CommandParser {

    // this variable is used to read user input from the console
    private Scanner scanner;

    // this variable stores the last full line the player typed
    private String fullInput;

    // this variable stores the first word of the player input (the command)
    private String commandWord;

    // this variable stores any extra words after the command (the argument)
    private String argument;

    // constructor for creating a command parser
    // it prepares a scanner to read from the keyboard
    public CommandParser() {
        scanner = new Scanner(System.in);
        fullInput = "";
        commandWord = "";
        argument = "";
    }

    // reads one line of input from the player
    // this method waits for the player to type something, then splits it into command and argument
    public void readCommand() {
        System.out.print("\n> "); // shows a prompt for the player to type
        fullInput = scanner.nextLine().trim();

        // if player pressed enter without typing anything
        if (fullInput.isEmpty()) {
            commandWord = "";
            argument = "";
            return;
        }

        // split the input into parts (example: “go north” → [go, north])
        String[] parts = fullInput.split(" ", 2);
        commandWord = parts[0].toLowerCase();
    }
}


