package Algorithms;
import Entity.Player;
import Entity.Enemy;
import java.util.ArrayList;
import java.util.List;

public interface Algorithm {
    public void Init(List<Enemy> enemies, Player player, long startTime); // Get data and preprocessing
    public byte Move(long _time); // {-1 -> left, 0 -> stop, 1 -> right}
    public boolean TryShoot(long _time); // true -> shoot, false -> no shoot
    public void FetchCurrentFrameInfo(long _time, boolean isShooted, List<Enemy> enemies, Player player);
}
