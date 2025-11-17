package model;

<<<<<<< HEAD
// Created by: Eyob
// This class handles all oxygen behavior in the game.
// It tracks current oxygen, draining, restoring, and checking if oxygen is critical.
public class OxygenSystem {

    // this variable stores the current oxygen level
    private int oxygenLevel;

    // this variable controls how fast oxygen drains
    private int depletionRate;

    // constructor sets starting oxygen and how fast it drains
=======
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
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public OxygenSystem(int startingLevel, int depletionRate) {
        this.oxygenLevel = startingLevel;
        this.depletionRate = depletionRate;
    }

    // gets the current oxygen level
    public int getOxygenLevel() {
        return oxygenLevel;
    }

<<<<<<< HEAD
    // this method reduces oxygen by the normal depletion rate
=======
    // decreases oxygen by the set depletion rate
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public void decreaseOxygen() {
        oxygenLevel -= depletionRate;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

<<<<<<< HEAD
    // this method reduces oxygen by a specific amount
=======
    // decreases oxygen by a custom amount
    // amount = how much oxygen to remove
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public void decreaseOxygenBy(int amount) {
        oxygenLevel -= amount;
        if (oxygenLevel < 0) {
            oxygenLevel = 0;
        }
    }

<<<<<<< HEAD
    // this method restores oxygen by a specific amount
=======
    // restores oxygen by a custom amount
    // amount = how much oxygen to add
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public void restoreOxygen(int amount) {
        oxygenLevel += amount;
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

<<<<<<< HEAD
    // this method restores oxygen when using an oxygen item
    public void useOxygenItem(int amount) {
        restoreOxygen(amount);
        if (oxygenLevel > 100) {
            oxygenLevel = 100;
        }
    }

    // this method checks if oxygen is dangerously low
=======
    // checks if oxygen is at a critical level (20% or lower)
    // return = true if oxygen is dangerously low
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public boolean isCritical() {
        return oxygenLevel <= 20;
    }

<<<<<<< HEAD
    // this prints the current oxygen level to the player
    public void showOxygenStatus() {
        if (isCritical()) {
            System.out.println("Warning. Oxygen level critical (" + oxygenLevel + "%). Find oxygen soon.");
=======
    // displays a warning message if oxygen is low
    public void showOxygenStatus() {
        if (isCritical()) {
            System.out.println("Warning: Oxygen level critical (" + oxygenLevel + "%). Find oxygen soon!");
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
        } else {
            System.out.println("Oxygen level: " + oxygenLevel + "%");
        }
    }
<<<<<<< HEAD
    public void setOxygenLevel(int lvl) {
        this.oxygenLevel = lvl;
    }

    // this checks if the player still has oxygen left
=======

    // checks if player can survive (oxygen above zero)
    // return = true if oxygen left, false if out
>>>>>>> 38f27ce9fdf6b988ece66d4e212656444e23ad5d
    public boolean canBreathe() {
        return oxygenLevel > 0;
    }
}