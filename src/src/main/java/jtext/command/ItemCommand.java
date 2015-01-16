package jtext.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.action.Action;
import jtext.condition.Condition;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class ItemCommand extends Command {
    public final String itemId;

    public ItemCommand(@JsonProperty("text") String text,
                       @JsonProperty("do") Collection<Action> actions,
                       @JsonProperty("if") Collection<Condition> conditions,
                       @JsonProperty("item") String itemId) {
        super(text, actions, conditions);
        this.itemId = itemId;
    }
}
