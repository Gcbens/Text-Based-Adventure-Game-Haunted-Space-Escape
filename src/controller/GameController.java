package controller;

import model.*;
import database.TextFileManager;
import database.SaveGameManager;
import view.GameView;

import java.util.ArrayList;
import java.util.Scanner;

// Created by: Gabens
// This controller runs the entire game
// It connects the player actions to rooms, monsters, puzzles, and items.
public class GameController {

    // the player object that holds stats and inventory
    private Player player;

    // the oxygen system that drains while moving
    private OxygenSystem oxygenSystem;

    // lists for everything loaded from text files
    private ArrayList<Room> rooms;
    private ArrayList<Item> items;
    private ArrayList<Monster> monsters;
    private ArrayList<Puzzle> puzzles;

    // main game state trackers
    private boolean isRunning;
    private Scanner scanner;

    // map stores all rooms by id
    private Map map;

    // view handles printed formatting
    private GameView view;

    // combat state
    private boolean inCombat;
    private Monster currentMonster;
    private boolean canRunThisCombat;

    // puzzle state
    private boolean inPuzzleMode;
    private Puzzle activePuzzle;

    // this set remembers which rooms the player has visited
    private java.util.HashSet<Integer> visitedRooms;

    // story and progression flags
    private boolean darkRoomHiddenDoorUnlocked;
    private boolean cargoTunnelUnlocked;
    private boolean escapePodDoorArmed;

    // constructor sets up all lists and systems
    public GameController() {
        rooms = new ArrayList<Room>();
        items = new ArrayList<Item>();
        monsters = new ArrayList<Monster>();
        puzzles = new ArrayList<Puzzle>();
        oxygenSystem = new OxygenSystem(100, 5);
        map = new Map();
        scanner = new Scanner(System.in);
        view = new GameView();
        isRunning = true;
        inCombat = false;
        currentMonster = null;
        canRunThisCombat = false;
        inPuzzleMode = false;
        activePuzzle = null;
        visitedRooms = new java.util.HashSet<Integer>();
        darkRoomHiddenDoorUnlocked = false;
        cargoTunnelUnlocked = false;
        escapePodDoorArmed = false;
    }

    // this loads all game data from text files
    public void initializeGame() {

        System.out.println("Loading game data.");

        // load all lists from TextFileManager
        rooms = TextFileManager.loadRooms("Rooms.txt");
        items = TextFileManager.loadItems("Items.txt");
        monsters = TextFileManager.loadMonsters("Monsters.txt");
        puzzles = TextFileManager.loadPuzzles("Puzzles.txt");

        // add rooms to map
        for (Room room : rooms) {
            map.addRoom(room);
        }

        // place items inside rooms
        for (Item item : items) {
            Room target = map.getRoom(item.getRoomId());
            if (target != null) {
                target.addItem(item);
            }
        }

        // place monsters
        int index = 0;
        try {
            java.io.File file = new java.io.File("src/Monsters.txt");
            java.util.Scanner scan = new java.util.Scanner(file);

            while (scan.hasNextLine() && index < monsters.size()) {
                String line = scan.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    continue;
                }

                String idStr = parts[2].trim();
                if (idStr.startsWith("R")) {
                    int roomId = Integer.parseInt(idStr.substring(1));
                    Room target = map.getRoom(roomId);

                    if (target != null) {
                        target.addMonster(monsters.get(index));
                    }
                    index++;
                }
            }
            scan.close();

        } catch (Exception e) {
            System.out.println("Error placing monsters");
        }

        // place puzzles
        for (Puzzle puzzle : puzzles) {
            Room target = map.getRoom(puzzle.getRoomId());
            if (target != null) {
                target.addPuzzle(puzzle);
            }
        }

        // create player with starting stats
        player = new Player(1);
        player.setStats("Player", 100, 10);

