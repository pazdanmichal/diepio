import Entity.*;
import Launcher.Windows.BotSelection;
import Launcher.Windows.Menu;
import Operators.ScreenHandler.ScreenOperator;

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
//cipskoooo
    public static void main(String[] args) {

        Player player = new Player(
                10, 50.0f, 270.0f, new float[]{0.0f, 0.0f},
                0, true, 300, 1, (byte) 0,
                1.5f, 1.7f, 0.65f, 7, 10, 0,2,20,1);

        Menu launcher = new Menu(
                playerAlgorithm -> {
                    ScreenOperator currentScreen = new ScreenOperator(player,60, 1000, 1000, playerAlgorithm);
                    ScreenOperator.RunGame();
                    return true;
                }
        );


    }
}