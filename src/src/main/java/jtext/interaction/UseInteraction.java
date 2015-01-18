package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The use interaction calls the use command's apply() method of an object, if it is visible and enabled.
 */
public class UseInteraction extends Interaction {
    // TODO Replace this with a translatable String, if we want to
    private static final String WITH_STRING = InteractionManager.WORD_SEPARATOR + "with" + InteractionManager.WORD_SEPARATOR;

    public UseInteraction(Collection<String> ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState gameState) {
        if(parameter.contains(WITH_STRING)) {
            applyUseWith(parameter, gameState);
        } else {
            applyUse(parameter, gameState);
        }
    }

    private void applyUseWith(String parameter, GameState gameState) {
        String[] itemIds = parameter.split(WITH_STRING);
        String inventoryId = itemIds[0];
        Item inventoryItem = gameState.findInventoryItemById(inventoryId);
        if(inventoryItem == null) {
            gameState.display("I don't seem to have %s", inventoryId);
        } else {
            String otherId = itemIds[1];
            BaseEntity otherItem = gameState.getLocation().findItemById(otherId);
            if(otherItem != null) {
                if(otherItem.getUseWith().itemId.equals(inventoryId)) {
                    otherItem.getUseWith().apply(gameState);
                } else {
                    gameState.display("I can't use %s with %s", inventoryId, otherId);
                }
            } else {
                gameState.displayItemNotFoundMessage(otherId);
            }
        }
    }

    private void applyUse(String itemId, GameState gameState) {
        Location loc = gameState.getLocation();
        BaseEntity item = findItem(itemId, loc);
        if(item != null && item.isVisible()) {
            if(item.isEnabled()) {
                item.getUse().apply(gameState);
            } else {
                gameState.display("I cannot use %s", itemId);
            }
        } else {
            gameState.displayItemNotFoundMessage(itemId);
        }
    }


}
