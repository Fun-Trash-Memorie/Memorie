package application.game.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundThread extends Thread{

    public void run() {
        System.out.println("neuer Sound-Thread gestartet.");

        // create AudioInputStream object
        try {
            SoundSystem.audioInputStream = AudioSystem.getAudioInputStream(new File("wav//" + SoundSystem.wav).getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        // create clip reference
        try {
            SoundSystem.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        // open audioInputStream to the clip
        try {
            SoundSystem.clip.open(SoundSystem.audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        // start the clip
        SoundSystem.clip.start();
        SoundSystem.setVolume(30);
    }
}
