import Algorithms.Algorithm;
import BoardController.*;
import Entity.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Tworzenie nowego ekranu
        Screen currentScreen = new Screen(900, 900);

        // Deklarowanie pustej listy wszystkich obiektow (Entity)
        ArrayList<Entity> currentEntityTable = new ArrayList<>();

        // Dodawanie nowego gracza i inicjalizowanie jego statystyk
        currentEntityTable.add(new Player(
                10, 50.0f, 270.0f, new float[]{0.0f, 0.0f},
                0, true, 300, 2, (byte) 0,
                1.5f, 1.7f, 0.65f, 7, 10));

        // Przesłanie ArrayListy z graczem (Player) do instancji klasy Screen
        currentScreen.setEntityTable(currentEntityTable);

        // Jakies gowno ze sztuczna inteligencja
        Algorithm playerAlgorithm = null; // nowy IdiotBot();
        currentScreen.RunGame(playerAlgorithm);
    }
}
