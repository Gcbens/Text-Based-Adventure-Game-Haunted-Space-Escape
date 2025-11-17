========== Welcome to Haunted Space Escape ==========

This program lets you play a text adventure set on a damaged and haunted spacecraft. You explore scorched corridors, dark maintenance shafts, engine rooms, abandoned labs, and more. Everything in your game world loads from text files named Rooms.txt, Items.txt, Monsters.txt, and Puzzles.txt.

You wake up strapped in a crash pod with a warning that oxygen is limited and the environment cannot be trusted. Monsters can appear, puzzles block progress, and rooms unlock new paths through story events.

This is a simple console adventure built to be readable and beginner friendly.

========== How to Start the Game ==========

Place these files in your project folder:

Main.java
GameController.java
Map.java
Room.java
Player.java
Item.java
Puzzle.java
Monster.java
OxygenSystem.java
GameView.java

Plus the text files:

Rooms.txt
Items.txt
Monsters.txt
Puzzles.txt

To compile the program, type:

javac Main.java


To run the game, type:

java Main


All data loads automatically.

========== Map File Format (Rooms.txt) ==========

Each line defines one room with this pattern:

RoomID | Name | Description | Exit1 | Exit2 | Exit3 | Exit4

Example:

1|Crash Pod Chamber|A scorched metal pod with flickering lights.|NORTH:2|EAST:3


Exits follow this format:

DIRECTION:RoomID


Examples:

NORTH:5
EAST:3
WEST:7

Rooms can have zero, one, or many exits.

========== Map  ==========
                                                    Compass
                                                      N
                                                      ^
                                                  W <-+-> E
                                                      v
                                                      S



====================================================
                   DECK A
====================================================

                           _________________________________
                           |                                 |
                           |        Dark Room [2]            |
                           |        No monster               |
                           |        (Requires flashlight)     |
                           |_________________________________|
                                        ^
                                        |
       _________________________________|_______________________________
       |                                                                 |
       |                   Crash Pod Chamber [1]                         |
       |                   Starting Area                                 |
       |_________________________________________________________________|
                              |                      ^
                              |                      |
                              v                      |
       _________________________________             |
       |                                 |           |
       |        Utility Closet [3]       |           |
       |        (K) Flashlight           |           |
       |_________________________________|           |


====================================================
                   DECK B
====================================================

                           _____________________________________________
                           |                                             |
                           |                  Lab [4]                    |
                           |                  (M) Monster                |
                           |_____________________________________________|
                                      ^                     |
                                      |                     |
                    __________________|__________           |
                    |                             |         |
                    |  Constellation Room [6]     |         |
                    |  (P) Camera Code Puzzle     |         |
                    |_____________________________|         v
                                                   _______________________
                                                   |                      |
                                                   |    Vacuum Room [5]   |
                                                   |    (M) Monster       |
                                                   |______________________|


====================================================
                   DECK C
====================================================

       _________________________________________________________________
       |                                                                |
       |                   Haunted Cafeteria [7]                        |
       |                   (M) Monster                                  |
       |________________________________________________________________|
                              |
                              v
       _________________________________________________________________
       |                                                                |
       |                   Toxic Infirmary [8]                          |
       |                   (M) Monster                                  |
       |________________________________________________________________|
                              |
                              v
       _________________________________________________________________
       |                                                                |
       |                 Alien Cosmetic Lab [9]                         |
       |                 (M) Monster                                    |
       |________________________________________________________________|
                              |
                              v
       _________________________________________________________________
       |                                                                |
       |                     Cryochamber [10]                           |
       |                     (M) Monster                                |
       |________________________________________________________________|


====================================================
                   DECK D
====================================================

                     ____________________________________________
                     |                                            |
                     |             Observation Deck [11]          |
                     |             (M) Monster                    |
                     |____________________________________________|
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |                Cargo Bay [12]              |
                     |                (M) Monster                 |
                     |____________________________________________|
                                |
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |           Arcade Rec Room [13]             |
                     |           (P) Digital Puzzle               |
                     |____________________________________________|
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |               Reactor Room [14]            |
                     |               (M) Monster                  |
                     |____________________________________________|
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |                 Armory [15]                |
                     |                 (K) Keycard                |
                     |____________________________________________|
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |               Engine Room [16]             |
                     |               (M) Monster                  |
                     |____________________________________________|
                                |
                                v
                     ____________________________________________
                     |                                            |
                     |           Engine Supply Room [17]          |
                     |           (M) Monster                      |
                     |____________________________________________|


====================================================
                   CREW DECK
====================================================

                                ____________________________________
                                |                                    |
                                |            Crew Cabin [18]         |
                                |            (P) Riddle Puzzle       |
                                |____________________________________|
                                               |
                                               v
                                ____________________________________
                                |                                    |
                                |        Echoing Hallway [19]       |
                                |        (M) Monster Encounter      |
                                |____________________________________|
                                               |
                                               v
                                ____________________________________
                                |                                    |
                                |            Escape Pod [20]        |
                                |            Final Exit             |
                                |____________________________________|


