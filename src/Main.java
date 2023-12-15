import BoardController.*;
import Entity.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Screen currentScreen = new Screen(900, 900);

        ArrayList<Entity> currentEntityTable = new ArrayList<Entity>();
        currentEntityTable.add(new Player(100, 50.0f, 90.0f, new float[]{0.0f, 0.0f}, 0, true, 200, 10, (byte) 0, 2));

        currentScreen.setEntityTable(currentEntityTable);

        currentScreen.RunGame();
    }
}
