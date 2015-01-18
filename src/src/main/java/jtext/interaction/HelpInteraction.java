package jtext.interaction;

import com.google.common.base.Joiner;
import jtext.game.GameState;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                                .map(this::interactionText)                     //show command name and ignored phrases
                                .collect(Collectors.joining(", "))              //separate commands with comma
                        )
        );
    }

    private String interactionText(Map.Entry<String, Interaction> interaction) {
        String phrases = join("/", interaction.getValue().ignoredPhrases);
        return interaction.getKey() + (phrases.length() > 0 ? " " + phrases : "");
    }

    private String join(String delimiter, Collection<?> values) {
        return values.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }
}
