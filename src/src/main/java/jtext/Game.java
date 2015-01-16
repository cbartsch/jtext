package jtext;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
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
    private final String start;
    private final String startText;

    private final Map<String, BaseEntity> entities;

    public Game(
            @JsonProperty(value = "locations") Map<String, Location> locations,
            @JsonProperty(value = "start") String start,
            @JsonProperty(value = "start_text") String startText) {
        if(locations == null || Strings.isNullOrEmpty(start)) {
            throw new IllegalArgumentException("Game must have locations and start location");
        }
        this.locations = locations;
        this.start = start;
        this.startText = startText;
        entities = new HashMap<>();

        buildEntityMap();
    }

    private void buildEntityMap() {
        for (Map.Entry<String, Location> locationEntry : locations.entrySet()) {
            Location location = locationEntry.getValue();
            addEntity(locationEntry.getKey(), location);
            for (String itemId: location.getItemIds()) {
                addEntity(itemId, location.findItemById(itemId));
            }
        }
    }

    private void addEntity(String id, BaseEntity entity) {
        entity.setId(id);
        entities.put(id, entity);
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

    public String getStart() {
        return start;
    }

    public String getStartText() {
        return startText;
    }

    public Location findLocationById(String id) {
        return locations.get(id);
    }
}
