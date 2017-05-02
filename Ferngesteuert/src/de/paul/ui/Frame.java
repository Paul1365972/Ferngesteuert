package de.paul.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import de.paul.main.Ferngesteuert;
import de.paul.sets.StringSet;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private RunPanel runPanel;
	
	public Frame() {
		super("Ferngesteuert by Paul1365972");
		System.out.println("Opening Frame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Ferngesteuert", runPanel = new RunPanel());
		tabs.add("Options", new OptionPanel());
		tabs.add("Item Sets", new ItemEditorPanel());
		tabs.add("Quest Sets", new QuestEditorPanel());
		getContentPane().add(tabs);
		pack();
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Ferngesteuert.get().close();
			}
		});
		System.out.println("Finished");
	}
	
	public void setTime(int time) {
		runPanel.setTime(time);
	}

	public void setItemSets(List<StringSet> itemSets) {
		runPanel.setItemSets(itemSets);
	}

	public void setQuestSets(List<StringSet> questSets) {
		runPanel.setQuestSets(questSets);
	}

	public List<StringSet> getActiveQuestSets() {
		return runPanel.getActiveQuestSets();
	}

	public List<StringSet> getActiveItemSets() {
		return runPanel.getActiveItemSets();
	}
	
	public void setActiveQuest(String quest) {
		runPanel.setActiveQuest(quest);
	}
	
	public void setActiveItem(String item) {
		runPanel.setActiveItem(item);
	}

	public void addItemSet(StringSet set) {
		runPanel.addItemSet(set);
	}

	public void addQuestSet(StringSet set) {
		runPanel.addQuestSet(set);
	}

	public void removeItemSet(StringSet set) {
		runPanel.removeItemSet(set);
	}

	public void removeQuestSet(StringSet set) {
		runPanel.removeQuestSet(set);
	}
	
}
