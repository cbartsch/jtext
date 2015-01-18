package jtext.game;

import jtext.action.Action;
import jtext.action.StateAction;
import jtext.action.WinAction;
import jtext.command.Command;
import jtext.command.ItemCommand;
import jtext.condition.ItemCondition;
import jtext.entity.Item;
import jtext.entity.Location;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests game state and commands.
 * Creates a game manually (not from JSON) and checks if the command's are working correctly.
 */
public class GameStateTest {

    private GameState gameState;
    private String startLocationId = "start_location";
    private String startText = "start_text";
    private String secondLocationId = "second_location";
    private String useItemId = "use_item";
    private String inventoryItem = "inventory_item";
    private String initialState = "initial_state";
    private String alteredState = "altered_state";
    private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    private PrintStream out = new PrintStream(outputBuffer);
    private String lookText = "activated look command";
    private String useText;
    private String takeText;
    private String useItemText;

    @Before
    public void setup() {
        Map<String, Location> locations = new HashMap<>();

        Map<String, Item> objects = new HashMap<>();
        objects.put(useItemId, new Item(true, true, initialState, null, null, null, null));

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

    @Test
    public void testSimple() {
        assertNotNull("Game state was null", gameState);
        Location startLocation = gameState.getGame().findLocationById(startLocationId);
        testEmptyCommand(startLocation.getLook());
        testEmptyCommand(startLocation.getTake());
        testEmptyCommand(startLocation.getUse());
        testEmptyCommand(startLocation.getUseWith());
        assertOutput(String.format("%s%n%s%n%s%n%s%n", lookText, takeText, useText, useItemText));
    }

    private void testEmptyCommand(Command command) {
        for(Action action : command.getActions()) {
            assertTrue("TargetId should be initialized", action.getTargetIdCount() > 0);
            assertEquals("TargetId should be start_location", startLocationId, action.getTargetIds().iterator().next());
        }
        command.apply(gameState);
    }

    @Test
    public void testLook() {
        gameState.getLocation().getLook().apply(gameState);
        assertOutput(String.format("%s%n", lookText));
    }

    private void assertOutput(String output) {
        assertEquals("output was wrong", output, outputBuffer.toString());
    }
}