package Entity;

public class Enemy extends Mob{
    public Enemy(int hp, byte radious, float angle, int[] position,
                 int movementSpeed, boolean canShoot, int attackFrequency, int damage) {
        super(hp, radious, angle, position, movementSpeed, canShoot, attackFrequency, damage);
    }
    public void Die(){
        //przyslonieta metoda dla klasy enemy -> usuniecie instancji tej klasy
    }
}