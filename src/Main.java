import Algorithms.Algorithm;
import Algorithms.IdiotBot;
import BoardController.*;
import Entity.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Screen currentScreen = new Screen(900, 900);

        ArrayList<Entity> currentEntityTable = new ArrayList<Entity>();
        currentEntityTable.add(new Player(10, 50.0f, 270.0f, new float[]{0.0f, 0.0f}, 0, true, 300, 2, (byte) 0, 1.5f, 1.7f, 0.65f, 7));

        currentScreen.setEntityTable(currentEntityTable);

        Algorithm playerAlgorithm = null; //new IdiotBot();
        currentScreen.RunGame(playerAlgorithm);
    }
}
