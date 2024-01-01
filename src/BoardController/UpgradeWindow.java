package BoardController;

import Entity.*;

public abstract class UpgradeWindow {
    public static void nextUpgrade(Player player) {
        player.setCurrentPkt(player.getCurrentPkt() + 1);
        player.setHp(player.getMaxHp());
    }

    public static void upgrade(int a, Player currentPlayer) {
        if (currentPlayer.getCurrentPkt() > 0) {
            if (a == 1) {
                // Opcja 1: Upgrade Max HP
                currentPlayer.setMaxHp(currentPlayer.getMaxHp() + 2);
                currentPlayer.setCurrentPkt(currentPlayer.getCurrentPkt() - 1);
            } else if (a == 2) {
                // Opcja 2: Upgrade Attack Speed
                currentPlayer.setAttackFrequency(currentPlayer.getAttackFrequency() * 0.9f);
                currentPlayer.setCurrentPkt(currentPlayer.getCurrentPkt() - 1);
            } else if (a == 3) {
                // Opcja 3: Upgrade Damage
                currentPlayer.setDamage(currentPlayer.getDamage() + 1);
                currentPlayer.setCurrentPkt(currentPlayer.getCurrentPkt() - 1);
            }
        }
    }

}