        System.out.println();
        System.out.println("All data loaded.");
        System.out.println("Type HELP to view commands.");
        System.out.println();
    }

    // helper to see if player has an item by name
    private boolean playerHasItem(String name) {
        if (name == null) {
            return false;
        }
        Item byName = player.getInventoryItem(name);
        if (byName != null) {
            return true;
        }
        // also check by id if they type I4 etc
        Item byId = player.getInventoryItemById(name);
        return byId != null;
    }

    // finds an item by its id in the full items list loaded from Items.txt
    // this is used for monster drops so the drop item does not need to be in a room yet
    private Item findItemByGlobalId(String itemId) {
        if (itemId == null) {
            return null;
        }

        String trimmed = itemId.trim();

        if (trimmed.isEmpty()) {
            return null;
        }

        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(trimmed)) {
                return item;
            }
        }

        return null;
    }


    private String normalizeDirection(String input) {
        input = input.toLowerCase();

        if (input.equals("n") || input.equals("north") || input.equals("go north") || input.equals("go n")) {
            return "north";
        }
        if (input.equals("s") || input.equals("south") || input.equals("go south") || input.equals("go s")) {
            return "south";
        }
        if (input.equals("e") || input.equals("east") || input.equals("go east") || input.equals("go e")) {
            return "east";
        }
        if (input.equals("w") || input.equals("west") || input.equals("go west") || input.equals("go w")) {
            return "west";
        }

        return input;
    }

    // shows the room name and description
    private void showCurrentRoom() {
        Room room = map.getRoom(player.getCurrentRoom());
        System.out.println();
        System.out.println("You are in: " + room.getName());

        // if we have seen this room before, tell the player
        if (visitedRooms.contains(room.getId())) {
            System.out.println("You have been here before.");
        }

        System.out.println(view.wrapText(room.getDescription()));
        System.out.println();

        // mark this room as visited
        visitedRooms.add(room.getId());
        room.markVisited();
    }

    // prints both HP bars during combat
    private void showCombatStatus() {
        if (currentMonster == null) {
            return;
        }

        System.out.println("[Your HP: " + player.getHp() + "]   vs ["
                + currentMonster.getName() + " HP: " + currentMonster.getHp() + "]");
    }

    // shows the combat move options for the player
    private void showCombatMovePrompt() {
        if (canRunThisCombat) {
            System.out.println("Your move (RUN, ATTACK, USE <item>, EQUIP <item>, UNEQUIP <item>, INSPECT <item>, PICKUP <item>, DROP <item>, INVENTORY, INSPECT MONSTER)");
        } else {
            System.out.println("Your move (ATTACK, USE <item>, EQUIP <item>, UNEQUIP <item>, INSPECT <item>, PICKUP <item>, DROP <item>, INVENTORY, INSPECT MONSTER)");
        }
    }

    // monster hits the player during combat
    private void applyMonsterAttack(Monster monster) {
        java.util.Random rng = new java.util.Random();
        monster.rollBattleAttack(rng);

        int damage = monster.getAttackThisTurn();
        player.takeDamage(damage);

        System.out.println(monster.getName() + " attacked for " + damage);
        showCombatStatus();
    }

    // checks if a monster appears in the room
    private void checkForMonsterEncounter() {
        Room room = map.getRoom(player.getCurrentRoom());
        Monster mon = room.getMonster();

        if (mon != null && mon.isAlive() && !mon.isGoneForever()) {
            inCombat = true;
            currentMonster = mon;
            canRunThisCombat = true;

            System.out.println();
            System.out.println("A " + mon.getName() + " appears.");
            applyMonsterAttack(mon);

            if (player.isAlive()) {
                showCombatMovePrompt();
            }
        }
    }

    // handles picking up an item
    private void handlePickup(String name, Room room) {
        if (name.isEmpty()) {
            System.out.println("Type PICKUP followed by a name.");
            return;
        }

        Item found = room.getItemByName(name);
        if (found == null) {
            found = room.getItemById(name);
        }

        if (found == null) {
            System.out.println("That item is not here.");
            return;
        }

        room.removeItem(found);
        player.addToInventory(found);

        System.out.println(found.getName() + " added to your inventory.");
    }

    // handles dropping an item
    private void handleDrop(String name, Room room) {
        if (name.isEmpty()) {
            System.out.println("Type DROP followed by an item name.");
            return;
        }

        Item item = player.getInventoryItem(name);
        if (item == null) {
            item = player.getInventoryItemById(name);
        }

        if (item == null) {
            System.out.println("You do not have that item.");
            return;
        }

        player.removeFromInventory(item);
        room.addItem(item);

        System.out.println(item.getName() + " dropped.");
    }

    // handles inspecting a monster
    private void handleInspectMonster(Room room) {
        Monster mon = room.getMonster();

        if (mon == null || !mon.isAlive()) {
            System.out.println("There is no monster to inspect.");
            return;
        }

        System.out.println("Monster: " + mon.getName());
        System.out.println("HP: " + mon.getHp());
        System.out.println("Weakness: " + mon.getWeakness());
        System.out.println(mon.getDescription());
    }

    // new, handles inspecting a puzzle
    private void handleInspectPuzzle(Room room) {
        Puzzle p = room.getPuzzle();

        if (p == null || p.isSolved()) {
            System.out.println("There is no unsolved puzzle to inspect.");
            return;
        }

        p.showInspectDetails();
    }

    // handles inspecting an item
    private void handleInspectItem(String name, Room room) {
        Item item = room.getItemByName(name);
        if (item == null) {
            item = room.getItemById(name);
        }
        if (item == null) {
            item = player.getInventoryItem(name);
        }
        if (item == null) {
            item = player.getInventoryItemById(name);
        }

        if (item == null) {
            System.out.println("No such item found.");
            return;
        }

        System.out.println(item.getName() + ": " + item.getDescription());
    }

    // special logic when using the flashlight
    private void handleFlashlightUse(Room room) {

        // make sure player actually has a flashlight
        Item flash = player.getInventoryItem("Flashlight");
        if (flash == null) {
            flash = player.getInventoryItemById("I3");
        }

        if (flash == null) {
            System.out.println("You do not have a flashlight.");
            return;
        }

        // using flashlight in the Dark Room reveals a hidden door to Haunted Cafeteria
        if (room.getId() == 2) {

            if (!darkRoomHiddenDoorUnlocked) {

                Room darkRoom = map.getRoom(2);
                Room cafeteria = map.getRoom(7);

                if (darkRoom != null && cafeteria != null) {

                    if (!darkRoom.hasExit("NORTH")) {
                        darkRoom.addExit("NORTH:7");
                    }
                    if (!cafeteria.hasExit("SOUTH")) {
                        cafeteria.addExit("SOUTH:2");
                    }

                    darkRoomHiddenDoorUnlocked = true;

                    System.out.println("You shine the flashlight around the Dark Room.");
                    System.out.println("Scratch marks on the wall reveal a hidden doorway to the north.");
                } else {
                    System.out.println("You shine the flashlight around, but nothing new appears.");
                }

            } else {
                System.out.println("You sweep the flashlight around. The hidden doorway is already open.");
            }

            return;
        }

        // using flashlight in combat against the Mutated Angerfish counts as a weakness hit
        if (inCombat && currentMonster != null && currentMonster.isAlive()
                && currentMonster.getName().toLowerCase().contains("angerfish")) {

            System.out.println("You blast bright light at the " + currentMonster.getName() + ".");
            System.out.println("Its glowing eyes flare and it thrashes in pain.");

            // deal a flat chunk of damage as a weakness
            currentMonster.takeDamage(20);
            showCombatStatus();

            if (!currentMonster.isAlive()) {
                System.out.println("The light overloads the " + currentMonster.getName() + ".");
                System.out.println("It slumps and stops moving.");

                // handle item drop for flashlight kill
                handleMonsterDrop(room, currentMonster);

                currentMonster.markDefeated();
                inCombat = false;
                currentMonster = null;
                return;
            }

            // monster gets its turn since USE costs a turn
            applyMonsterAttack(currentMonster);
            return;
        }

        // default flashlight behavior
        System.out.println("You turn on the flashlight. It helps you see, but nothing special happens here.");
    }

    // special logic when using the Bloodstained Keycard
    private void handleKeycardUse(Room room) {

        Item key = player.getInventoryItem("Bloodstained Keycard");
        if (key == null) {
            key = player.getInventoryItemById("I4");
        }

        if (key == null) {
            System.out.println("You do not have a keycard.");
            return;
        }

        // using the keycard in the Engine Room can unlock a path to the Echoing Hallway
        if (room.getId() == 16) {

            Room engine = map.getRoom(16);
            Room hallway = map.getRoom(19);

            if (engine != null && hallway != null && !engine.hasExit("SOUTH")) {

                engine.addExit("SOUTH:19");
                if (!hallway.hasExit("NORTH")) {
                    hallway.addExit("NORTH:16");
                }

                System.out.println("You swipe the Bloodstained Keycard at a sealed hatch.");
                System.out.println("Heavy locks release. A new passage opens to a dark hallway below.");
            } else {
                System.out.println("You try the keycard on a nearby panel, but this door is already open.");
            }

            return;
        }

        // using the keycard in the Echoing Hallway arms the escape pod door
        if (room.getId() == 19) {
            if (!escapePodDoorArmed) {
                escapePodDoorArmed = true;
                System.out.println("You hold up the Bloodstained Keycard to a scanner near the escape pod door.");
                System.out.println("Red lights flicker, then turn green. The escape pod lock hums as it unlocks.");
            } else {
                System.out.println("The escape pod door is already unlocked.");
            }
            return;
        }

        // other rooms do nothing special
        System.out.println("You try the keycard here, but nothing unlocks.");
    }

    // this method fires after a puzzle finishes and applies rewards
    private void applyPuzzleRewards(Puzzle puzzle) {

        if (puzzle == null) {
            return;
        }

        String name = puzzle.getName();

        // Constellation Room puzzle unlocks the tunnel from Vacuum Room to Cargo Bay
        if (name.equalsIgnoreCase("Camera Code Retrieval")) {

            if (!cargoTunnelUnlocked) {

                Room vacuum = map.getRoom(5);
                Room cargo = map.getRoom(12);

                if (vacuum != null && cargo != null) {

                    if (!vacuum.hasExit("EAST")) {
                        vacuum.addExit("EAST:12");
                    }
                    if (!cargo.hasExit("WEST")) {
                        cargo.addExit("WEST:5");
                    }

                    cargoTunnelUnlocked = true;

                    System.out.println("You hear metal locks releasing somewhere in the ship.");
                    System.out.println("A maintenance tunnel between the Vacuum Room and Cargo Bay is now open.");
                }
            }
        }

        // you can add more puzzle rewards here later if you want
    }

    // handles dropping items from monsters into the current room
    private void handleMonsterDrop(Room room, Monster monster) {
        if (room == null || monster == null) {
            return;
        }

        // if monster has no drop item set then do nothing
        if (!monster.hasDropItem()) {
            return;
        }

        String dropId = monster.getDropItemId();
        if (dropId == null) {
            return;
        }

        // find the item in the full items list
        Item dropItem = findItemByGlobalId(dropId);

        if (dropItem == null) {
            System.out.println("Something should have dropped here, but the item was not found.");
            return;
        }

        // avoid duplicates if somehow already in room or inventory
        if (room.getItemById(dropItem.getId()) != null) {
            return;
        }
        if (player.getInventoryItemById(dropItem.getId()) != null) {
            return;
        }

        // add the item into the room so EXPLORE can see it
        room.addItem(dropItem);
        System.out.println(monster.getName() + " dropped " + dropItem.getName() + ".");
    }

    // lets the player manually engage a monster in the room
    private void handleEngageCombat(Room room) {
        Monster mon = room.getMonster();

        if (mon == null || !mon.isAlive() || mon.isGoneForever()) {
            System.out.println("There is no monster to attack.");
            return;
        }

        if (inCombat && currentMonster == mon && mon.isAlive()) {
            System.out.println("You are already in combat with " + mon.getName() + ".");
            showCombatMovePrompt();
            return;
        }

        inCombat = true;
        currentMonster = mon;
        canRunThisCombat = true;

        System.out.println("You engage the " + mon.getName() + " in combat.");
        showCombatStatus();
        showCombatMovePrompt();
    }

    // handles attacking a monster
    private void handleAttack(Room room) {
        if (!inCombat || currentMonster == null || !currentMonster.isAlive()) {
            System.out.println("There is nothing to attack.");
            return;
        }

        // after the player chooses to attack, running is no longer allowed
        canRunThisCombat = false;

        int dmg = player.getCurrentAttack();
        currentMonster.takeDamage(dmg);

        System.out.println("You hit " + currentMonster.getName() + " for " + dmg);
        showCombatStatus();

        if (!currentMonster.isAlive()) {
            System.out.println("You defeated " + currentMonster.getName());

            // handle any item drop from this monster
            handleMonsterDrop(room, currentMonster);

            currentMonster.markDefeated();
            inCombat = false;
            currentMonster = null;
            return;
        }

        // the monster only gets a turn after ATTACK or USE
        applyMonsterAttack(currentMonster);
    }

    // handles using an item
    private void handleUse(String name, Room room) {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("You must type USE followed by an item name.");
            return;
        }

        String lowered = name.toLowerCase();

        // special non consumable tools and keys are handled here
        if (lowered.equals("flashlight") || lowered.equals("i3")) {
            handleFlashlightUse(room);
            return;
        }

        if (lowered.contains("keycard") || lowered.equals("i4")) {
            handleKeycardUse(room);
            return;
        }

        // special handling for oxygen canister through OxygenSystem
        if (lowered.equals("oxygen canister")) {
            if (player.getInventoryItem("Oxygen Canister") == null) {
                System.out.println("You do not have an Oxygen Canister.");
                return;
            }
            player.removeFromInventory("Oxygen Canister");
            oxygenSystem.restoreOxygen(50);
            System.out.println("You breathe deeply as the canister restores your oxygen.");
            return;
        }

        // everything else goes through the player useItem method
        Monster target = inCombat ? currentMonster : null;

        boolean used = player.useItem(name, target);

        // using an item in combat lets the monster respond
        if (inCombat && used && currentMonster != null && currentMonster.isAlive()) {
            applyMonsterAttack(currentMonster);
        }
    }

    // handles running from combat
    private void handleRun() {
        if (!inCombat || currentMonster == null) {
            System.out.println("There is nothing to run from.");
            return;
        }

        if (!canRunThisCombat) {
            System.out.println("You cannot run anymore.");
            return;
        }

        System.out.println("You escaped from " + currentMonster.getName());
        inCombat = false;
        currentMonster = null;
        canRunThisCombat = false;

        showCurrentRoom();
    }

    // asks the player which save slot to use
    private int askForSlot(String actionWord) {
        System.out.println("Choose a slot to " + actionWord + " (1, 2, or 3).");
        System.out.println("Type CANCEL to go back.");
        while (true) {
            System.out.print("Slot number: ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("cancel")) {
                return -1;
            }
            try {
                int slot = Integer.parseInt(line);
                if (slot >= 1 && slot <= 3) {
                    return slot;
                }
            } catch (NumberFormatException ex) {
                // ignore and reprompt
            }
            System.out.println("Invalid slot. Enter 1, 2, or 3, or CANCEL.");
        }
    }

    // saves the current game into a chosen slot
    private void handleSaveGame() {
        if (inCombat) {
            System.out.println("You cannot save during combat.");
            return;
        }
        int slot = askForSlot("save");
        if (slot == -1) {
            System.out.println("Save cancelled.");
            return;
        }

        SaveGameManager.saveGameToSlot(
                slot,
                player,
                oxygenSystem,
                rooms,
                monsters,
                puzzles,
                darkRoomHiddenDoorUnlocked,
                cargoTunnelUnlocked,
                escapePodDoorArmed
        );
    }

    // reapplies map changes based on story flags after loading
    private void applyStoryFlagsToMapAfterLoad() {

        if (darkRoomHiddenDoorUnlocked) {
            Room darkRoom = map.getRoom(2);
            Room cafeteria = map.getRoom(7);
            if (darkRoom != null && cafeteria != null) {
                if (!darkRoom.hasExit("NORTH")) {
                    darkRoom.addExit("NORTH:7");
                }
                if (!cafeteria.hasExit("SOUTH")) {
                    cafeteria.addExit("SOUTH:2");
                }
            }
        }

        if (cargoTunnelUnlocked) {
            Room vacuum = map.getRoom(5);
            Room cargo = map.getRoom(12);
            if (vacuum != null && cargo != null) {
                if (!vacuum.hasExit("EAST")) {
                    vacuum.addExit("EAST:12");
                }
                if (!cargo.hasExit("WEST")) {
                    cargo.addExit("WEST:5");
                }
            }
        }

        // escapePodDoorArmed does not add exits
        // it is checked when moving east from room 19
    }

    // applies a loaded snapshot to the current world state
    private void applySnapshotToWorld(SaveGameManager.SaveSnapshot snapshot) {
        if (snapshot == null) {
            return;
        }

        // player position
        player.setCurrentRoom(snapshot.playerRoomId);

        // oxygen level using existing methods
        int currentOxy = oxygenSystem.getOxygenLevel();
        if (snapshot.oxygenLevel > currentOxy) {
            oxygenSystem.restoreOxygen(snapshot.oxygenLevel - currentOxy);
        } else if (snapshot.oxygenLevel < currentOxy) {
            oxygenSystem.decreaseOxygenBy(currentOxy - snapshot.oxygenLevel);
        }

        // reset and mark puzzles
        for (Puzzle p : puzzles) {
            p.resetPuzzle();
            if (snapshot.solvedPuzzleIds.contains(p.getId())) {
                p.markSolvedFromLoad();
            }
        }

        // mark defeated monsters by name
        for (Monster m : monsters) {
            if (snapshot.defeatedMonsterNames.contains(m.getName())) {
                m.markDefeated();
            }
        }

        // restore flags and map changes
        darkRoomHiddenDoorUnlocked = snapshot.darkRoomHiddenDoorUnlocked;
        cargoTunnelUnlocked = snapshot.cargoTunnelUnlocked;
        escapePodDoorArmed = snapshot.escapePodDoorArmed;

        applyStoryFlagsToMapAfterLoad();
    }

    // loads a game from a chosen slot
    private void handleLoadGame() {
        if (inCombat) {
            System.out.println("You cannot load during combat.");
            return;
        }

        int slot = askForSlot("load");
        if (slot == -1) {
            System.out.println("Load cancelled.");
            return;
        }

        SaveGameManager.SaveSnapshot snapshot = SaveGameManager.loadSnapshotFromSlot(slot);
        if (snapshot == null) {
            return;
        }

        applySnapshotToWorld(snapshot);
        System.out.println("Game state loaded.");
        showCurrentRoom();
        checkForMonsterEncounter();
    }

    // main game loop
    public void startGame() {

        showCurrentRoom();
        checkForMonsterEncounter();

        while (isRunning) {

            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            // puzzle mode overrides everything else
            if (inPuzzleMode && activePuzzle != null) {

                boolean finished = activePuzzle.handleInput(input);

                if (finished) {
                    boolean solvedNow = activePuzzle.isSolved();

                    inPuzzleMode = false;

                    if (solvedNow) {
                        System.out.println("Puzzle solved.");
                        applyPuzzleRewards(activePuzzle);
                    } else {
                        System.out.println("You step away from the puzzle.");
                    }

                    activePuzzle = null;
                    showCurrentRoom();
                    checkForMonsterEncounter();
                }

                continue;
            }

            // split command and argument
            String lower = input.toLowerCase();
            String command = lower;
            String arg = "";

            int space = lower.indexOf(" ");
            if (space != -1) {
                command = lower.substring(0, space);
                arg = lower.substring(space + 1).trim();
            }

            Room room = map.getRoom(player.getCurrentRoom());
            String dir = normalizeDirection(lower);

            // exit game
            if (command.equals("exit") || command.equals("quit")) {
                System.out.println("Goodbye.");
                isRunning = false;
                continue;
            }

            // COMBAT MODE
            if (inCombat) {
                if (command.equals("attack")) {
                    handleAttack(room);
                }
                else if (command.equals("run")) {
                    handleRun();
                }
                else if (command.equals("use")) {
                    handleUse(arg, room);
                }
                else if (command.equals("equip")) {
                    player.equip(arg);
                }
                else if (command.equals("unequip")) {
                    player.unEquip();
                }
                else if (command.equals("pickup")) {
                    handlePickup(arg, room);
                }
                else if (command.equals("drop")) {
                    handleDrop(arg, room);
                }
                else if (command.equals("inspect") && arg.equals("monster")) {
                    handleInspectMonster(room);
                }
                else if (command.equals("inspect")) {
                    handleInspectItem(arg, room);
                }
                else if (command.equals("inventory")) {
                    System.out.println(player.inventoryLine());
                }
                else if (command.equals("stats")) {
                    player.showStats(oxygenSystem.getOxygenLevel());
                }
                else {
                    System.out.println("You are in combat. Use combat commands only.");
                }

                // after a combat command, if combat is still happening, show the move prompt
                if (inCombat && isRunning && oxygenSystem.getOxygenLevel() > 0 && player.isAlive()) {
                    showCombatMovePrompt();
                }
            }

            // NORMAL MODE
            else {

                // allow ATTACK or ATTACK MONSTER in normal mode to start combat
                if (command.equals("attack") || lower.equals("attack monster")) {
                    handleEngageCombat(room);
                    continue;
                }

                if (command.equals("look")) {
                    view.showLookDescription(room, map);
                }
                else if (command.equals("explore")) {
                    view.showExplore(room);
                }
                else if (command.equals("save")) {
                    handleSaveGame();
                }
                else if (command.equals("load")) {
                    handleLoadGame();
                }
                else if (dir.equals("north") || dir.equals("south") || dir.equals("east") || dir.equals("west")) {

                    // special lock for the escape pod door
                    if (player.getCurrentRoom() == 19 && dir.equals("east") && !escapePodDoorArmed) {
                        System.out.println("The escape pod door is locked tight.");
                        System.out.println("You probably need to use a keycard on the scanner first.");
                    }
                    else {
                        if (player.move(dir, map)) {
                            oxygenSystem.decreaseOxygenBy(5);
                            showCurrentRoom();
                            checkForMonsterEncounter();
                        } else {
                            System.out.println("You cannot go that way.");
                        }
                    }
                }
                else if (command.equals("pickup")) {
                    handlePickup(arg, room);
                }
                else if (command.equals("drop")) {
                    handleDrop(arg, room);
                }
                else if (command.equals("inspect")) {

                    if (arg.equals("monster")) {
                        handleInspectMonster(room);
                    }
                    else if (arg.equals("puzzle")) {
                        handleInspectPuzzle(room);
                    }
                    else {
                        handleInspectItem(arg, room);
                    }
                }
                else if (command.equals("use")) {
                    handleUse(arg, room);
                }
                else if (command.equals("solve")) {

                    Puzzle p = room.getPuzzle();

                    if (p == null) {
                        System.out.println("There is no unsolved puzzle here.");
                    }
                    else {
                        activePuzzle = p;
                        inPuzzleMode = true;

                        System.out.println("You begin working on the puzzle.");
                        p.showPuzzleIntro();
                        System.out.println("Type your answer, HINT, or EXIT.");
                    }
                }
                else if (command.equals("inventory")) {
                    System.out.println(player.inventoryLine());
                }
                else if (command.equals("oxygen")) {
                    oxygenSystem.showOxygenStatus();
                }
                else if (command.equals("stats")) {
                    player.showStats(oxygenSystem.getOxygenLevel());
                }
                else if (command.equals("help")) {
                    view.showHelp();
                }
                else {
                    System.out.println("Unknown command.");
                }
            }

            // oxygen death check
            if (oxygenSystem.getOxygenLevel() <= 0) {
                System.out.println("You ran out of oxygen.");
                isRunning = false;
            }

            // player death check
            if (!player.isAlive()) {
                System.out.println("You have died.");
                isRunning = false;
            }
        }
    }
}