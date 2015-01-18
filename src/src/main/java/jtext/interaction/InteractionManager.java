package jtext.interaction;

import jtext.game.GameState;
import jtext.util.HammingUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The interaction manager is the centerpiece of the interaction between the user and the system.
 *
 * It takes input from the user, and applies it to the system by finding the appropriate interaction.
 */
public class InteractionManager {
    public static final String WORD_SEPARATOR = " ";
    private final Map<String, Interaction> actions;

    private final GameState gameState;


    public InteractionManager(GameState gameState) {
        this.gameState = gameState;
        actions = new HashMap<>();
        actions.put("look", new LookInteraction(Arrays.asList("at")));
        actions.put("inspect", new LookInteraction(Collections.emptyList()));
        actions.put("use", new UseInteraction(Collections.emptyList()));
        actions.put("take", new TakeInteraction(Collections.emptyList()));
        actions.put("pick", new TakeInteraction(Arrays.asList("up")));
        actions.put("go", new GoInteraction(Arrays.asList("to")));
        actions.put("walk", new GoInteraction(Arrays.asList("to")));
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
                gameState.display("I could not understand %s, did you mean %s?", commandName, findClosestCommand(commandName));
            }
        } else {
            // TODO Display the command help
            gameState.display("Huh?");
        }
    }

    private String findClosestCommand(String command) {
        return HammingUtils.findClosestElement(command, actions.keySet());
    }
}
