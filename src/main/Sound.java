package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
     public Sound() {

         soundURL[0] = getClass().getResource("../sound/teehee.wav");
         soundURL[1] = getClass().getResource("../sound/autumn.wav");
     }
     public void setFile(int track){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[track]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     }
     public void playSound(){
        clip.start();
     }
     public void loopSound(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
     }
     public void stopSound(){
        clip.stop();
     }
}
