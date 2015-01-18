package jtext;

import jtext.action.StateAction;
import jtext.command.Command;
import jtext.command.ItemCommand;
import jtext.condition.ItemCondition;
import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.Game;
import jtext.game.GameState;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Chrisu on 18/01/2015.
 */
public class AbstractGameStateTest {
    protected GameState gameState;
    protected String startLocationId = "start_location";
    protected String lookText = "activated look command";
    protected String useText;
    protected String takeText;
    protected String useItemText;
    protected String startText = "start_text";
    protected String secondLocationId = "second_location";
    protected String useItemId = "use_item";
    protected String inventoryItem = "inventory_item";
    protected String initialState = "initial_state";
    protected String itemLookText = "looked at item";

    protected String alteredState = "altered_state";
    protected ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    protected PrintStream out = new PrintStream(outputBuffer);

    @Before
    public void setup() {
        Map<String, Location> locations = new HashMap<>();

        Map<String, Item> objects = new HashMap<>();
        objects.put(useItemId, new Item(true, true, initialState, new Command(itemLookText, null, null), null, null, null));

        useText = "activated use command";
        takeText = "activated take command";
        useItemText = "activated use item command";
        locations.put(startLocationId, new Location(true, true, null,
                new Command(lookText,
                        null,
                        Arrays.asList(new ItemCondition("item not in inventory!", Collections.singleton(inventoryItem)))
                ),
                new Command(useText,
                        Arrays.asList(new StateAction(Arrays.asList(startLocationId, useItemId), alteredState)),
                        Arrays.asList()),
                new Command(takeText, Arrays.asList(), Arrays.asList()),
                new ItemCommand(useItemText, Arrays.asList(), Arrays.asList(), useItemId),
                objects,
                Collections.singleton(secondLocationId)));

        locations.put(secondLocationId, new Location(true, true, null, null, null, null, null, null,
                Collections.singleton(startLocationId)));

        gameState = new GameState(new Game(locations, startLocationId, startText), out);
        Item item = new Item(true, true, null, null, null, null, null);
        item.setId(inventoryItem);
        gameState.addInventoryItem(item);
    }

    protected void assertOutput(String output) {
        assertEquals("output was wrong", output, outputBuffer.toString());
    }
}
