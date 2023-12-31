package Entity;

public class Enemy extends Mob{
    public Enemy(int hp, float radius, float angle, float[] position,
                 float movementSpeed, boolean canShoot, int attackFrequency, int damage) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
    }
    public Enemy() {
        super();
    }
    public void Die(){
        //przyslonieta metoda dla klasy enemy -> usuniecie instancji tej klasy
    }

    @Override
    public String toString() {
        return "\nEnemy:\n" + super.toString();
    }
}
