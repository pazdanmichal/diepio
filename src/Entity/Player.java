package Entity;

public class Player extends Mob{
    private byte currentRotation;//-1 -> lewo; 0 -> stoi; 1 -> prawo
    private int rotationSpeed;

    public Player(int hp, byte radius, float angle, int[] position, int movementSpeed, boolean canShoot,
                  int attackFrequency, int damage, byte currentRotation, int rotationSpeed) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
        this.currentRotation = currentRotation;
        this.rotationSpeed = rotationSpeed;
    }

    public byte getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(byte currentRotation) {
        this.currentRotation = currentRotation;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }
    //mozliwa zmiana, np. zmiana statystyk w trakcie gry (lvl up)

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void Die(){
        //przyslonieta metoda dla klasy player -> zakonczenie rozgrywki
    }
}
