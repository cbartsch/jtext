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

    public GoInteraction(Collection<String> ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String parameter, GameState game) {
        Location currentLocation = game.getLocation();
        boolean hasAdjacent = currentLocation.hasAdjacent(parameter);
        if(hasAdjacent) {
            Location newLocation = game.getGame().findLocationById(parameter);
            if(newLocation.isVisible() && newLocation.isEnabled()) {
                game.setLocation(newLocation);
                // TODO Real location text/translashun
                game.display("I am now in %s", parameter);
            } else {
                hasAdjacent = false;
            }
        }

        if(!hasAdjacent) {
            game.displayLocationNotFoundMessage(parameter);
        }
    }
}
