package de.paul.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import de.paul.manager.IngameKeyListener;
import de.paul.manager.KeyManager;
import de.paul.manager.SoundManager;
import de.paul.manager.TimeManager;
import de.paul.resources.Resources;
import de.paul.sets.Loader;
import de.paul.sets.StringSet;
import de.paul.ui.Frame;
import de.paul.utils.FileIO;
import de.paul.utils.UnzipUtility;

public class Ferngesteuert {

	private static Ferngesteuert INSTANCE;
	private boolean running = false;
	private int time;
	private Frame frame;
	private KeyManager keyManager;
	private TimeManager timeManager;
	private SoundManager soundManager;
	private Options options;
	private List<StringSet> quests = new ArrayList<>();
	private List<StringSet> items = new ArrayList<>();
	private List<File> itemSounds = new ArrayList<>();
	private List<File> questSounds = new ArrayList<>();
	private Random rdm = new Random();
	public static final String homePath = System.getProperty("user.home").concat("\\Ferngesteuert\\");
	public static final String optionsPath = homePath.concat("options.txt");
	public static final String questsPath = homePath.concat("QuestSets");
	public static final String itemPath = homePath.concat("ItemSets");
	public static final String soundsItemsPath = homePath.concat("sounds\\items");
	public static final String soundsQuestsPath = homePath.concat("sounds\\quests");
	public static final String VERSION = "V1";

	private void start() {
		init();
		openUI();
		loop();
	}

	private void init() {
		if (!new File(homePath).exists()) {
			new File(itemPath).mkdirs();
			new File(questsPath).mkdirs();
			new File(soundsItemsPath).mkdirs();
			new File(soundsQuestsPath).mkdirs();
			try {
				new File(itemPath.concat("\\ItemSet1.txt")).createNewFile();
				new File(itemPath.concat("\\ItemSet2.txt")).createNewFile();
				new File(itemPath.concat("\\ItemSet3.txt")).createNewFile();
				new File(questsPath.concat("\\QuestSet1.txt")).createNewFile();
				new File(questsPath.concat("\\QuestSet2.txt")).createNewFile();
				new File(questsPath.concat("\\QuestSet3.txt")).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileIO.copy(Resources.get("AD-Items.txt"), itemPath.concat("\\AD-Items.txt"));
			FileIO.copy(Resources.get("AP-Items.txt"), itemPath.concat("\\AP-Items.txt"));
			UnzipUtility.unZipIt(Resources.get("itemSounds.zip"), Resources.get("itemSounds.zip"), soundsItemsPath);
		}
		options = new Options(optionsPath);
		Loader.loadStringSets(questsPath, quests);
		Loader.loadStringSets(itemPath, items);
		Loader.loadSounds(new File(soundsQuestsPath), questSounds);
		Loader.loadSounds(new File(soundsItemsPath), itemSounds);
	}

	private void openUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		frame = new Frame();
		frame.setQuestSets(quests);
		frame.setItemSets(items);
		resetTimer();
		keyManager = new KeyManager();
		keyManager.setNativeKeyListener(new IngameKeyListener());
		soundManager = new SoundManager();
	}

	private void loop() {
		timeManager = new TimeManager();
	}
	
	public void close() {
		System.out.println("Closing");
		frame.dispose();
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		options.saveOptions(optionsPath);
		for (StringSet set : items) {
			String[] lines = new String[set.getSize()];
			set.getList().toArray(lines);
			FileIO.storeText(itemPath + "\\" + set.getName() + ".txt", lines);
		}
		for (StringSet set : quests) {
			String[] lines = new String[set.getSize()];
			set.getList().toArray(lines);
			FileIO.storeText(questsPath + "\\" + set.getName() + ".txt", lines);
		}
		System.out.println("Closed");
		System.exit(0);
	}

	public static Ferngesteuert get() {
		return INSTANCE;
	}

	public static void main(String[] args) {
		System.out.println("Starting Ferngesteuert with " + Arrays.toString(args));
		INSTANCE = new Ferngesteuert();
		INSTANCE.start();
		System.out.println("Ready...");
	}

	public boolean isRunning() {
		return running;
	}

	public int getTime() {
		return time;
	}

	public void setRunning(boolean running) {
		this.running = running;
		if (running) {
			timeManager.start();
		} else {
			timeManager.stop();
			resetTimer();
		}
	}

	public void resetTimer() {
		setTime(0);
	}

	public void setTime(int time) {
		this.time = time;
		frame.setTime(time);
	}

	public Options getOptions() {
		return options;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public void nextQuest() {
		String quest = getRdmString(frame.getActiveQuestSets());
		if (quest != null) {
			frame.setActiveQuest(quest);
			if (options.isSoundQuests())
				soundManager.playQuest(quest);
		}
	}

	public void nextItem() {
		String item = getRdmString(frame.getActiveItemSets());
		if (item != null) {
			frame.setActiveItem(item);
			if (options.isSoundItems())
				soundManager.playItem(item);
		}
	}

	private String getRdmString(List<StringSet> sets) {
		List<String> all = new ArrayList<>();
		for (StringSet set : sets) {
			for (String string : set.getList()) {
				all.add(string);
			}
		}
		if (all.isEmpty())
			return null;
		return all.get(rdm.nextInt(all.size()));
	}

	public List<File> getQuestSounds() {
		return questSounds;
	}
	
	public List<File> getItemSounds() {
		return itemSounds;
	}

	public List<StringSet> getQuestsSets() {
		return quests;
	}

	public List<StringSet> getItemsSets() {
		return items;
	}
	
	public void addItemSet(StringSet set) {
		items.add(set);
		frame.addItemSet(set);
	}

	public void addQuestSet(StringSet set) {
		quests.add(set);
		frame.addQuestSet(set);
	}

	public void removeItemSet(StringSet set) {
		items.remove(set);
		frame.removeItemSet(set);
		new File(itemPath + "\\" + set.getName() + ".txt").delete();
	}

	public void removeQuestSet(StringSet set) {
		quests.remove(set);
		frame.removeQuestSet(set);
		new File(questsPath + "\\" + set.getName() + ".txt").delete();
	}

}
