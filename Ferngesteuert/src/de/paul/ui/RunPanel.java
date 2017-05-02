package de.paul.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import de.paul.components.AbstractPanel;
import de.paul.components.MyListSelectionModel;
import de.paul.main.Ferngesteuert;
import de.paul.sets.StringSet;

public class RunPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;

	private JLabel timeLabel;
	private JTextArea questLabel;
	private JTextArea itemLabel;
	private DefaultListModel<StringSet> questModel = new DefaultListModel<>();
	private JList<StringSet> questList;
	private DefaultListModel<StringSet> itemModel = new DefaultListModel<>();
	private JList<StringSet> itemList;
	private DecimalFormat df2 = new DecimalFormat("00");

	public RunPanel() {
		super(null);
		
		final JButton startButton = new JButton("Start");
		startButton.setFont(big);
		startButton.setBounds(10, 220, 100, 40);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Ferngesteuert.get().isRunning()) {
					Ferngesteuert.get().setRunning(false);
					startButton.setText("Start");
				} else {
					Ferngesteuert.get().setRunning(true);
					startButton.setText("Stop");
				}
			}
		});

		timeLabel = new JLabel("Loading...", SwingConstants.CENTER);
		timeLabel.setFont(big);
		timeLabel.setBounds(10, 265, 100, 28);

		JLabel questNameLabel = new JLabel("Select Quest Sets", SwingConstants.CENTER);
		questNameLabel.setFont(medium);
		questNameLabel.setBounds(30, 10, 160, 20);
		
		questList = new JList<>(questModel);
		questList.setSelectionModel(new MyListSelectionModel());
		questList.setDragEnabled(true);
		
		JScrollPane scrollQuestList = new JScrollPane(questList);
		scrollQuestList.setBounds(30, 30, 160, 180);
		
		JLabel itemNameLabel = new JLabel("Select Item Sets", SwingConstants.CENTER);
		itemNameLabel.setFont(medium);
		itemNameLabel.setBounds(210, 10, 160, 20);
		
		itemList = new JList<>(itemModel);
		itemList.setSelectionModel(new MyListSelectionModel());
		itemList.setDragEnabled(true);
		
		JScrollPane scrollItemList = new JScrollPane(itemList);
		scrollItemList.setBounds(210, 30, 160, 180);
		
		questLabel = new JTextArea("Quest: None");
		questLabel.setBounds(120, 215, 270, 40);
		questLabel.setFont(medium);
		questLabel.setLineWrap(true);
		questLabel.setBackground(getBackground());
		questLabel.setEditable(false);
		
		itemLabel = new JTextArea("Item: None");
		itemLabel.setBounds(120, 260, 270, 40);
		itemLabel.setFont(medium);
		itemLabel.setLineWrap(true);
		itemLabel.setBackground(getBackground());
		itemLabel.setEditable(false);
		
		add(startButton);
		add(timeLabel);
		add(scrollQuestList);
		add(scrollItemList);
		add(questNameLabel);
		add(itemNameLabel);
		add(questLabel);
		add(itemLabel);
	}

	public void setItemSets(List<StringSet> itemSets) {
		itemModel.clear();
		for (StringSet set : itemSets) {
			itemModel.addElement(set);
		}
	}

	public void setQuestSets(List<StringSet> questSets) {
		questModel.clear();
		for (StringSet set : questSets) {
			questModel.addElement(set);
		}
	}

	public void setTime(int time) {
		int sec = time % 60;
		int min = (time - sec) / 60;
		timeLabel.setText(df2.format(min).concat(":").concat(df2.format(sec)));
	}
	
	public List<StringSet> getActiveQuestSets() {
		return questList.getSelectedValuesList();
	}
	
	public List<StringSet> getActiveItemSets() {
		return itemList.getSelectedValuesList();
	}

	public void setActiveQuest(String quest) {
		questLabel.setText("Quest: ".concat(quest));
	}

	public void setActiveItem(String item) {
		itemLabel.setText("Item: ".concat(item));
	}

	public void addItemSet(StringSet set) {
		itemModel.addElement(set);
	}
	
	public void addQuestSet(StringSet set) {
		questModel.addElement(set);
	}
	
	public void removeItemSet(StringSet set) {
		itemModel.removeElement(set);
	}
	
	public void removeQuestSet(StringSet set) {
		questModel.removeElement(set);
	}
	
}
