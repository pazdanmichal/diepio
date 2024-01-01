// Klasa "BoardController.Statistics" przechowuje informacje dotyczące postępu gracza w trakcie rozgrywki
// Na razie ma tylko jedno pole - killedEnemies

// Pole killedEnemies jest nadpisywane w klasie Collider (przy kolizji i śmierci przeciwnika) i odczytywane
// w klasie Screen (przy wpisywaniu statystyk do tabeli)

// W przyszłości, wraz z rozwojem projektu, będzie można dodać więcej pól
// (na przykład celność gracza)

package BoardController;

public class Statistics {
    static int killedEnemies;

    public Statistics(int killedEnemies) {
        Statistics.killedEnemies = 0;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void setKilledEnemies(int killedEnemies) {
        Statistics.killedEnemies = killedEnemies;
    }


}
