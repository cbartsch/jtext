package jtext.interaction;

import jtext.AbstractGameStateTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the interaction manager, to see if it responds correctly to text commands.
 */
public class InteractionManagerTest extends AbstractGameStateTest {

    private InteractionManager interactionManager;

    @Override
    public void setup() {
        super.setup();

        interactionManager = new InteractionManager(gameState);
    }

    @Test
    public void testStart() throws Exception {
        interactionManager.start();
        assertOutput(String.format("%s%n", startText));
    }

    @Test
    public void testApplyCommand() throws Exception {
        interactionManager.start();

        StringBuilder expectedOutput = new StringBuilder();

        interactionManager.applyCommand("");
        String newLine = String.format("%n");
        expectedOutput.append(startText)
                .append(newLine)
                .append("Try entering 'help' to see a list of available commands!")
                .append(newLine);
        assertOutput(expectedOutput.toString());

        interactionManager.applyCommand("look");
        expectedOutput.append(lookText).append(newLine);
        assertOutput(expectedOutput.toString());

        interactionManager.applyCommand("look at " + useItemId);
        expectedOutput.append(itemLookText).append(newLine);
        assertOutput(expectedOutput.toString());

        interactionManager.applyCommand("use " + startLocationId);
        assertEquals("state was not altered by using the location", alteredState,
                gameState.getGame().findEntityById(useItemId).getState());
    }

    @Test
    public void testListCommands() throws Exception {
        final boolean[] goFound = {false};
        interactionManager.listCommands().forEach(c -> goFound[0] |= c.getKey().equals("go"));
        assertTrue("go command was not listed", goFound[0]);
    }
}