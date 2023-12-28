package BoardController;

import Entity.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class UpgradeWindow {
    private boolean isOpen = false;
    private long window;
    private int pkt;

    public UpgradeWindow(boolean isOpen, long window) {
        this.isOpen = isOpen;
        this.window = window;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void NextUpgrade(Entity entity, int pkt) {
        Player player = (Player) entity;
        int currentpkt = pkt;
        handleKeyPress(player, currentpkt);
        //render(player);
        player.setHp(player.getMaxHp());
    }

    public void render(Player player) {
        if (isOpen) {


        }
    }

    public void handleKeyPress(Player currentPlayer, int currentPkt) {
        if (glfwGetKey(window, GLFW_KEY_1) == GLFW.GLFW_PRESS) {
            // Opcja 1: Upgrade Max HP
            currentPlayer.setMaxHp(currentPlayer.getMaxHp() + 2);
            isOpen = false;
            currentPkt--;
        } else if (glfwGetKey(window, GLFW_KEY_2) == GLFW.GLFW_PRESS) {
            // Opcja 2: Upgrade Attack Speed
            currentPlayer.setAttackFrequency(currentPlayer.getAttackFrequency() * 0.9f);
            isOpen = false;
            currentPkt--;
        } else if (glfwGetKey(window, GLFW_KEY_3) == GLFW.GLFW_PRESS) {
            // Opcja 3: Upgrade Damage
            currentPlayer.setDamage(currentPlayer.getDamage() + 1);
            isOpen = false;
            currentPkt--;
        }
    }
}