package jtext.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The item condition checks out, if the user possesses all the required items.
 */
public class ItemCondition extends Condition {
    private final Collection<String> itemIds;

    public ItemCondition(@JsonProperty("else") String elseText,
                         @JsonProperty("items") Collection<String> itemIds) {
        super(elseText);
        this.itemIds = itemIds;
    }

    @Override
    public boolean check(GameState state) {
        boolean result = itemIds.stream().allMatch(state::hasInventoryItem);
        if(!result) {
            state.display(elseText);
        }

        return result;
    }
}
