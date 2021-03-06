package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jtext.entity.BaseEntity;
import jtext.game.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Basic action which can be added to any Command.
 * Subclasses define which action actually happens.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VisibleAction.class, name = "visible"),
        @JsonSubTypes.Type(value = StateAction.class, name = "state"),
        @JsonSubTypes.Type(value = EnableAction.class, name = "enable"),
        @JsonSubTypes.Type(value = TakeAction.class, name = "take"),
        @JsonSubTypes.Type(value = RemoveAction.class, name = "remove"),
        @JsonSubTypes.Type(value = WinAction.class, name = "win"),
})
public abstract class Action {
    private final Collection<String> targetIds;

    public Action(@JsonProperty("targets") Collection<String> targetIds) {
        this.targetIds = targetIds != null ? targetIds : new ArrayList<>(1);
    }

    public Iterable<String> getTargetIds() {
        return targetIds::iterator;
    }

    public int getTargetIdCount() {
        return targetIds.size();
    }

    public abstract void apply(GameState gameState);

    protected Collection<BaseEntity> findEntities(GameState gameState) {
        return targetIds.stream()
                .map(id -> gameState.getGame().findEntityById(id))
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }

    public void init(BaseEntity entity) {
        if(targetIds.isEmpty()) {
            targetIds.add(entity.getId());
        }
    }
}
