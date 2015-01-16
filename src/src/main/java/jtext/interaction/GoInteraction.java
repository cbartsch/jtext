package jtext.interaction;

import jtext.entity.Item;
import jtext.entity.Location;
import jtext.game.GameState;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class GoInteraction extends Interaction {
    public static final Interaction INSTANCE = new GoInteraction();

    private GoInteraction() { }

    @Override
    public void apply(String parameter, GameState game) {
        Location loc = game.getLocation();
        String locationId = parameter;
        if(loc.hasAdjacent(locationId)) {
            Location newLocation = game.getGame().findLocationById(locationId);
            game.setLocation(newLocation);
            // TODO Real location text/translashun
            game.display("I am now in %s", locationId);
        } else {
            // TODO Real location text/translashun
            game.display("I can't go to %s", locationId);
        }
    }
}
