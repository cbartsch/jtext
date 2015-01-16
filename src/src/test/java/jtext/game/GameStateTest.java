package jtext.game;

import jtext.action.WinAction;
import jtext.command.Command;
import jtext.command.ItemCommand;
import jtext.condition.ItemCondition;
import jtext.entity.Item;
import jtext.entity.Location;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GameStateTest {

    private GameState gameState;
    private String startLocationId;
    private String startText;
    private String secondLocationId;
    private String useItemId;
    private String inventoryItem;

    @Before
    public void setup() {
        Map<String, Location> locations = new HashMap<>();
        startLocationId = "start_location";
        startText = "start_text";
        secondLocationId = "second_location";
        useItemId = "use_item";
        inventoryItem = "inventory_item";

        Map<String, Item> objects = new HashMap<>();
        locations.put(startLocationId, new Location(true, true, null,
                new Command("activated look command",
                        Arrays.asList(new WinAction(null)),
                        Arrays.asList(new ItemCondition("item not in inventory!", Collections.singleton(inventoryItem)))
                ),
                new Command("activated use command", Arrays.asList(), Arrays.asList()),
                new Command("activated take command", Arrays.asList(), Arrays.asList()),
                new ItemCommand("activated use item command", Arrays.asList(), Arrays.asList(), useItemId),
                objects, Collections.singleton(secondLocationId)));
        locations.put(secondLocationId, new Location(true, true, null, null, null, null, null, null,
                Collections.singleton(startLocationId)));

        gameState = new GameState(new Game(locations, startLocationId, startText), System.out);
        Item item = new Item(true, true, null, null, null, null, null);
        item.setId(inventoryItem);
        gameState.addInventoryItem(item);
    }

    @Test
    public void testSimple() {
        assertNotNull("Game state was null", gameState);
    }

    @Test
    public void testLook() {
        gameState.getLocation().getLook().apply(gameState);
    }
}