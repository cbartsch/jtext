package jtext.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The state condition checks out if the state of a list of entities is the required state.
 */
public class StateCondition extends Condition {
    private final Collection<String> targetIds;
    private final String value;

    public StateCondition(@JsonProperty("else") String elseText,
                          @JsonProperty("targets") Collection<String> targetIds,
                          @JsonProperty("value") String value) {
        super(elseText);
        this.targetIds = targetIds;
        this.value = value;
    }

    @Override
    public boolean check(GameState state) {
        boolean result = targetIds.stream().allMatch(id -> checkEntity(state.getGame().findEntityById(id)));
        if(!result) {
            state.display(elseText);
        }

        return result;
    }

    private boolean checkEntity(BaseEntity entity) {
        return entity != null && entity.getState().equals(value);
    }
}
