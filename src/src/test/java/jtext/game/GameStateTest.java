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
public class GameStateTest extends jtext.AbstractGameStateTest {

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
    @Test
    public void testUse() {
        gameState.getLocation().getUse().apply(gameState);
        assertEquals("state has not changed to " + alteredState, alteredState,
                gameState.getGame().findEntityById(startLocationId).getState());
    }

}