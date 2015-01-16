package jtext.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.logging.Logger;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class WinAction extends Action {
    public WinAction(@JsonProperty("targets") Collection<String> targetIds) {
        super(targetIds);
    }

    @Override
    public void apply(GameState gameState) {
        Logger.getLogger(getClass()).info("YOU WIN THE GAME");
    }
}
