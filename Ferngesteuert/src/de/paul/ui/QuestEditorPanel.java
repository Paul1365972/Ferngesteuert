package de.paul.ui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import de.paul.components.AbstractPanel;
import de.paul.main.Ferngesteuert;
import de.paul.sets.StringSet;
import de.paul.utils.NetworkUtils;
import de.paul.utils.Utils;

public class QuestEditorPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;
	
	public QuestEditorPanel() {
		super(null);
		JButton exploreButton = new JButton("Open Folder");
		exploreButton.setBounds(10, 260, 130, 30);
		
		JButton newSet = new JButton("New Set");
		newSet.setBounds(145, 240, 80, 15);
		
		JButton delSet = new JButton("Del. Set");
		delSet.setBounds(145, 260, 80, 15);
		
		JLabel availableQuestsLabel = new JLabel("Write Quests", SwingConstants.CENTER);
		availableQuestsLabel.setFont(medium);
		availableQuestsLabel.setBounds(10, 10, 130, 20);
		
		JLabel QuestsetLabel = new JLabel("Quest Sets", SwingConstants.CENTER);
		QuestsetLabel.setFont(medium);
		QuestsetLabel.setBounds(150, 10, 70, 20);
		
		JLabel containedQuestsLabel = new JLabel("Contained Quests", SwingConstants.CENTER);
		containedQuestsLabel.setFont(medium);
		containedQuestsLabel.setBounds(230, 10, 160, 20);
		
		final JTextField questField = new JTextField();
		questField.setBounds(10, 40, 130, 25);
		
		final JButton addQuest = new JButton("Add");
		addQuest.setBounds(10, 70, 130, 30);
		
		List<StringSet> questSets = Ferngesteuert.get().getQuestsSets();
		final DefaultListModel<StringSet> questsListModel = new DefaultListModel<>();
		for (StringSet set : questSets) {
			questsListModel.addElement(set);
		}
		final JList<StringSet> questsList = new JList<>(questsListModel);
		questsList.setDragEnabled(true);
		questsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane questsListPane = new JScrollPane(questsList);
		questsListPane.setBounds(150, 40, 70, 200);
		
		final DefaultListModel<String> containedQuestsModel = new DefaultListModel<>();
		JList<String> containedQuests = new JList<>(containedQuestsModel);
		containedQuests.setDragEnabled(true);
		containedQuests.setSelectionModel(new DefaultListSelectionModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void setSelectionInterval(int index0, int index1) {
				String del = containedQuestsModel.remove(index0);
				questsList.getSelectedValue().getList().remove(del);
			}
		});
		containedQuests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane containedQuestsPane = new JScrollPane(containedQuests);
		containedQuestsPane.setBounds(230, 40, 160, 200);
		
		JLabel removeInfo = new JLabel("Click to Remove", SwingConstants.CENTER);
		removeInfo.setBounds(230, 240, 160, 20);
		
		questsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				StringSet set = questsList.getSelectedValue();
				if (set != null) {
					containedQuestsModel.removeAllElements();
					for (int i = 0; i < set.getSize(); i++) {
						containedQuestsModel.addElement(set.get(i));
					}
				}
			}
		});
		
		exploreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(Ferngesteuert.questsPath));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		addQuest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String quest = Utils.normWithSpace(questField.getText());
				containedQuestsModel.addElement(quest);
				questsList.getSelectedValue().add(quest);
				questField.setText("");
				addQuest.setText("Downloading Sound");
				addQuest.setEnabled(false);
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						NetworkUtils.downloadMP3(quest);
						addQuest.setText("Add");
						addQuest.setEnabled(true);
					}
				});
			}
		});
		
		newSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Enter a Name");
				name = Utils.normWithSpace(name);
				if (name != null) {
					StringSet set = new StringSet(null, name);
					questsListModel.addElement(set);
					Ferngesteuert.get().addQuestSet(set);
				}
			}
		});
		
		delSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSet set = questsList.getSelectedValue();
				questsListModel.removeElement(set);
				Ferngesteuert.get().removeQuestSet(set);
			}
		});
		
		add(exploreButton);
		add(questField);
		add(addQuest);
		add(questsListPane);
		add(containedQuestsPane);
		add(removeInfo);
		add(availableQuestsLabel);
		add(QuestsetLabel);
		add(containedQuestsLabel);
		add(newSet);
		add(delSet);
		questsList.setSelectedIndex(0);
	}
}
