package jtext.interaction;

import com.google.common.base.Strings;
import jtext.game.GameState;
import jtext.util.HammingUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Chrisu on 16/01/2015.
 * <p>
 * The interaction manager is the centerpiece of the interaction between the user and the system.
 * <p>
 * It takes input from the user, and applies it to the system by finding the appropriate interaction.
 */
public class InteractionManager {
    public static final String WORD_SEPARATOR = " ";
    private final Map<String, Interaction> commands;

    private final GameState gameState;


    public InteractionManager(GameState gameState) {
        this.gameState = gameState;
        commands = new HashMap<>();
        commands.put("look", new LookInteraction("at"));
        commands.put("inspect", new LookInteraction());
        commands.put("use", new UseInteraction());
        commands.put("take", new TakeInteraction());
        commands.put("pick", new TakeInteraction("up"));
        commands.put("go", new GoInteraction("to", "towards"));
        commands.put("walk", new GoInteraction("to"));
        commands.put("cd", new GoInteraction().setVisible(false));
        commands.put("help", new HelpInteraction(this).setVisible(false));
        commands.put("?", new HelpInteraction(this).setVisible(false));
        commands.put("inventory", new InventoryInteraction());
        commands.put("items", new InventoryInteraction());
        commands.put("ls", new InventoryInteraction().setVisible(false));
    }

    public void start() {
        gameState.display(gameState.getGame().getStartText());
    }

    public void applyCommand(String command) {
        if (!Strings.isNullOrEmpty(command)) {
            String parameter = "";
            String commandName = command;
            Interaction interaction = commands.get(commandName);
            if (interaction == null) {
                int index = command.indexOf(WORD_SEPARATOR);
                if (index >= 0) {
                    commandName = command.substring(0, index);
                    parameter = command.substring(index + 1);
                    interaction = commands.get(commandName);
                }
            }

            if (interaction != null) {
                interaction.apply(parameter, gameState);
            } else {
                String closestCommand = findClosestCommand(commandName);
                if(closestCommand != null) {
                    gameState.display("I could not understand %s, did you mean %s?", commandName, closestCommand);
                } else {
                    gameState.display("I could not understand %s.", commandName);
                }
            }
        } else {
            gameState.display("Try entering 'help' to see a list of available commands!");
        }
    }

    private String findClosestCommand(String command) {
        return HammingUtils.findClosestElement(command, commands.keySet());
    }

    public Iterable<Map.Entry<String, Interaction>> listCommands() {
        return commands.entrySet();
    }
    public Stream<Map.Entry<String, Interaction>> streamCommands() {
        return commands.entrySet().stream();
    }
}
