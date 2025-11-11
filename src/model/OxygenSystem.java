package model;

// represents the player's oxygen management system
// keeps track of how much oxygen the player has left
// reduces oxygen as time passes or as the player moves between rooms
public class OxygenSystem {

    // this variable represents the current oxygen level (out of 100)
    private int oxygenLevel;

    // this variable sets how much oxygen is lost each turn or movement
    private int depletionRate;

    // constructor for creating an oxygen system
    // startingLevel = starting oxygen percentage
    // depletionRate = how much oxygen is lost each move
    public OxygenSystem(int startingLevel, int depletionRate) {
        this.oxygenLevel = startingLevel;
        this.depletionRate = depletionRate;
    }

    // gets the current oxygen level
    public int getOxygenLevel() {
        return oxygenLevel;
    }

    // decreases oxygen by the set depletion rate
    public void decreaseOxygen() {
        oxygenLevel -= depletionRate;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

    // decreases oxygen by a custom amount
    // amount = how much oxygen to remove
    public void decreaseOxygenBy(int amount) {
        oxygenLevel -= amount;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

    // restores oxygen by a custom amount
    // amount = how much oxygen to add
    public void restoreOxygen(int amount) {
        oxygenLevel += amount;
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

    // checks if oxygen is at a critical level (20% or lower)
    // return = true if oxygen is dangerously low
    public boolean isCritical() {
        return oxygenLevel <= 20;
    }

    // displays a warning message if oxygen is low
    public void showOxygenStatus() {
        if (isCritical()) {
            System.out.println("Warning: Oxygen level critical (" + oxygenLevel + "%). Find oxygen soon!");
        } else {
            System.out.println("Oxygen level: " + oxygenLevel + "%");
        }
    }

    // checks if player can survive (oxygen above zero)
    // return = true if oxygen left, false if out
    public boolean canBreathe() {
        return oxygenLevel > 0;
    }
}