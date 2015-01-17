package jtext.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.action.Action;
import jtext.condition.Condition;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The item command extends the normal command by adding a reference to an item,
 * for the use_with command, this means the entered item must be this item.
 */
public class ItemCommand extends Command {
    public static final ItemCommand EMPTY = new ItemCommand("I can't do this.", null, null, "");
    public final String itemId;

    public ItemCommand(@JsonProperty("text") String text,
                       @JsonProperty("do") Collection<Action> actions,
                       @JsonProperty("if") Collection<Condition> conditions,
                       @JsonProperty("item") String itemId) {
        super(text, actions, conditions);
        this.itemId = itemId;
    }
}
