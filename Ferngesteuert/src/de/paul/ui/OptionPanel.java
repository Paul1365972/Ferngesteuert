package de.paul.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.paul.components.AbstractPanel;
import de.paul.components.KeyOption;
import de.paul.components.MyKeyOption;
import de.paul.main.Ferngesteuert;
import de.paul.main.Options;

public class OptionPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;

	public OptionPanel() {
		super(null);
		final Options o = Ferngesteuert.get().getOptions();
		
		final JCheckBox questSound = new JCheckBox("Quest Sound", o.isSoundQuests());
		questSound.setBounds(10, 10, 200, 20);
		
		final JCheckBox itemSound = new JCheckBox("Item Sound", o.isSoundItems());
		itemSound.setBounds(10, 30, 200, 20);
		
		final JCheckBox hotkeyActive = new JCheckBox("Hotkey Active", o.isHotkeyActive());
		hotkeyActive.setBounds(10, 50, 200, 20);
		
		final JCheckBox resetInterval = new JCheckBox("Reset Interval on Quest Hotkey", o.isHotkeyActive());
		resetInterval.setBounds(10, 70, 200, 20);

		MyKeyOption keyNextItem = new MyKeyOption("Next Item Key", o.getNextItemKey(), KeyOption.NextItem);
		keyNextItem.setBounds(10, 100, 200, 30);

		MyKeyOption keyNextQuest = new MyKeyOption("Next Quest Key", o.getNextQuestKey(), KeyOption.NextQuest);
		keyNextQuest.setBounds(10, 140, 200, 30);

		MyKeyOption keyStart = new MyKeyOption("Start/Stop Key", o.getStartKey(), KeyOption.Start);
		keyStart.setBounds(10, 180, 200, 30);

		final JSlider volumeSlider = new JSlider(-50, 6, o.getVolume());
		volumeSlider.setBounds(220, 10, 170, 30);

		final JLabel volumeLabel = new JLabel("Volume: +".concat(String.valueOf(o.getVolume())).concat("db"), SwingConstants.CENTER);
		volumeLabel.setBounds(220, 40, 170, 20);

		final JTextField intervalField = new JTextField(String.valueOf(o.getInterval()));
		intervalField.setBounds(220, 70, 170, 30);
		
		final JLabel intervalLabel = new JLabel("Quest every ".concat(String.valueOf(o.getInterval())).concat("s"));
		intervalLabel.setBounds(220, 100, 170, 20);

		intervalField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateInterval();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateInterval();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			private void updateInterval() {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						int before = o.getInterval();
						int i = 0;
						if (!intervalField.getText().isEmpty()) {
							try {
								i = Integer.valueOf(intervalField.getText());
							} catch (Exception e2) {
								setText(String.valueOf(o.getInterval()));
								return;
							}
						}
						if (before == i)
							return;
						o.setInterval(Math.min(15*60, Math.max(0, i)));
						setText(String.valueOf(o.getInterval()));
					}

					private void setText(String text) {
						intervalField.setText(text);
						intervalLabel.setText("Quest every: ".concat(text).concat("s"));
					}
				});
			}
		});

		

		questSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				o.setSoundQuests(questSound.isSelected());
			}
		});

		itemSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				o.setSoundItems(itemSound.isSelected());
			}
		});
		
		hotkeyActive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				o.setHotkeyActive(hotkeyActive.isSelected());
			}
		});
		
		resetInterval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				o.setResetInterval(resetInterval.isSelected());
			}
		});

		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				volumeLabel.setText("Volume: +".concat(String.valueOf(volumeSlider.getValue())).concat("db"));
				Ferngesteuert.get().getOptions().setVolume(volumeSlider.getValue());
			}
		});
		
		add(questSound);
		add(itemSound);
		add(keyNextItem);
		add(keyNextQuest);
		add(keyStart);
		add(volumeSlider);
		add(volumeLabel);
		add(intervalField);
		add(intervalLabel);
		add(hotkeyActive);
		add(resetInterval);
	}
}
