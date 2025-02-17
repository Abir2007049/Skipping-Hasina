package main;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {
    private static Clip clip; // Store the clip globally

    public static void main(String[] args) {
        int boardWidth = 750;
        int boardHeight = 450;

        JFrame frame = new JFrame("SkipHasina");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SkipHasina skiphasina = new SkipHasina();
        frame.add(skiphasina);
        frame.pack();
        skiphasina.requestFocus();
        frame.setVisible(true);

        // Start music in a separate thread
        new Thread(() -> playSound("src/fire-crackers-23055.wav")).start();
    }

    public static void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip(); // Store clip in global variable
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music indefinitely
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
