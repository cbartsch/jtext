import jtext.game.Game;
import jtext.game.GameLoader;
import jtext.game.GameState;
import jtext.interaction.InteractionManager;

import java.io.*;
import java.util.Objects;

/**
 * Created by Chrisu on 16/01/2015.
 */
public class Starter {

    public static void main(String[] args) throws IOException {
        Game game = GameLoader.load(new FileInputStream(args[0]));
        InteractionManager interactionManager = new InteractionManager(new GameState(game, System.out));

        System.out.printf("Enter 'exit' to quit the game at any time %n%n");
        interactionManager.start();
        String input = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            input = reader.readLine();
            interactionManager.applyCommand(input);
        } while(!Objects.equals("exit", input.toLowerCase()));

        System.out.println("Goodbye");
    }
}
