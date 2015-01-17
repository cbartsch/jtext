package jtext.interaction;

import com.google.common.base.Joiner;
import jtext.game.GameState;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Chrisu on 17/01/2015.
 */
public class HelpInteraction extends Interaction {
    private final InteractionManager interactionManager;

    public HelpInteraction(InteractionManager interactionManager, String ... ignoredPhrases) {
        super(ignoredPhrases);
        this.interactionManager = interactionManager;
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        gameState.display("Here is a list of all possible commands:");
        for (Map.Entry<String, Interaction> interactionEntry : interactionManager.listCommands()) {
            if(!(interactionEntry.getValue() instanceof HelpInteraction)) {
                gameState.display("%s %s", interactionEntry.getKey(), Joiner.on("/").join(interactionEntry.getValue().ignoredPhrases));
            }
        }
    }
}
