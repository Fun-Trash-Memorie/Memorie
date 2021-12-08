package application.sound;

import application.Main;

import javax.sound.sampled.*;

public class SoundSystem {

    public static Clip clip;
    public static AudioInputStream audioInputStream;
    public static String wav;

    /**
     *
     * @Source https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     *
     */

    // jedem SoundSystem wird seine .wav Datei mitgegeben
    public SoundSystem(String wav) {
        Main.soundIndex++;
        Main.SOUND_THREADS.add(new SoundThread());
        SoundSystem.wav = wav;


        Main.SOUND_THREADS.get(Main.soundIndex).start();
    }

    public static void setVolume(int volume)
    {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(0);

        double gain = ((double) volume / 100); // number between 0 and 1 (loudest)
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

}