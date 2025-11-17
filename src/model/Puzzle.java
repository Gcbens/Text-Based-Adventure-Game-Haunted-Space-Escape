package model;

// Created by: Quincy
// This class makes a puzzle for the player to solve
// Each puzzle lives in a room and has a question, a hint, and a limit on tries
public class Puzzle {

<<<<<<< HEAD
    // this number is the puzzle id
    private int id;

    // this is the short name of the puzzle like Camera Code Retrieval
    private String name;

    // this is the question the player sees
    private String question;

    // this is the hint that helps the player
    private String hint;

    // this is the correct answer
    private String answer;

    // this is the maximum tries allowed
    private int maxAttempts;

    // this keeps track of how many tries were used
    private int attemptsUsed;

    // this tells us if the puzzle is already solved
    private boolean solved;

    // this is the message on success
    private String successMessage;

    // this is the message on failure
    private String failureMessage;

    // this tells us which room the puzzle belongs to
    private int roomId;

    // NEW this is a short description that shows when you inspect the puzzle
    private String description;

    // NEW this is a small mysterious clue about why you should solve it
    private String whySolve;

    // constructor that sets all the puzzle info
    public Puzzle(int id, String name, String question, String answer, int maxAttempts,
                  String successMessage, String failureMessage, int roomId, String hint) {

        this.id = id;
        this.name = name;
        this.question = question;
        this.answer = answer.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.solved = false;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.roomId = roomId;
        this.hint = hint;

        // NEW default texts so old constructors still behave
        this.description = "";
        this.whySolve = "";
    }

    // NEW constructor that supports description and whySolve together
    public Puzzle(int id, String name, String question, String description,
                  String answer, int maxAttempts,
                  String successMessage, String failureMessage,
                  String hint, String whySolve, int roomId) {

        this.id = id;
        this.name = name;
        this.question = question;
        this.answer = answer.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.solved = false;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.roomId = roomId;
        this.hint = hint;
        this.description = description;
        this.whySolve = whySolve;
    }

    // gets the puzzle id
    public int getId() {
        return id;
    }

    // gets the puzzle name
    public String getName() {
        return name;
    }

    // gets the puzzle question text
    public String getQuestion() {
        return question;
    }

    // gets how many attempts were used
    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    // gets the maximum attempt limit
    public int getMaxAttempts() {
        return maxAttempts;
    }

    // checks if solved
    public boolean isSolved() {
        return solved;
    }

    // gets success message
    public String getSuccessMessage() {
        return successMessage;
    }

    // gets failure message
    public String getFailureMessage() {
        return failureMessage;
    }

    // gets the room id
    public int getRoomId() {
        return roomId;
    }

    // gets the hint text
    public String getHint() {
        return hint;
    }

    // NEW gets description
    public String getDescription() {
        return description;
    }

    // NEW gets whySolve text
    public String getWhySolve() {
        return whySolve;
    }

    // NEW shows puzzle info when player uses INSPECT PUZZLE
    public void showInspectDetails() {
        System.out.println("Puzzle: " + name);
        if (description != null && !description.isEmpty()) {
            System.out.println("Description: " + description);
        }
        if (whySolve != null && !whySolve.isEmpty()) {
            System.out.println(whySolve);
        }
        System.out.println("Attempts: " + (maxAttempts - attemptsUsed));
    }

    // shows the puzzle intro when puzzle mode starts
    public void showPuzzleIntro() {
        System.out.println("Puzzle: " + name);
        System.out.println(question);
    }

    // this handles input during puzzle mode
    public boolean handleInput(String playerInput) {

        // player wants to exit puzzle mode
        if (playerInput.equalsIgnoreCase("exit")) {
            System.out.println("You stop working on the puzzle.");
            return true;
        }

        // player asked for a hint
        if (playerInput.equalsIgnoreCase("hint")) {
            System.out.println("Hint: " + hint);
            return false;
        }

        // try solving with whatever the player typed
        return trySolve(playerInput);
    }

    // tries to solve the puzzle once
    public boolean trySolve(String playerAnswer) {

        // stop if solved already
        if (solved) {
            System.out.println("You already solved this puzzle.");
            return true;
        }

        // stop if no attempts remain
        if (attemptsUsed >= maxAttempts) {
            System.out.println("No more tries. " + failureMessage);
            return true;
        }

        attemptsUsed++;

        // correct answer
        if (playerAnswer.trim().equalsIgnoreCase(answer)) {
            solved = true;
            System.out.println(successMessage);
            return true;
        }

        // wrong answer
        System.out.println("Wrong answer.");

        int triesLeft = maxAttempts - attemptsUsed;

        if (triesLeft > 0) {
            // changed label to Attempts
            System.out.println("Attempts: " + triesLeft);
            return false;
        }

        // no tries left
        System.out.println("You failed the puzzle. " + failureMessage);
        return true;
    }