====================================================
                   ICON LEGEND
====================================================

(M)   Monster
(P)   Puzzle
(K)   Key item



Paths can change based on puzzle solutions or keycard usage.

Flashlight reveals a hidden door in the Dark Room.
The Bloodstained Keycard unlocks access from the Engine Room and arms the escape pod door.
Solving the Camera Code Retrieval puzzle opens a tunnel between Vacuum Room and Cargo Bay.

========== Item File Format (Items.txt) ==========

Each line defines an item using this pattern:

ItemID | Name | Description | RoomID | Category | Value

Example:

I3|Flashlight|Helps you see in the dark.|3|TOOL|0
I4|Bloodstained Keycard|Opens restricted doors on the ship.|16|KEY|0


Category tells what the item is used for:

WEAPON items increase your attack when equipped.
CONSUMABLE items restore oxygen or health if added later.
TOOL items like the flashlight unlock secrets.
KEY items unlock doors and progress story events.

Value controls damage, healing, or item effects depending on category.

========== Puzzle File Format (Puzzles.txt) ==========

Each line defines a puzzle using this pattern:

PuzzleID | Name | Description | CorrectAnswer | RoomID | Attempts | PassMessage | FailMessage

Example:

P1|Camera Code Retrieval|A scrambled surveillance code must be solved.|4132|5|3|The lock pops open.|Access denied.


Solving puzzles can:

Unlock hallways
Open hidden tunnels
Remove barriers
Trigger story flags

After a puzzle is solved it disappears forever.

========== Monster File Format (Monsters.txt) ==========

Each monster is defined with this pattern:

MonsterID | Name | Description | HP | BaseAttack | Threshold | RoomID

Example:

M3|Mutated Angerfish|A glowing creature triggered by light.|70|12|20|6


Monsters have these features:

They attack with random rolls
They deal extra damage if the roll is below their threshold
They drop items if designed to do so
They disappear after defeat

If they are labeled as gone forever they never respawn.

========== Save and Load System ==========

The game supports three save slots.

To save:

save


To load:

load


If a slot is empty the game shows:

There is no saved game in this slot.


Corrupted or missing room data also prints a message instead of crashing.

========== Gameplay Instructions ==========

You start in the Crash Pod Chamber. The game prints the room name and description, then waits for your command.

Movement Commands:

N or NORTH
E or EAST
S or SOUTH
W or WEST

Exploration Commands:

LOOK
EXPLORE

Item Commands:

PICKUP <name>
DROP <name>
INSPECT <name>
INVENTORY
EQUIP <weapon>
UNEQUIP

Puzzle Commands:

SOLVE
INSPECT PUZZLE
HINT or EXIT during a puzzle

Combat Commands:

ATTACK
RUN
USE <item>
INSPECT MONSTER

Player Commands:

STATS
OXYGEN
HELP
QUIT

========== Monster Interaction ==========

When you enter a room a monster may appear.

EXAMINE MONSTER shows its details.
ATTACK starts combat.
RUN escapes if allowed.
USE <item> applies special effects like the flashlight against the Angerfish.

Monsters disappear permanently when defeated or if designed to be gone forever.

========== Combat System ==========

Combat alternates between your turn and the monster’s turn.

You can:

Attack
Use an item
Equip something
Inspect the monster

The monster attacks after your turn unless it dies.

If you win the monster is marked defeated and stays gone.
If you lose the game ends.

========== Story Events and Hidden Interactions ==========

Flashlight in Dark Room reveals a hidden door.
Keycard in Engine Room unlocks a lower hallway.
Keycard in Echoing Hallway arms the escape pod door.
Camera Code Retrieval puzzle opens a tunnel from Vacuum Room to Cargo Bay.

These events persist in saved games.

========== Example Scenarios ==========
> explore
You sweep the room and spot a Flashlight on a crate.

> pickup flashlight
Flashlight added to your inventory.

> use flashlight
You turn on the flashlight. It helps you see.


Monster Example:

A Mutated Angerfish appears.
It attacked for 12.
Your move


Puzzle Example:

> solve
You begin working on the puzzle.
Type your answer, HINT, or EXIT.

========== Notes ==========

Rooms mark themselves as visited.
Items move between rooms and inventory naturally.
Puzzles, monsters, and story flags persist across saves.
Rooms update their exits when story flags unlock them.
Save slots never crash even if empty or corrupted.
========== AI Usage ==========
I used ClaudeCode/ChatGPT to help with:

→ Formatting this README for readability and documentation completeness
→ Formatting text files and improving file structure
→ Creating the map
→ Clarifying assignment requirements for readability