package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VisibleAction.class, name = "visible"),
        @JsonSubTypes.Type(value = StateAction.class, name = "state"),
        @JsonSubTypes.Type(value = EnableAction.class, name = "enable"),
        @JsonSubTypes.Type(value = TakeAction.class, name = "take"),
        @JsonSubTypes.Type(value = RemoveAction.class, name = "remove")
})
public abstract class Action {
    private final Collection<String> targetIds;

    public Action(@JsonProperty("targets") Collection<String> targetIds) {
        this.targetIds = targetIds;
    }

    public Iterable<String> getTargetIds() {
        return targetIds::iterator;
    }

    public int getTargetIdCount() {
        return targetIds.size();
    }
}
