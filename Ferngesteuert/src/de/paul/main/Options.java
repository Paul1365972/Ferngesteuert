package de.paul.main;

import org.jnativehook.keyboard.NativeKeyEvent;

import de.paul.utils.FileIO;

public class Options {

	private static final String undef = String.valueOf(NativeKeyEvent.VC_UNDEFINED);
	private static final String[] defaultOptions = new String[] { Ferngesteuert.VERSION, "true", "true", undef, undef, undef, "0", "300", "true", "true"};

	private String version;
	private boolean soundQuests;
	private boolean soundItems;
	private int nextQuestKey;
	private int nextItemKey;
	private int startKey;
	private int volume;
	private int interval;
	private boolean hotkeyActive;
	private boolean resetInterval;

	public Options(String path) {
		String[] lines = FileIO.loadText(path, defaultOptions);
		loadOptions(lines);
	}

	public void loadOptions(String[] lines) {
		version = lines[0];
		soundQuests = Boolean.valueOf(lines[1]);
		soundItems = Boolean.valueOf(lines[2]);
		nextQuestKey = Integer.valueOf(lines[3]);
		nextItemKey = Integer.valueOf(lines[4]);
		startKey = Integer.valueOf(lines[5]);
		volume = Integer.valueOf(lines[6]);
		interval = Integer.valueOf(lines[7]);
		hotkeyActive = Boolean.valueOf(lines[8]);
		resetInterval = Boolean.valueOf(lines[9]);
	}

	public void saveOptions(String path) {
		String[] options = new String[] { Ferngesteuert.VERSION, String.valueOf(soundQuests), String.valueOf(soundItems), String.valueOf(nextQuestKey), String.valueOf(nextItemKey), String.valueOf(startKey), String.valueOf(volume), String.valueOf(interval), String.valueOf(hotkeyActive), String.valueOf(resetInterval)};
		FileIO.storeText(path, options);
	}

	public boolean isSoundQuests() {
		return soundQuests;
	}

	public void setSoundQuests(boolean soundQuests) {
		this.soundQuests = soundQuests;
	}

	public boolean isSoundItems() {
		return soundItems;
	}

	public void setSoundItems(boolean soundItems) {
		this.soundItems = soundItems;
	}

	public int getNextQuestKey() {
		return nextQuestKey;
	}

	public void setNextQuestKey(int nextQuestKey) {
		this.nextQuestKey = nextQuestKey;
	}

	public int getNextItemKey() {
		return nextItemKey;
	}

	public void setNextItemKey(int nextItemKey) {
		this.nextItemKey = nextItemKey;
	}

	public int getStartKey() {
		return startKey;
	}

	public void setStartKey(int startKey) {
		this.startKey = startKey;
	}

	public String getVersion() {
		return version;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public boolean isHotkeyActive() {
		return hotkeyActive;
	}

	public void setHotkeyActive(boolean hotkeyActive) {
		this.hotkeyActive = hotkeyActive;
	}

	public boolean isResetInterval() {
		return resetInterval;
	}

	public void setResetInterval(boolean resetInterval) {
		this.resetInterval = resetInterval;
	}

}
