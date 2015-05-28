package jtext.interaction;

import jtext.game.GameState;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Chrisu on 17/01/2015.
 */
public class HelpInteraction extends Interaction {
    private final InteractionManager interactionManager;

    public HelpInteraction(InteractionManager interactionManager, String... ignoredPhrases) {
        super(ignoredPhrases);
        this.interactionManager = interactionManager;
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        gameState.display("Here is a list of all possible commands:");

        interactionManager.streamCommands()
                .filter(e -> e.getValue().isVisible())                          //do not show invisible commands
                .collect(Collectors.groupingBy(e -> e.getValue().getClass()))   //group commands by type
                .values().forEach(groupedInteractions ->
                        gameState.display(groupedInteractions.stream()
                                .map(HelpInteraction::interactionText)          //show command name and ignored phrases
                                .collect(Collectors.joining(", "))              //separate commands with comma
                        )
        );
    }

    private static String interactionText(Map.Entry<String, Interaction> interaction) {
        String phrases = interaction.getValue().ignoredPhrases.stream()
                .map(Object::toString)
                .collect(Collectors.joining("/"));
        return interaction.getKey() + (phrases.length() > 0 ? " " + phrases : "");
    }
}
