package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
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
            gameState.display("I can't seem to find %s", parameter);
        }
    }

}
