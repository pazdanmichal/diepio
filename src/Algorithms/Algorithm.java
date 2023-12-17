package Algorithms;
import Entity.Player;
import Entity.Enemy;
import java.util.ArrayList;
public interface Algorithm {
    public void Init(List<Enemy> enemies, Player player, long startTime); // Get data
    public void Run(); // Preprocessing
    public byte Move(long _time); // {-1 -> left, 0 -> stop, 1 -> right}
    public boolean TryShoot(long _time); // true -> shoot, false -> no shoot
}
