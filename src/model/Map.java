//created by Keyauna Fuller 



import java.util.HashMap;
import java.util.Map;

public class GameMap {

    private final Map<Integer, Room> rooms = new HashMap<>();

    public GameMap() {
        initializeRooms();
        connectRooms();
    }

    private void initializeRooms() {
        rooms.put(1, new Room(1, "Hunters Guild Hall",
                "The bustling headquarters where hunters gather before raids. Wooden notice boards overflow with dungeon postings."));
        rooms.put(2, new Room(2, "Tutorial Dungeon Entrance",
                "A damp stone stairwell descends into shadow. Strange markings crawl across the walls, pulsing faint blue."));
        rooms.put(3, new Room(3, "Dungeon Lobby",
                "A circular chamber lined with glowing crystals. Adventurers use this as a staging ground."));
        rooms.put(4, new Room(4, "Statue Chamber",
                "Gigantic godlike statues loom in eternal judgment. Their stone eyes follow your every move."));
        rooms.put(5, new Room(5, "Ruined City Street",
                "Shattered skyscrapers pierce the sky like jagged teeth. Cars lie rusting, abandoned mid-flight from disaster."));
        rooms.put(6, new Room(6, "Double Dungeon Altar",
                "A massive altar engraved with runes. Dried blood stains the floor. The rules of survival are etched in stone: “Worship the gods.”"));
        rooms.put(7, new Room(7, "Gate Plaza",
                "A glowing rift shimmers in the air. Hunters mill nervously nearby."));
        rooms.put(8, new Room(8, "Cursed Statue Throne",
                "A towering statue sits upon a throne, holding a severed head. Its gaze demands obedience."));
        rooms.put(9, new Room(9, "Orc Encampment",
                "Fires burn in cracked barrels. The stench of roasted meat and unwashed bodies fills the night."));
        rooms.put(10, new Room(10, "Fire Chamber",
                "A cavern of lava rivers and scorched stone. Pyros towers here, molten cracks glowing across his armor."));
        rooms.put(11, new Room(11, "Water Cavern",
                "A shimmering cave of coral and flooding chambers. Neressa rises, cloaked in flowing water."));
        rooms.put(12, new Room(12, "Earth Fortress",
                "A titanic stone hall with shifting walls. Gravemaw stomps, creating earthquakes and stone spikes."));
        rooms.put(13, new Room(13, "Air Spire",
                "A tower of sky bridges and roaring winds. Zephyra floats above, summoning cyclones and lightning strikes."));
        rooms.put(14, new Room(14, "Demon Castle Foyer",
                "Immense gates open into a crimson-lit hall. Demonic runes glow on towering pillars."));
        rooms.put(15, new Room(15, "Demon Castle Library",
                "Thousands of cursed tomes float mid-air, pages turning by themselves."));
        rooms.put(16, new Room(16, "Demon Castle Throne Room",
                "A long red carpet leads to a massive black throne. Claws have shredded the walls."));
        rooms.put(17, new Room(17, "Monarch’s Chamber",
                "The oppressive presence of a Monarch fills the room. Cracks of black lightning scorch the marble floor."));
        rooms.put(18, new Room(18, "Dimensional Rift",
                "A swirling vortex of space and time. Gravity bends oddly here."));
        rooms.put(19, new Room(19, "Hunters Association Office",
                "Suits and uniforms bustle about, papers stacked high. Giant holographic maps flicker overhead."));
        rooms.put(20, new Room(20, "Eternal Battlefield",
                "Swords and shields from countless ages litter the field. Spirits of warriors wander aimlessly."));
        rooms.put(21, new Room(21, "Hospital Ward",
                "Rows of hunters lie unconscious. Nurses rush between beds."));
        rooms.put(22, new Room(22, "Hunter Exam Arena",
                "Stone coliseum seats tower overhead. Spectators roar as new hunters prove themselves."));
        rooms.put(23, new Room(23, "Dungeon Boss Chamber",
                "Bones crunch underfoot. A monstrous roar echoes. The boss lurks just beyond the shadows."));
        rooms.put(24, new Room(24, "Shadow Army Plains",
                "Rolling plains stretch endlessly. A black mist rises from the soil."));
        rooms.put(25, new Room(25, "High Orc Fortress",
                "Heavy wooden gates bar the path. Drums thunder inside."));
        rooms.put(26, new Room(26, "Hunters’ Cafeteria",
                "Hunters chat over coffee and ramen. Laughter briefly masks the dread of their next raid."));
        rooms.put(27, new Room(27, "Blacksmith’s Forge",
                "Sparks fly as weapons are reforged. The smell of molten metal fills the room."));
        rooms.put(28, new Room(28, "Monarch’s Throne",
                "The throne of destruction towers, larger than life. The Monarch of Shadows gazes upon the battlefield."));
        rooms.put(29, new Room(29, "Monarch’s Throne of Darkness",
                "Darkness itself gathers like liquid. The shadows bow to you here."));
        rooms.put(30, new Room(30, "Final Gate",
                "A colossal rift glowing brighter than the sun. Beyond it lies the endgame — the fate of hunters, Monarchs, and the world itself."));
    }

