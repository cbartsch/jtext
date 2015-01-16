package jtext.game;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class GameLoader {
    private static Logger logger = Logger.getLogger(GameLoader.class.getSimpleName());

    public static Game load(InputStream input) throws IOException {
        Game game = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            game = mapper.readValue(input, Game.class);

        } catch (JsonParseException | JsonMappingException e) {
            logger.log(Level.WARNING, "Could not read game JSON", e);
        }

        return game;
    }
}
