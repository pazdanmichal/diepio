package Entity;

public class Bullet extends Entity {
    private boolean isAlly;

    public Bullet(int hp, byte radius, float angle,
                  int[] position, int movementSpeed, boolean isAlly) {
        super(hp, radius, angle, position, movementSpeed);
        this.isAlly = isAlly;
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean ally) {
        isAlly = ally;
    }
    //raczej niepotrzebne

    public void Die(){
        //przyslonieta metoda dla klasy bullet -> usuniecie instancji tej klasy
    }

}
