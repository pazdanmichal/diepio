import Algorithms.*;
import Entity.*;
import Operators.ScreenHandler.ScreenOperator;
import Launcher.Windows.Launcher;

/*
                                ⠀⠀⠀  ⠀⠀⠀⣠⣤⣤⣤⣤⣤⣶⣦⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
                            ⠀⠀⠀⠀  ⠀⠀⠀⢀⣴⣿⡿⠛⠉⠙⠛⠛⠛⠛⠻⢿⣿⣷⣤⠀⠀⠀⠀⠀
                            ⠀⠀⠀⠀ ⠀⠀⠀⠀⣼⣿⠋⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠈⢻⣿⣿⡄⠀⠀⠀⠀
                            ⠀⠀ ⠀⠀⠀⠀⠀⣸⣿⡏⠀⠀⠀⣠⣶⣾⣿⣿⣿⠿⠿⠿⢿⣿⣿⣿⣄⠀⠀⠀
                            ⠀⠀⠀⠀⠀⠀ ⠀⣿⣿⠁⠀⠀⢰⣿⣿⣯⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣷⡄⠀
                            ⠀ ⠀⣀⣤⣴⣶⣶⣿⡟⠀⠀⠀⢸⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠀
                            ⠀⢰⣿⡟⠋⠉⣹⣿⡇⠀⠀⠀⠘⣿⣿⣿⣿⣷⣦⣤⣤⣤⣶⣶⣶⣶⣿⣿⣿⠀
                            ⠀⢸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀
                            ⠀⣸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠉⠻⠿⣿⣿⣿⣿⡿⠿⠿⠛⢻⣿⡇⠀⠀
                            ⠀⣿⣿⠁⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠀⢸⣿⣧⠀⠀
                            ⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀        ⠀   ⢸⣿⣿⠀⠀
                            ⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀   ⠀        ⢸⣿⣿⠀⠀
                            ⠀⢿⣿⡆⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀   ⠀        ⢸⣿⡇⠀⠀
                            ⠀⠸⣿⣧⡀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠀⣿⣿⠃⠀⠀
                            ⠀⠀⠛⢿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣰⣿⣿⣷⣶⣶⣶⣶⠶⠀ ⢠⣿⣿
                            ⠀⠀⠀⠀⠀⠀ Ɛ⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⣽⣿⡏⠁⠀  ⠀⢸⣿⡇⠀⠀⠀
                            ⠀⠀⠀⠀⠀ ⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⢹⣿⡆⠀⠀  ⠀⣸⣿⠇⠀⠀
                            ⠀⠀⠀⠀⠀ ⠀⠀⢿⣿⣦⣄⣀⣠⣴⣿⣿⠁⠀⠈⠻⣿⣿⣿⣿⡿⠏⠀⠀⠀⠀
                            ⠀⠀⠀⠀⠀ ⠀⠀⠈⠛⠻⠿⠿⠿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
*/

public class Main {

    public static void main(String[] args) {

        Player player = new Player(
                10, 50.0f, 270.0f, new float[]{0.0f, 0.0f},
                0, true, 300, 1, (byte) 0,
                1.5f, 1.7f, 0.65f, 7, 10, 0);

        Launcher launcher = new Launcher(
                playerAlgorithm -> {
                    ScreenOperator currentScreen = new ScreenOperator(player,60, 1000, 1000, playerAlgorithm);
                    ScreenOperator.RunGame();
                    return true;
                }
        );



        /*// Dodawanie nowego gracza i inicjalizowanie jego statystyk
        Player player = new Player(
                10, 50.0f, 270.0f, new float[]{0.0f, 0.0f},
                0, true, 300, 1, (byte) 0,
                1.5f, 1.7f, 0.65f, 7, 10, 0);

        // Ustawianie algorytmu
        Algorithm playerAlgorithm = null;

        // Inicjowanie konstruktora Screen
        ScreenOperator currentScreen = new ScreenOperator(
                player,60,1000, 1000, playerAlgorithm
        );

        ScreenOperator.RunGame();*/
    }
}