import Algorithms.*;
import BoardController.*;
import Entity.*;

public class Main {
    public static void main(String[] args) {

        // Tworzenie nowego ekranu
        Screen currentScreen = new Screen(900, 900);
        Screen.setBoardRadius(900);

        // Dodawanie nowego gracza i inicjalizowanie jego statystyk
        Player player = new Player(
                10, 50.0f, 270.0f, new float[]{0.0f, 0.0f},
                0, true, 300, 2, (byte) 0,
                1.5f, 1.7f, 0.65f, 7, 10, 0);

        // Jakies cos ze sztuczna inteligencja
        Algorithm playerAlgorithm = null; // nowy IdiotBot();
        currentScreen.RunGame(playerAlgorithm, player);
    }
}
