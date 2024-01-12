package BoardController;

import Entity.*;

public class UpgradeWindow {

    public UpgradeWindow() {
    }

    public static void nextUpgrade(Player player) {
        player.setCurrentPkt(player.getCurrentPkt() + 1);
        player.setHp(player.getMaxHp());
    }

    public static void upgrade(int a, Player currentPlayer, int maxHpDiff, float attackFrequencyDiff, int damageDiff) {
        if (currentPlayer.getCurrentPkt() > 0) {
            currentPlayer.setCurrentPkt(currentPlayer.getCurrentPkt() - 1);
            if (a == 1) {
                // Opcja 1: Upgrade Max HP
                currentPlayer.setMaxHp(currentPlayer.getMaxHp() + maxHpDiff);
            } else if (a == 2) {
                // Opcja 2: Upgrade Attack Speed
                currentPlayer.setAttackFrequency(currentPlayer.getAttackFrequency() - attackFrequencyDiff);
            } else if (a == 3) {
                // Opcja 3: Upgrade Damage
                currentPlayer.setDamage(currentPlayer.getDamage() + damageDiff);
            }
        }
    }

}