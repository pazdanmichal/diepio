package Algorithms;

import BoardController.Screen;
import Entity.Enemy;
import Entity.Entity;
import Entity.Player;

import java.util.List;

public class ClosestBot implements Algorithm{
    private long startTime;
    private Player player;
    @Override
    public void Init(List<Enemy> enemies, Player player, long startTime) {
        this.startTime = startTime;
        this.player=player;
    }
    private long GetTimeFromStart(long _time){
        return _time - startTime;
    }
    private float AngleDiff(float angle){
        if (player.getCurrentRotation()-(angle-180) < 180 && player.getCurrentRotation()-(angle-180) < -180){
            return player.getCurrentRotation()-(angle-180);
        }else if(player.getCurrentRotation()-(angle-180)>180){
            return (player.getCurrentRotation()-(angle-180))-360;
        }else {
            return 360+(player.getCurrentRotation()-(angle-180));
        }
    }

    private Enemy findClosest(){
        float minAngle=360;
        Enemy closestEnemy = null;
        for(int i = 0; i<Screen.currentEntityTable.size(); i++){
            if(Screen.currentEntityTable.get(i) instanceof Enemy){
                if(AngleDiff(Screen.currentEntityTable.get(i).getAngle())<minAngle) {
                    minAngle=AngleDiff(Screen.currentEntityTable.get(i).getAngle());
                    closestEnemy=(Enemy) Screen.currentEntityTable.get(i);
                }
            }
        }
        return closestEnemy;
    }
    @Override
    public byte Move(long _time) {
        if ((AngleDiff(findClosest().getAngle())<3 && AngleDiff(findClosest().getAngle())>-3)) return 0;
        else if(AngleDiff(findClosest().getAngle())<0) return -1;
        else return 1;
    }

    @Override
    public boolean TryShoot(long _time) {
        if (AngleDiff(findClosest().getAngle())<5 && AngleDiff(findClosest().getAngle())>-5) return true;
        return false;
    }

    @Override
    public void FetchCurrentFrameInfo(long _time, boolean isShooted, List<Enemy> enemies, Player player) {
        if(isShooted) { System.out.println("Czas: " + (double)GetTimeFromStart(_time)/1000 + ", Strzelono!"); }
    }
}
