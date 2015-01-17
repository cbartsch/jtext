package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.logging.Logger;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Removes certain items from the game state's inventory.
 */
public class RemoveAction extends Action {

    public RemoveAction(@JsonProperty("targets") Collection<String> targetIds) {
        super(targetIds);
    }

    @Override
    public void apply(GameState gameState) {
        for (String id : getTargetIds()) {
            BaseEntity entity = gameState.removeInventoryItem(id);
            if(entity == null) {
                Logger.getLogger(getClass()).warning("RemoveAction could not remove item id: " + id);
            }
        }
    }
}
