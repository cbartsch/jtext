package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Make certain items visible or invisible.
 */
public class VisibleAction extends Action {
    public final boolean value;

    public VisibleAction(@JsonProperty("targets") Collection<String> targetIds,
                         @JsonProperty("value") Boolean value) {
        super(targetIds);
        this.value = value != null ? value : true;
    }

    @Override
    public void apply(GameState gameState) {
        for (BaseEntity entity : findEntities(gameState)) {
            entity.setVisible(value);
        }
    }
}
