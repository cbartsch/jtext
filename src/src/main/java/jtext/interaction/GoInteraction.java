package jtext.interaction;

import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 * <p>
 * The go interaction manages the user "walking" from one location to another.
 * Only adjacent locations of the current location, which are enabled can be went to.
 */
public class GoInteraction extends Interaction {

    public GoInteraction(String... ignoredPhrases) {
        super(ignoredPhrases);
    }

    @Override
    protected void applyInternal(String locationId, GameState game) {
        Location currentLocation = game.getLocation();
        //if the entered location is the current location, nothing will happen.
        if(locationId.equals(game.getLocation().getId())) {
            game.display("This is where I am right now.");
            return;
        }
        boolean hasAdjacent = currentLocation.hasAdjacent(locationId);
        if (hasAdjacent) {
            Location newLocation = game.getGame().findLocationById(locationId);
            if (newLocation.isVisible() && newLocation.isEnabled()) {
                game.setLocation(newLocation);
                newLocation.getLook().apply(game);
            } else {
                hasAdjacent = false;
            }
        }

        if (!hasAdjacent) {
            game.displayLocationNotFoundMessage(locationId);
        }
    }
}
