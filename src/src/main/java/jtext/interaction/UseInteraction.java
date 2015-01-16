package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class UseInteraction extends Interaction {

    public UseInteraction(Collection<String> ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        Location loc = gameState.getLocation();
        String itemId = parameter; // TODO Extract real item ID
        BaseEntity item = findItem(itemId, loc);
        //TODO use with
        if(item != null && item.isVisible()) {
            if(item.isEnabled()) {
                item.getUse().apply(gameState);
            } else {
                gameState.display("I cannot use %s", itemId);
            }
        } else {
            gameState.display("I can't seem to find %s", itemId);
        }
    }
}
