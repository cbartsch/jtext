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
        if(args.length < 1) {
            System.out.println("first command line argument must be path to game .json file");
            return;
        }
        Game game = GameLoader.load(new FileInputStream(args[0]));
        InteractionManager interactionManager = new InteractionManager(new GameState(game, System.out));

        System.out.printf("Enter 'exit' to quit the game at any time %n%n");
        interactionManager.start();
        String input = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while((input = reader.readLine()) != null && !input.toLowerCase().equals("exit")) {
            interactionManager.applyCommand(input);
        }

        System.out.println("Goodbye");
    }
}
