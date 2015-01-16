package jtext.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by Chrisu on 16/01/2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ItemCondition.class, name = "item"),
        @JsonSubTypes.Type(value = StateCondition.class, name = "state")
})
public class Condition {
    public final String elseText;

    public Condition(@JsonProperty("else") String elseText) {
        this.elseText = elseText;
    }
}
