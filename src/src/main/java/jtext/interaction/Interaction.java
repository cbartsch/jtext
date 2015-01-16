package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Objects;

/**
 * Created by Chrisu on 16/01/2015.
 */
public abstract class Interaction {
    public abstract void apply(String parameter, GameState game);

    protected BaseEntity findItem(String id, Location location) {
        if(Objects.equals(id, location.getId())) {
            return location;
        } else {
            return location.findItemById(id);
        }
    }
}
