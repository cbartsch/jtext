package jtext.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.action.Action;
import jtext.condition.Condition;
import jtext.game.GameState;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Commands are applied to the game state by the user,
 * via entering text commands.
 * They apply a list of actions, if all conditions check out.
 */
public class Command {
    public static final Command EMPTY = new Command("I can't do this.", null, null);
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

    public void apply(GameState gameState) {
        if(conditions.stream().allMatch(c -> c.check(gameState))) {
            gameState.display(text);
            for(Action a : actions) {
                a.apply(gameState);
            }
        }
    }
}
