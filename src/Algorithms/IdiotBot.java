package Algorithms;

import Entity.Enemy;
import Entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IdiotBot implements Algorithm{

    private long startTime;
    @Override
    public void Init(List<Enemy> enemies, Player player, long startTime) {
        this.startTime = startTime;
    }

    private long GetTimeFromStart(long _time){
        return _time - startTime;
    }
    @Override
    public byte Move(long _time) {
        // Co 5 sekund zmien kierunek ruchu
        if((GetTimeFromStart(_time)/5000) % 2 == 0) return 1;
        else return -1;
    }

    @Override
    public boolean TryShoot(long _time) {
        // Strzelaj zawsze kiedy mozliwe
        return true;
    }

    @Override
    public void FetchCurrentFrameInfo(long _time, boolean isShooted, List<Enemy> enemies, Player player) {
        if(isShooted) { System.out.println("Czas: " + (double)GetTimeFromStart(_time)/1000 + ", Strzelono!"); }
    }
}
