package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Changes the state of certain entities to a defined value.
 */
public class StateAction extends Action {
    public final String value;

    public StateAction(@JsonProperty("targets") Collection<String> targetIds,
                       @JsonProperty("value") String value) {
        super(targetIds);
        this.value = value;
    }

    @Override
    public void apply(GameState gameState) {
        for (BaseEntity entity : findEntities(gameState)) {
            entity.setState(value);
        }
    }
}
