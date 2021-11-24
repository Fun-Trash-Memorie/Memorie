package application.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundSystem {

    private Clip clip;
    private AudioInputStream audioInputStream;
    /**
     *
     * @Source https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     *
     */
        // jedem SoundSystem wird seine .wav Datei mitgegeben
    public SoundSystem(String wav) {
        // create AudioInputStream object
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("wav//" + wav).getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        // create clip reference
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        // open audioInputStream to the clip
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    // Method to play the audio
    public void play() {
        // start the clip
        clip.start();

        setVolume(30);
    }

    public void setVolume(int volume)
    {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(0);

        double gain = ((double) volume / 100); // number between 0 and 1 (loudest)
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }


}
