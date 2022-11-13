package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sounds/game_menu_and_ost.wav");
		soundURL[1] = getClass().getResource("/sounds/loot.wav");
		soundURL[2] = getClass().getResource("/sounds/door.wav");
		soundURL[3] = getClass().getResource("/sounds/Ending_of_the_game.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip =  AudioSystem.getClip();
			clip.open(ais);
		
		}catch(Exception e) {					
	}
}
		public void play() {
			clip.start();
		}
		
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		public void stop() {
			clip.stop();
		}
}
