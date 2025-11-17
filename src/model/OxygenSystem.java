package model;

// Created by: Eyob
// This class handles all oxygen behavior in the game.
// It tracks current oxygen, draining, restoring, and checking if oxygen is critical.
public class OxygenSystem {

    // this variable stores the current oxygen level
    private int oxygenLevel;

    // this variable controls how fast oxygen drains
    private int depletionRate;

    // constructor sets starting oxygen and how fast it drains
    public OxygenSystem(int startingLevel, int depletionRate) {
        this.oxygenLevel = startingLevel;
        this.depletionRate = depletionRate;
    }

    // gets the current oxygen level
    public int getOxygenLevel() {
        return oxygenLevel;
    }

    // this method reduces oxygen by the normal depletion rate
    public void decreaseOxygen() {
        oxygenLevel -= depletionRate;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

    // this method reduces oxygen by a specific amount
    public void decreaseOxygenBy(int amount) {
        oxygenLevel -= amount;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

    // this method restores oxygen by a specific amount
    public void restoreOxygen(int amount) {
        oxygenLevel += amount;
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

    // this method restores oxygen when using an oxygen item
    public void useOxygenItem(int amount) {
        restoreOxygen(amount);
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

    // this method checks if oxygen is dangerously low
    public boolean isCritical() {
        return oxygenLevel <= 20;
    }

    // this prints the current oxygen level to the player
    public void showOxygenStatus() {
        if (isCritical()) {
            System.out.println("Warning. Oxygen level critical (" + oxygenLevel + "%). Find oxygen soon.");
        } else {
            System.out.println("Oxygen level: " + oxygenLevel + "%");
        }
    }
    public void setOxygenLevel(int lvl) {
        this.oxygenLevel = lvl;
    }

    // this checks if the player still has oxygen left
    public boolean canBreathe() {
        return oxygenLevel > 0;
    }
}