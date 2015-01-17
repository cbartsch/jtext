package jtext.game;

import jtext.action.*;
import jtext.command.Command;
import jtext.command.ItemCommand;
import jtext.condition.Condition;
import jtext.condition.ItemCondition;
import jtext.condition.StateCondition;
import jtext.entity.Item;
import jtext.entity.Location;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameLoaderTest {

    private Game loadGame(String json) throws IOException {
        return GameLoader.load(new ByteArrayInputStream(
                json.getBytes(Charset.defaultCharset())
        ));
    }

    private Game loadGameFromFile(String fileName) throws IOException {
        return GameLoader.load(getClass().getResourceAsStream(fileName));
    }

    //will return no game because locations and start is not set
    @Test
    public void testEmpty() throws IOException {
        Game game = loadGame("{}");
        assertNull("Game must be null", game);
    }

    @Test
    public void testSimple() throws IOException {
        String location = "location";
        String startText = "the text";
        Game game = loadGame(String.format("{" +
                "\"start\": \"%s\"," +
                "\"start_text\": \"%s\"," +
                "\"locations\": {}" +
                "}", location, startText));
        assertNotNull("Game must not be null", game);
        assertEquals("Game start location must be set", game.getStartLocationId(), location);
        assertEquals("Game start text must be set", game.getStartText(), startText);
        assertTrue("Game locations must be empty", game.getLocations().isEmpty());
    }

    @Test
    public void testWithOneLocation() throws IOException {
        String location = "location";
        String startText = "the text";
        Game game = loadGame(String.format("{" +
                "\"start\": \"%s\"," +
                "\"start_text\": \"%s\"," +
                "\"locations\": {" +
                "\"%s\": {}" +
                "}" +
                "}", location, startText, location));

        assertNotNull("Game must not be null", game);
        assertEquals("Game start location must be set", game.getStartLocationId(), location);
        assertEquals("Game start text must be set", game.getStartText(), startText);
        assertEquals("Game locations must contain one entry", game.getLocations().size(), 1);
        Location locationEntity = game.getLocations().get(location);
        assertNotNull("Location must use the correct id for the map key", locationEntity);
        assertEquals("Location id must be set", locationEntity.getId(), location);
    }

    @Test
    public void testWithValidGameJson() throws IOException {
        Game game = loadGameFromFile("validGame.json");

        assertNotNull("Game must not be null", game);
        assertEquals("Game start location must be set", game.getStartLocationId(), "location");
        assertEquals("Game start text must be set", game.getStartText(), "Welcome");
        assertEquals("Game locations must contain two entries", game.getLocationCount(), 2);
        assertEquals("Game must contain seven entities", game.getEntityCount(), 7);

        Location otherLocation = game.findLocationById("otherLocation");
        assertNotNull("Other location must not be null", otherLocation);
        assertEquals("Other location must contain zero entities", otherLocation.getItemCount(), 0);
        assertEquals("Other location must have zero adjacent", otherLocation.getAdjacentCount(), 0);
        assertFalse("Other location must be disabled", otherLocation.isEnabled());
        assertTrue("Other location must be visible", otherLocation.isVisible());

        Location location = game.findLocationById("location");
        assertNotNull("Location must not be null", location);
        assertEquals("Location must contain five entities", location.getItemCount(), 5);
        assertEquals("Location must have one adjacent", location.getAdjacentCount(), 1);
        assertTrue("Location must be enabled", location.isEnabled());
        assertFalse("Location must be invisible", location.isVisible());

        assertEquals("State object's state must be set", "stateful",
                location.findItemById("stateObject").getState());


        Item doIfObject = location.findItemById("doIfObject");
        Command lookCommand = doIfObject.getLook();

        assertEquals("look must be instanceof Command", "You looked", lookCommand.getText());

        int i = 0;
        List<Class<? extends Action>> expectedActionTypes = Arrays.asList(
                VisibleAction.class, EnableAction.class, TakeAction.class, RemoveAction.class, StateAction.class
        );
        for (Action action : lookCommand.getActions()) {
            assertEquals("Action class was wrong", expectedActionTypes.get(i), action.getClass());
            assertEquals("Action target count was not 1", 1, action.getTargetIdCount());
            i++;
            if (action instanceof StateAction) {
                assertEquals("State action value was wrong", "targetState", ((StateAction) action).value);
            } else if (action instanceof VisibleAction) {
                assertEquals("Visible action value was wrong", false, ((VisibleAction) action).value);
            } else if (action instanceof EnableAction) {
                assertEquals("Enable action value was wrong", true, ((EnableAction) action).value);
            }
        }
        i = 0;
        List<Class<? extends Condition>> expectedConditionTypes = Arrays.asList(
                ItemCondition.class, StateCondition.class
        );
        for (Condition condition : lookCommand.getConditions()) {
            assertEquals("Condition class was wrong", expectedConditionTypes.get(i), condition.getClass());
            if (i == 1) {
                assertEquals("Action target count was not 1", "Wrong state", condition.elseText);
            }
            i++;
        }

        Item commandObject = location.findItemById("commandObject");
        ItemCommand useWithCommand = commandObject.getUseWith();
        assertNotNull("Use with command must be present", useWithCommand);
        assertEquals("Use with command item was wrong", "useItem", useWithCommand.itemId);
    }


    @Test
    public void testWithRealGameJson() throws IOException {
        Game game = loadGameFromFile("realGame.json");

        assertNotNull("Real game could not be loaded", game);
    }
}