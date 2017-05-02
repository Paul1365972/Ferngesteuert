package de.paul.ui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.paul.components.AbstractPanel;
import de.paul.main.Ferngesteuert;
import de.paul.sets.StringSet;
import de.paul.utils.Utils;

public class ItemEditorPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;

	private static List<String> allItems;

	public ItemEditorPanel() {
		super(null);
		JButton exploreButton = new JButton("Open Folder");
		exploreButton.setBounds(10, 260, 130, 30);
		
		JButton newSet = new JButton("New Set");
		newSet.setBounds(145, 240, 80, 15);
		
		JButton delSet = new JButton("Del. Set");
		delSet.setBounds(145, 260, 80, 15);

		allItems = getNames(Ferngesteuert.get().getItemSounds());
		
		JLabel availableItemsLabel = new JLabel("Available Items", SwingConstants.CENTER);
		availableItemsLabel.setFont(medium);
		availableItemsLabel.setBounds(10, 10, 130, 20);
		
		JLabel itemSetLabel = new JLabel("Item Sets", SwingConstants.CENTER);
		itemSetLabel.setFont(medium);
		itemSetLabel.setBounds(150, 10, 70, 20);
		
		JLabel containedItemsLabel = new JLabel("Contained Items", SwingConstants.CENTER);
		containedItemsLabel.setFont(medium);
		containedItemsLabel.setBounds(230, 10, 160, 20);

		final JTextField itemSearch = new JTextField();
		itemSearch.setBounds(10, 40, 130, 25);

		final DefaultListModel<String> availableItemsModel = new DefaultListModel<>();
		for (String item : allItems) {
			availableItemsModel.addElement(item);
		}
		final JList<String> availableItems = new JList<>(availableItemsModel);
		availableItems.setDragEnabled(true);
		availableItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane availableItemsPane = new JScrollPane(availableItems);
		availableItemsPane.setBounds(10, 70, 130, 170);
		
		JLabel addInfo = new JLabel("Click to Add", SwingConstants.CENTER);
		addInfo.setBounds(10, 240, 130, 20);

		List<StringSet> itemSets = Ferngesteuert.get().getItemsSets();
		final DefaultListModel<StringSet> itemsListModel = new DefaultListModel<>();
		for (StringSet set : itemSets) {
			itemsListModel.addElement(set);
		}
		final JList<StringSet> itemsList = new JList<>(itemsListModel);
		itemsList.setDragEnabled(true);
		itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane itemsListPane = new JScrollPane(itemsList);
		itemsListPane.setBounds(150, 40, 70, 200);

		final DefaultListModel<String> containedItemsModel = new DefaultListModel<>();
		JList<String> containedItems = new JList<>(containedItemsModel);
		containedItems.setDragEnabled(true);
		containedItems.setSelectionModel(new DefaultListSelectionModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void setSelectionInterval(int index0, int index1) {
				String del = containedItemsModel.remove(index0);
				itemsList.getSelectedValue().getList().remove(del);
			}
		});
		containedItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane containedItemsPane = new JScrollPane(containedItems);
		containedItemsPane.setBounds(230, 40, 160, 200);

		JLabel removeInfo = new JLabel("Click to Remove", SwingConstants.CENTER);
		removeInfo.setBounds(230, 240, 160, 20);

		itemsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				StringSet set = itemsList.getSelectedValue();
				if (set != null) {
					containedItemsModel.removeAllElements();
					for (int i = 0; i < set.getSize(); i++) {
						containedItemsModel.addElement(set.get(i));
					}
				}
			}
		});

		exploreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(Ferngesteuert.itemPath));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		availableItems.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!availableItems.isSelectionEmpty()) {
					int index = e.getLastIndex();
					String selected = availableItemsModel.get(index);
					containedItemsModel.addElement(selected);
					itemsList.getSelectedValue().add(selected);
					availableItems.clearSelection();
				}
			}
		});

		itemSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			private void update() {
				String search = itemSearch.getText();
				availableItemsModel.removeAllElements();
				for (int i = 0; i < allItems.size(); i++) {
					String item = allItems.get(i);
					if (item.toLowerCase().contains(search.toLowerCase())) {
						availableItemsModel.addElement(item);
					}
				}
			}
		});
		
		newSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Enter a Name");
				name = Utils.normWithSpace(name);
				if (name != null) {
					StringSet set = new StringSet(null, name);
					itemsListModel.addElement(set);
					Ferngesteuert.get().addItemSet(set);
				}
			}
		});
		
		delSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSet set = itemsList.getSelectedValue();
				itemsListModel.removeElement(set);
				Ferngesteuert.get().removeItemSet(set);
			}
		});

		add(exploreButton);
		add(itemSearch);
		add(availableItemsPane);
		add(itemsListPane);
		add(containedItemsPane);
		add(removeInfo);
		add(availableItemsLabel);
		add(itemSetLabel);
		add(containedItemsLabel);
		add(addInfo);
		add(newSet);
		add(delSet);
		itemsList.setSelectedIndex(0);
	}

	private static List<String> getNames(List<File> files) {
		List<String> list = new ArrayList<>();
		for (File file : files) {
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.length() - 4);
			list.add(fileName);
		}
		return list;
	}
}
