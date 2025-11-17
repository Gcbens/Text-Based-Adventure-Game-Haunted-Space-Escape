package model;

import java.util.Random;

// Created by: Quincy
// This class represents the enemies found in the world.
// A monster has a name, health, attack strength, and reacts when the player interacts with it.
public class Monster {

    // this variable stores the monster’s name (example, "Alien Slime")
    private String name;

    // this variable stores a short description of the monster
    private String description;

    // this variable represents the monster’s maximum health points
    private int maxHp;

    // this variable represents the monster’s current health points
    private int hp;

    // this variable represents the monster’s base attack power
    private int baseAttack;

    // this variable represents the monster’s attack value used in the current battle turn
    private int attackThisTurn;

    // this variable stores the monster’s weakness (example, "fire")
    private String weakness;

    // this variable tracks if the monster has been defeated
    private boolean defeated;

    // this variable tracks if the monster has permanently disappeared from the game
    private boolean goneForever;

    // this variable is available if you want internal random behavior
    private Random rng;

    // this variable stores the id of the item that this monster will drop
    // example: "I4" for the Bloodstained Keycard
    // if the value is "None" or blank then the monster drops nothing
    private String dropItemId;

    // constructor for creating a monster
    // name = monster name, description = description text, hp = starting health, attack = base attack,
    // weakness = weakness keyword, dropItemId = id of the item to drop when defeated or "None"
    public Monster(String name, String description, int hp, int attack, String weakness, String dropItemId) {
        this.name = name;
        this.description = description;
        this.maxHp = hp;
        this.hp = hp;
        this.baseAttack = attack;
        this.weakness = weakness;
        this.defeated = false;
        this.goneForever = false;
        this.attackThisTurn = 0;
        this.rng = new Random();
        this.dropItemId = dropItemId;
    }

    // gets the monster’s name
    public String getName() {
        return name;
    }

    // gets the monster’s description
    public String getDescription() {
        return description;
    }

    // gets the monster’s current HP
    public int getHp() {
        return hp;
    }

    // gets the monster’s maximum HP
    public int getMaxHp() {
        return maxHp;
    }

    // gets the monster’s base attack value
    public int getBaseAttack() {
        return baseAttack;
    }

    // gets the monster’s weakness (used for damage bonus checks)
    public String getWeakness() {
        return weakness;
    }

    // checks if the monster has been defeated
    public boolean isDefeated() {
        return defeated;
    }

    // checks if the monster has been permanently removed from the game
    public boolean isGoneForever() {
        return goneForever;
    }

    // checks if the monster is still alive
    public boolean isAlive() {
        return hp > 0;
    }

    // returns the id of the item that this monster should drop
    public String getDropItemId() {
        return dropItemId;
    }

    // true if this monster actually has a real drop item
    public boolean hasDropItem() {
        if (dropItemId == null) {
            return false;
        }

        String trimmed = dropItemId.trim();

        if (trimmed.isEmpty()) {
            return false;
        }

        if (trimmed.equalsIgnoreCase("none")) {
            return false;
        }

        return true;
    }

    // calculates and sets a random attack value for this turn
    // rng = random number generator to decide damage variation
    public void rollBattleAttack(Random rng) {
        int chance = rng.nextInt(100);

        // 20 percent chance of a critical hit that doubles damage
        if (chance < 20) {
            attackThisTurn = baseAttack * 2;
        } else {
            attackThisTurn = baseAttack;
        }
    }

    // gets the attack value rolled for this turn
    public int getAttackThisTurn() {
        return attackThisTurn;
    }

    // reduces the monster’s health by a specific amount
    // damage = how much HP to remove
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }

        // no print here because GameController handles combat text

        if (hp == 0) {
            markDefeated();
        }
    }

    // marks the monster as defeated
    public void markDefeated() {
        defeated = true;
    }

    // forces the monster to leave the room permanently
    public void leaveForever() {
        goneForever = true;
    }

    // fully heals the monster back to max HP
    public void healToFull() {
        hp = maxHp;
        defeated = false;
        goneForever = false;
    }

    // returns a status string used by the HUD in battle
    public String getStatusLine() {
        return name + " - HP: " + hp + "/" + maxHp + " | Attack: " + baseAttack;
    }

    public void reduceAttack(int amount) {
        baseAttack -= amount;
        if (baseAttack < 0) {
            baseAttack = 0;
        }
    }
}