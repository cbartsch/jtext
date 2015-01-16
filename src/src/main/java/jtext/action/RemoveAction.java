package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class RemoveAction extends Action {

    public RemoveAction(@JsonProperty("targets") Collection<String> targetIds) {
        super(targetIds);
    }
}
