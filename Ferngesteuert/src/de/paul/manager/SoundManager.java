package de.paul.manager;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import de.paul.main.Ferngesteuert;
import de.paul.utils.Utils;
import javazoom.jl.player.Player;

public class SoundManager {
	
	public SoundManager() {
		System.out.println("Init SoundManager");
		System.out.println("Finished");
	}

	public void playItem(String soundName) {
		List<File> sounds = Ferngesteuert.get().getItemSounds();
		for (final File file : sounds) {
			String fileName = file.getName();
			fileName = Utils.normalize(fileName.substring(0, fileName.length() - 4));
			soundName = Utils.normalize(soundName);
			if (!soundName.equalsIgnoreCase(fileName))
				continue;
			new Thread(new Runnable() {
				@Override
				public void run() {
					play(file, Ferngesteuert.get().getOptions().getVolume());
				}
			}).start();
			break;
		}
	}
	
	public void playQuest(String soundName) {
		List<File> sounds = Ferngesteuert.get().getQuestSounds();
		for (final File file : sounds) {
			String fileName = file.getName();
			fileName = Utils.normalize(fileName.substring(0, fileName.length() - 4));
			soundName = Utils.normalize(soundName);
			if (!soundName.equalsIgnoreCase(fileName))
				continue;
			new Thread(new Runnable() {
				@Override
				public void run() {
					play(file, Ferngesteuert.get().getOptions().getVolume());
				}
			}).start();
			break;
		}
	}

	private void play(File file, float volume) {
		try {
			FileInputStream fis = new FileInputStream(file);
			Player player = new Player(fis);
			player.setGain(Ferngesteuert.get().getOptions().getVolume());
			player.play();
			fis.close();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
	
}