    private void connectRooms() {
        rooms.get(1).addExit("north", rooms.get(2));
        rooms.get(1).addExit("east", rooms.get(3));
        rooms.get(2).addExit("south", rooms.get(1));
        rooms.get(2).addExit("north", rooms.get(4));
        rooms.get(3).addExit("west", rooms.get(1));
        rooms.get(3).addExit("east", rooms.get(5));
        rooms.get(4).addExit("south", rooms.get(2));
        rooms.get(4).addExit("north", rooms.get(6));
        rooms.get(5).addExit("west", rooms.get(3));
        rooms.get(5).addExit("east", rooms.get(7));
        rooms.get(6).addExit("south", rooms.get(4));
        rooms.get(6).addExit("north", rooms.get(8));
        rooms.get(7).addExit("west", rooms.get(5));
        rooms.get(7).addExit("east", rooms.get(9));
        rooms.get(8).addExit("south", rooms.get(6));
        rooms.get(8).addExit("north", rooms.get(10));
        rooms.get(9).addExit("west", rooms.get(7));
        rooms.get(9).addExit("east", rooms.get(11));
        rooms.get(10).addExit("south", rooms.get(8));
        rooms.get(10).addExit("north", rooms.get(11));
        rooms.get(11).addExit("south", rooms.get(10));
        rooms.get(11).addExit("north", rooms.get(12));
        rooms.get(12).addExit("south", rooms.get(11));
        rooms.get(12).addExit("north", rooms.get(13));
        rooms.get(13).addExit("south", rooms.get(12));
        rooms.get(13).addExit("north", rooms.get(14));
        rooms.get(14).addExit("south", rooms.get(13));
        rooms.get(14).addExit("north", rooms.get(15));
        rooms.get(15).addExit("south", rooms.get(14));
        rooms.get(15).addExit("north", rooms.get(16));
        rooms.get(16).addExit("south", rooms.get(15));
        rooms.get(16).addExit("north", rooms.get(17));
        rooms.get(17).addExit("south", rooms.get(16));
        rooms.get(17).addExit("north", rooms.get(18));
        rooms.get(18).addExit("south", rooms.get(17));
        rooms.get(18).addExit("north", rooms.get(19));
        rooms.get(19).addExit("south", rooms.get(18));
        rooms.get(19).addExit("north", rooms.get(20));
        rooms.get(20).addExit("south", rooms.get(19));
        rooms.get(20).addExit("north", rooms.get(21));
        rooms.get(21).addExit("south", rooms.get(20));
        rooms.get(21).addExit("north", rooms.get(22));
        rooms.get(22).addExit("south", rooms.get(21));
        rooms.get(22).addExit("north", rooms.get(23));
        rooms.get(23).addExit("south", rooms.get(22));
        rooms.get(23).addExit("north", rooms.get(24));
        rooms.get(24).addExit("south", rooms.get(23));
        rooms.get(24).addExit("north", rooms.get(25));
        rooms.get(25).addExit("south", rooms.get(24));
        rooms.get(25).addExit("north", rooms.get(26));
        rooms.get(26).addExit("south", rooms.get(25));
        rooms.get(26).addExit("north", rooms.get(27));
        rooms.get(27).addExit("south", rooms.get(26));
        rooms.get(27).addExit("north", rooms.get(28));
        rooms.get(28).addExit("south", rooms.get(27));
        rooms.get(28).addExit("north", rooms.get(29));
        rooms.get(29).addExit("south", rooms.get(28));
        rooms.get(29).addExit("north", rooms.get(30));
        rooms.get(30).addExit("south", rooms.get(29));
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public Room getStartingRoom() {
        return rooms.get(1);
    }
}
