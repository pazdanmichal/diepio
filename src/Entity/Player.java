package Entity;

public class Player extends Mob{
    private byte currentRotation;//-1 -> lewo; 0 -> stoi; 1 -> prawo
    private int rotationSpeed;

    public Player(int hp, float radius, float angle, float[] position, int movementSpeed, boolean canShoot,
                  float attackFrequency, int damage, byte currentRotation, int rotationSpeed) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
        this.currentRotation = currentRotation;
        this.rotationSpeed = rotationSpeed;
    }
    public Player() {
        super();
        this.currentRotation = 0;
        this.rotationSpeed = 0;
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