    // resets puzzle for reuse if needed
    public void resetPuzzle() {
        solved = false;
        attemptsUsed = 0;
    }

    // NEW helper for loading solves from the save file
    public void markSolvedFromLoad() {
        solved = true;
        attemptsUsed = maxAttempts;
    }
=======

    // represents a puzzle in the game
// puzzles are challenges that the player must solve to unlock rooms or progress further
    public class Puzzle {

        // this variable stores the unique puzzle id number
        private int id;

        // this variable stores the puzzle’s title or short name
        private String name;

        // this variable stores the puzzle question or challenge text
        private String question;

        // this variable stores the correct answer to the puzzle
        private String answer;

        // this variable stores the maximum number of attempts allowed
        private int maxAttempts;

        // this variable tracks how many attempts the player has already used
        private int attemptsUsed;

        // this variable tracks whether the puzzle has been solved or not
        private boolean solved;

        // this variable stores the success message that displays when puzzle is solved
        private String successMessage;

        // this variable stores the failure message that displays when puzzle attempts are used up
        private String failureMessage;

        // this variable represents the room id where the puzzle is located
        private int roomId;

        // constructor for creating a puzzle
        // id = puzzle id, name = short name, question = text shown to player, answer = correct solution,
        // maxAttempts = how many times player can try, successMessage = message on success, failureMessage = message on failure, roomId = room where puzzle is located
        public Puzzle(int id, String name, String question, String answer, int maxAttempts, String successMessage, String failureMessage, int roomId) {
            this.id = id;
            this.name = name;
            this.question = question;
            this.answer = answer.toLowerCase();
            this.maxAttempts = maxAttempts;
            this.attemptsUsed = 0;
            this.solved = false;
            this.successMessage = successMessage;
            this.failureMessage = failureMessage;
            this.roomId = roomId;
        }

        // gets the puzzle id number
        public int getId() {
            return id;
        }

        // gets the puzzle name
        public String getName() {
            return name;
        }

        // gets the puzzle question text
        public String getQuestion() {
            return question;
        }

        // gets the number of attempts used so far
        public int getAttemptsUsed() {
            return attemptsUsed;
        }

        // gets the maximum number of attempts allowed
        public int getMaxAttempts() {
            return maxAttempts;
        }

        // checks if the puzzle is solved
        public boolean isSolved() {
            return solved;
        }

        // gets the success message
        public String getSuccessMessage() {
            return successMessage;
        }

        // gets the failure message
        public String getFailureMessage() {
            return failureMessage;
        }

        // gets the room id where this puzzle is located
        public int getRoomId() {
            return roomId;
        }

        // attempts to solve the puzzle
        // playerAnswer = the answer that the player types in
        // return = true if correct, false if wrong
        public boolean trySolve(String playerAnswer) {
            // do not continue if puzzle is already solved or out of attempts
            if (solved) {
                System.out.println("You already solved this puzzle.");
                return false;
            }
            if (attemptsUsed >= maxAttempts) {
                System.out.println("You have used all attempts. " + failureMessage);
                return false;
            }

            // increase attempt count
            attemptsUsed++;

            // check if player’s answer matches the correct answer
            if (playerAnswer.trim().equalsIgnoreCase(answer)) {
                solved = true;
                System.out.println(successMessage);
                return true;
            } else {
                System.out.println("That answer is incorrect.");
                if (attemptsUsed >= maxAttempts) {
                    System.out.println("You have failed this puzzle. " + failureMessage);
                } else {
                    System.out.println("Attempts left: " + (maxAttempts - attemptsUsed));
                }
                return false;
            }
        }

        // resets the puzzle (used if game resets or for replay)
        public void resetPuzzle() {
            solved = false;
            attemptsUsed = 0;
        }

        // displays the puzzle information to the player
        public void showPuzzleInfo() {
            System.out.println("----- PUZZLE INFO -----");
            System.out.println("Name: " + name);
            System.out.println("Room ID: " + roomId);
            System.out.println("Question: " + question);
            System.out.println("Attempts: " + attemptsUsed + "/" + maxAttempts);
            if (solved) {
                System.out.println("Status: SOLVED");
            } else {
                System.out.println("Status: UNSOLVED");
            }
            System.out.println("-----------------------");
        }
    }

>>>>>>> ec1d4f853d56398e6703a2ec15e6a24124d4598a
}
