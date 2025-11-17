import controller.GameController;

// Created by: Gabens
// this is the starting point of the Haunted Space Escape game
// this class only runs the game controller and starts the game
public class Main {

    // the main method starts when the game launches
    // it creates a game controller object and runs the game
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     WELCOME TO HAUNTED SPACE ESCAPE!");
        System.out.println("===========================================\n");

        // create the game controller that manages everything
        GameController controller = new GameController();

        // load all game files and data
        controller.initializeGame();

        // start the main game loop
        controller.startGame();
    }
}
