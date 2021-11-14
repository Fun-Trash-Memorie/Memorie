package application.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundSystem {

    /**
     *
     * @Source https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     *
     */
        // jedem SoundSystem wird seine .wav Datei mitgegeben
    public SoundSystem(String wav){
            // jedes kreierte SoundSystem läuft auf seinem eigenen Thread ---
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    URL path = this.getClass().getClassLoader().getResource(wav);
                    assert path != null;    // Es wird die Bedinngung aufgestell, dass die URL path nicht 'null' sein darf.
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
                    clip.open(inputStream); // Die Datei wird geöffnet.
                    clip.start();   // Die Datei wird abgespielt.
                    System.out.println(wav + " wurde erfolgreich abgespielt");
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                    System.err.println("Audio konnte nicht geladen oder abgespielt werden!");
                }
            }
        }).start();
    }
}
