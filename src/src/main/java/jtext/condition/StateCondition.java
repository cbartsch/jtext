package jtext.condition;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class StateCondition extends Condition {
    private final Collection<String> targetIds;
    private final String state;

    public StateCondition(@JsonProperty("else") String elseText,
                          @JsonProperty("targets") Collection<String> targetIds,
                          @JsonProperty("value") String state) {
        super(elseText);
        this.targetIds = targetIds;
        this.state = state;
    }
}
