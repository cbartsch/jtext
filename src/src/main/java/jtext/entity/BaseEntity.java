package jtext.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jtext.command.Command;
import jtext.command.ItemCommand;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class BaseEntity {
    private String id;
    private boolean visible = true;
    private boolean enabled = true;
    private String state;

    private final Command look;
    private final Command use;
    private final Command take;
    private final ItemCommand useWith;

    public BaseEntity(@JsonProperty("visible") Boolean visible,
                      @JsonProperty("enabled")Boolean enabled,
                      @JsonProperty("state") String state,
                      @JsonProperty("look") Command look,
                      @JsonProperty("use") Command use,
                      @JsonProperty("take") Command take,
                      @JsonProperty("use_with") ItemCommand useWith) {
        this.visible = visible != null ? visible : true;
        this.enabled = enabled != null ? enabled : true;
        this.state = state != null ? state : "";
        this.look = look != null ? look : Command.EMPTY;
        this.use = use != null ? use : Command.EMPTY;
        this.take = take != null ? take : Command.EMPTY;
        this.useWith = useWith != null ? useWith : ItemCommand.EMPTY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Command getLook() {
        return look;
    }

    public Command getUse() {
        return use;
    }

    public Command getTake() {
        return take;
    }

    public ItemCommand getUseWith() {
        return useWith;
    }
}
