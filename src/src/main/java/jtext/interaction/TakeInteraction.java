package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.GameState;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class TakeInteraction extends Interaction {
    public static final Interaction INSTANCE = new TakeInteraction();

    private TakeInteraction() { }

    @Override
    public void apply(String parameter, GameState gameState) {
        Location loc = gameState.getLocation();
        String itemId = parameter; // TODO Extract real item ID
        BaseEntity item = findItem(itemId, loc);
        if(item != null && item.isVisible()) {
            if(item.isEnabled()) {
                item.getTake().apply(gameState);
            } else {
                gameState.display("I cannot pick up %s", itemId);
            }
        } else {
            gameState.display("I can't seem to find %s", itemId);
        }
    }
}
