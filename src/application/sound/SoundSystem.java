package application.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundSystem {

    public SoundSystem(String wav){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    URL path = this.getClass().getClassLoader().getResource(wav);
                    //System.out.println("try to get stream");
                    assert path != null;
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
                    //System.out.println("got stream");
                    clip.open(inputStream);
                    clip.start();
                    System.out.println(wav + " wurde erfolgreich abgespielt");
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                    System.err.println("Audio konnte nicht geladen oder abgespielt werden!");
                }
            }
        }).start();
    }
}
