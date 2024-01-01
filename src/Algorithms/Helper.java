package Algorithms;

public class Helper {
    public static float mod360(float n) {
        n %= 360;
        if (n < 0) {
            n += 360;
        }
        return n;
    }
}
