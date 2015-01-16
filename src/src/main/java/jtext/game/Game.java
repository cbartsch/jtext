package jtext.game;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import jtext.command.Command;
import jtext.entity.BaseEntity;
import jtext.entity.Item;
import jtext.entity.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class Game {
    private final Map<String, Location> locations;
    private final String startLocationId;
    private final String startText;

    private final Map<String, BaseEntity> entities;

    public Game(
            @JsonProperty(value = "locations") Map<String, Location> locations,
            @JsonProperty(value = "start") String startLocationId,
            @JsonProperty(value = "start_text") String startText) {
        if(locations == null || Strings.isNullOrEmpty(startLocationId)) {
            throw new IllegalArgumentException("Game must have locations and start location");
        }
        this.locations = locations;
        this.startLocationId = startLocationId;
        this.startText = startText;
        entities = new HashMap<>();

        buildEntityMap();
    }

    private void buildEntityMap() {
        for (Map.Entry<String, Location> locationEntry : locations.entrySet()) {
            Location location = locationEntry.getValue();
            addEntity(locationEntry.getKey(), location);
            for (String itemId: location.getItemIds()) {
                Item item = location.findItemById(itemId);
                addEntity(itemId, item);
                item.setLocation(location);
            }
        }
    }

    private void addEntity(String id, BaseEntity entity) {
        entity.setId(id);
        entities.put(id, entity);
        initCommand(entity, entity.getLook());
    }

    private void initCommand(BaseEntity entity, Command command) {
        command.getActions().forEach(action -> action.init(entity));
    }

    public BaseEntity findEntityById(String id) {
        return entities.get(id);
    }

    public  int getEntityCount() {
        return entities.size();
    }

    public int getLocationCount() {
        return locations.size();
    }

    public Map<String, Location> getLocations() {
        return locations;
    }

    public String getStartLocationId() {
        return startLocationId;
    }

    public String getStartText() {
        return startText;
    }

    public Location findLocationById(String id) {
        return locations.get(id);
    }

    public Location getStartLocation() {
        return findLocationById(startLocationId);
    }
}
