package jtext.interaction;

import com.google.common.base.Joiner;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.stream.Collectors;

/**
 * Created by Chrisu on 17/01/2015.
 */
public class InventoryInteraction extends Interaction {
    public InventoryInteraction(String... ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        if (gameState.getInventorySize() == 0) {
            gameState.display("I carry nothing with me.");
        } else {
            gameState.display("I carry the following items:");
            gameState.display(gameState.streamInventoryItems()
                    .map(BaseEntity::getId)
                    .collect(Collectors.joining("," + InteractionManager.WORD_SEPARATOR)));
        }
    }
}
