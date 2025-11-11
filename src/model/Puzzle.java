package model;

public class Puzzle {
    public static void main(String[] args) {}

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
}
