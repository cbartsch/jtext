package jtext.condition;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class ItemCondition extends Condition {
    private final Collection<String> itemIds;

    public ItemCondition(@JsonProperty("else") String elseText,
                         @JsonProperty("items") Collection<String> itemIds) {
        super(elseText);
        this.itemIds = itemIds;
    }
}
