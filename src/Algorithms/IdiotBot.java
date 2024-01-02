package Algorithms;

import BoardController.BoardController;
import BoardController.Screen;
import Entity.Enemy;
import Entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IdiotBot implements Algorithm {
    private Screen screen;
    private long startTime;
    private long _time;

    @Override
    public void Init(Screen screen) {
        this.screen = screen;
        this.startTime = screen.getStartTime();

        _time = System.currentTimeMillis();
    }

    private long GetTimeFromStart(long time) {
        return time - startTime;
    }

    @Override
    public byte Move() {
        // Co 5 sekund zmien kierunek ruchu
        if ((GetTimeFromStart(_time) / 5000) % 2 == 0) return 1;
        else return -1;
    }

    @Override
    public boolean TryShoot() {
        // Strzelaj zawsze kiedy mozliwe
        return true;
    }

    @Override
    public void FetchCurrentFrameInfo(boolean isShooted) {
        if (isShooted) {
            System.out.println("Czas: " + (double) GetTimeFromStart(_time) / 1000 + ", Strzelono!");
        }
    }
}
