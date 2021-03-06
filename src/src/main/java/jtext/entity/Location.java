package jtext.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.command.Command;
import jtext.command.ItemCommand;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Location represents a location the user can be in.
 * They have a list of items which the user can interact with,
 * and a list of adjacent locations which the user can go to.
 */
public class Location extends BaseEntity {
    private final Map<String, Item> objects;
    private final Set<String> adjacent;

    public Location(@JsonProperty("visible") Boolean visible,
                    @JsonProperty("enabled") Boolean enabled,
                    @JsonProperty("state") String state,
                    @JsonProperty("look") Command look,
                    @JsonProperty("use") Command use,
                    @JsonProperty("take") Command take,
                    @JsonProperty("use_with") ItemCommand useWith,
                    @JsonProperty("objects") Map<String, Item> objects,
                    @JsonProperty("adjacent") Set<String> adjacent) {
        super(visible, enabled, state, look, use, take, useWith);
        this.objects = objects != null ? objects : Collections.emptyMap();
        this.adjacent = adjacent != null ? adjacent : Collections.emptySet();
    }

    public Collection<String> getItemIds() {
        return objects.keySet();
    }

    public Item findItemById(String itemId) {
        return objects.get(itemId);
    }

    public int getItemCount() {
        return objects.size();
    }

    public boolean hasAdjacent(String locationId) {
        return adjacent.contains(locationId);
    }

    public int getAdjacentCount() {
        return adjacent.size();
    }

    public void removeItem(Item item) {
        objects.remove(item.getId());
        item.setLocation(null);
    }

    public Collection<String> findVisibleItemIds() {
        return objects.keySet().stream()
                .filter(id -> objects.get(id).isVisible())
                .collect(Collectors.toList());
    }

    public Collection<String> listAdjacents() {
        return Collections.unmodifiableCollection(adjacent);
    }
}
