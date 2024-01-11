package Algorithms;

public interface Algorithm {
    public void Init(); // Get data and preprocessing

    public byte Move(); // {-1 -> left, 0 -> stop, 1 -> right}

    public boolean TryShoot(); // true -> shoot, false -> no shoot

    public void FetchCurrentFrameInfo(boolean isShooted);
}
