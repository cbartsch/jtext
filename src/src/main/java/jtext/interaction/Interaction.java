package jtext.interaction;

import jtext.entity.BaseEntity;
import jtext.entity.Location;
import jtext.game.GameState;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Chrisu on 16/01/2015.
 *
 * Interactions represent interactions between the user and the system.
 *
 * They parse the user's input,
 * locate a game entity to interact with
 * and apply the respective commands of the entity,
 * if the game state allows this.
 */
public abstract class Interaction {
    private final Collection<String> ignoredPhrases;

    public Interaction(Collection<String> ignoredPhrases) {
        this.ignoredPhrases = Collections.unmodifiableCollection(ignoredPhrases);
    }

    public void apply(String parameter, GameState game) {
        String cleanedParameter = parameter;
        for (String ignoredPhrase : ignoredPhrases) {
            if(cleanedParameter.startsWith(ignoredPhrase + InteractionManager.WORD_SEPARATOR)) {
                cleanedParameter = cleanedParameter.substring(ignoredPhrase.length() + InteractionManager.WORD_SEPARATOR.length(), cleanedParameter.length());
            }
        }

        applyInternal(cleanedParameter, game);
    }

    protected BaseEntity findItem(String id, Location location) {
        if(Objects.equals(id, location.getId())) {
            return location;
        } else {
            return location.findItemById(id);
        }
    }

    protected abstract void applyInternal(String parameter, GameState gameState);
}
