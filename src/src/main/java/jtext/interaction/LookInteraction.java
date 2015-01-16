package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.GameState;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class LookInteraction extends Interaction {
    public static final Interaction INSTANCE = new LookInteraction();

    private LookInteraction() { }

    @Override
    public void apply(String parameter, GameState gameState) {
        Location loc = gameState.getLocation();
        String itemId = parameter;  // TODO Extract real itemID
        BaseEntity item = findItem(itemId, loc);
        if(item != null && item.isVisible()) {
            item.getLook().apply(gameState);
        } else {
            gameState.display("I can't seem to find %s", itemId);
        }
    }

}
