package jtext.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.action.Action;
import jtext.condition.Condition;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class Command {
    private final String text;
    private final Collection<Action> actions;
    private final Collection<Condition> conditions;

    public Command(@JsonProperty("text") String text,
                   @JsonProperty("do") Collection<Action> actions,
                   @JsonProperty("if") Collection<Condition> conditions) {
        this.text = text;
        this.actions = actions != null ? actions : Collections.emptyList();
        this.conditions = conditions != null ? conditions : Collections.emptyList();
    }

    public String getText() {
        return text;
    }

    public Iterable<Action> getActions() {
        return actions::iterator;
    }

    public Iterable<Condition> getConditions() {
        return conditions::iterator;
    }
}
