package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class StateAction extends Action {
    public final String value;

    public StateAction(@JsonProperty("targets") Collection<String> targetIds,
                       @JsonProperty("value") String value) {
        super(targetIds);
        this.value = value;
    }
}
