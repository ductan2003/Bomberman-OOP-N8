package uet.oop.bomberman.controlSystem;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlay {
    private Clip clip;
    String sFile;
    SoundPlay(String sFile) {
        this.sFile = sFile;
        try{
            File f = new File("./" + sFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            // Lower audio
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-18.0f);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
