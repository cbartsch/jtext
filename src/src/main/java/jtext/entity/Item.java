package jtext.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.command.Command;
import jtext.command.ItemCommand;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class Item extends BaseEntity {

    private Location location;

    public Item(@JsonProperty("visible") Boolean visible, @JsonProperty("enabled") Boolean enabled, @JsonProperty("state") String state, @JsonProperty("look") Command look, @JsonProperty("use") Command use, @JsonProperty("take") Command take, @JsonProperty("use_with") ItemCommand useWith) {
        super(visible, enabled, state, look, use, take, useWith);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
