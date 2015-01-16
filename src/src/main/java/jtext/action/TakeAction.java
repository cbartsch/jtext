package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.logging.Logger;
import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 * <p/>
 * Takes vertain items, removes it from it's location, and adds it to the game state's inventory.
 */
public class TakeAction extends Action {

    public TakeAction(@JsonProperty("targets") Collection<String> targetIds) {
        super(targetIds);
    }

    @Override
    public void apply(GameState gameState) {
        for (BaseEntity entity : findEntities(gameState)) {
            if (entity instanceof Item) {
                Item item = (Item) entity;
                gameState.addInventoryItem(item);
                item.getLocation().removeItem(item);
            } else {
                Logger.getLogger(getClass()).warning("TakeAction tried to take Location id: " + entity.getId());
            }
        }
    }
}
