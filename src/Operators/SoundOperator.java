package Operators;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundOperator {
    public static ArrayList<Clip>SoundArrayList = new ArrayList<>();
    public static void playSoundOnThread(final String filePath, boolean looped, float volume) {
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                requestSound(filePath, looped, volume);
            }
        });

        soundThread.start();
    }

    private static void playSound(String filePath, float volume) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioIn);

            // Get the volume control for the clip
            FloatControl gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Set the volume level (in decibels)
            gainControl.setValue(volume);

            soundClip.start();
            SoundArrayList.add(soundClip);

            // Sleep for the duration of the sound to allow it to play
            Thread.sleep(soundClip.getMicrosecondLength() / 1000);
            SoundArrayList.remove(soundClip);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void StopSounds(){
        for (Clip soundClip:SoundArrayList){
            soundClip.stop();
        }
    }

    private static void requestSound(String filePath, boolean looped, float volume) {
        if (looped) {
            while (true) {
                playSound(filePath, volume);
            }
        } else {
            playSound(filePath, volume);
        }
    }
}
