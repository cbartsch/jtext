package jtext.interaction;

import jtext.game.Game;
import jtext.game.GameState;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class InteractionManager {
    public static final String WORD_SEPARATOR = " ";
    private final Map<String, Interaction> actions;

    private final GameState gameState;


    public InteractionManager(GameState gameState) {
        this.gameState = gameState;
        actions = new HashMap<>();
        actions.put("look", LookInteraction.INSTANCE);
        actions.put("use", UseInteraction.INSTANCE);
        actions.put("take", TakeInteraction.INSTANCE);
        actions.put("pick", TakeInteraction.INSTANCE);
        actions.put("go", GoInteraction.INSTANCE);
    }

    public void start() {
        gameState.display(gameState.getGame().getStartText());
    }

    public void applyCommand(String command) {
        int index = command.indexOf(WORD_SEPARATOR);
        if(index >= 0) {
            String commandName = command.substring(0, index);
            String parameter = command.substring(index + 1);
            Interaction interaction = actions.get(commandName);
            if (interaction != null) {
                interaction.apply(parameter, gameState);
            } else {
                // TODO Search for a command that the user might have wanted to use
                gameState.display("Huh?");
            }
        } else {
            // TODO Display the command help
            gameState.display("Huh?");
        }
    }
}
