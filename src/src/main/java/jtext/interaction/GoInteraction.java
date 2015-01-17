package jtext.interaction;

import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * The go interaction manages the user "walking" from one location to another.
 * Only adjacent locations of the current location, which are enabled can be went to.
 */
public class GoInteraction extends Interaction {

    public GoInteraction(String ... ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState game) {
        Location loc = game.getLocation();
        String locationId = parameter;
        if(loc.hasAdjacent(locationId)) {
            Location newLocation = game.getGame().findLocationById(locationId);
            if(newLocation.isVisible() && newLocation.isEnabled()) {
                game.setLocation(newLocation);
                newLocation.getLook().apply(game);
            } else {
                game.display("I can't go to %s", locationId);
            }
        } else {
            game.display("I can't go to %s", locationId);
        }
    }
}
