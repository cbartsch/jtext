package jtext.game;

import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The game state represents the current state of a game, which a user can interact with.
 *
 * It contains a reference to the game structure, the inventory, the current location,
 * and a print stream which prints messages to the user.
 */
public class GameState {
    private Location location;
    private final Map<String, Item> inventory;
    private final Game game;
    private final PrintStream output;

    public GameState(Game game, PrintStream output) {
        this.game = game;
        this.output = output;
        this.location = game.getStartLocation();
        this.inventory = new HashMap<>();
    }

    public void display(String text, Object ... args) {
        output.printf(text + "%n", args);
    }

    public Location getLocation() {
        return location;
    }

    public Game getGame() {
        return game;
    }

    public Item findInventoryItemById(String id) {
        return inventory.get(id);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean hasInventoryItem(String id) {
        return inventory.containsKey(id);
    }

    public Item removeInventoryItem(String id) {
        return inventory.remove(id);
    }

    public void addInventoryItem(Item item) {
        inventory.put(item.getId(), item);
    }

    public int getInventorySize() {
        return inventory.size();
    }

    public Stream<Item> streamInventoryItems() {
        return inventory.values().stream();
    }
}
