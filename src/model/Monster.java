package model;

public class Monster {

import java.util.Random;

    // represents a monster in the game
// tracks the monster’s name, description, health, attack, weakness, and defeat state
    public class Monster {

        // this variable stores the monster’s name (example: “Alien Slime”)
        private String name;

        // this variable stores a short description of the monster (example: “A slimy creature dripping acid”)
        private String description;

        // this variable represents the monster’s maximum health points
        private int maxHp;

        // this variable represents the monster’s current health points
        private int hp;

        // this variable represents the monster’s base attack power
        private int baseAttack;

        // this variable represents the monster’s attack value used in the current battle turn
        private int attackThisTurn;

        // this variable stores the monster’s weakness (example: “fire”)
        private String weakness;

        // this variable tracks if the monster has been defeated
        private boolean defeated;

        // this variable tracks if the monster has permanently disappeared from the game
        private boolean goneForever;

        // this variable is used to roll random attacks during battle
        private Random rng;

        // constructor for creating a monster
        // name = monster name, description = description text, hp = starting health, attack = base attack, weakness = weakness keyword
        public Monster(String name, String description, int hp, int attack, String weakness) {
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

        // calculates and sets a random attack value for this turn
        // rng = random number generator to decide damage variation
        public void rollBattleAttack(Random rng) {
            // randomly decide if attack is normal or critical (double damage)
            int chance = rng.nextInt(100);
            if (chance < 20) { // 20% chance of critical hit
                attackThisTurn = baseAttack * 2;
                System.out.println(name + " prepares a powerful attack!");
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
            System.out.println(name + " took " + damage + " damage!");
            if (hp == 0) {
                markDefeated();
            }
        }

        // marks the monster as defeated
        public void markDefeated() {
            defeated = true;
            System.out.println(name + " has been defeated!");
        }

        // forces the monster to leave the room permanently (used when ignored)
        public void leaveForever() {
            goneForever = true;
            System.out.println(name + " disappears into the darkness and will not return.");
        }

        // fully heals the monster back to max HP (can be used for respawn logic)
        public void healToFull() {
            hp = maxHp;
            defeated = false;
            goneForever = false;
        }

        // shows the monster’s battle status line
        public String getStatusLine() {
            return name + " - HP: " + hp + "/" + maxHp + " | Attack: " + baseAttack;
        }
    }
}
