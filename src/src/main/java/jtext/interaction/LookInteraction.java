package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The look interaction calls the look command's apply() method of an object, if it is visible.
 */
public class LookInteraction extends Interaction {

    public LookInteraction(Collection<String> ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        Location loc = gameState.getLocation();
        BaseEntity item = findItem(parameter, loc);
        if(item != null && item.isVisible()) {
            item.getLook().apply(gameState);
        } else {
            gameState.displayItemNotFoundMessage(parameter);
        }
    }

}
