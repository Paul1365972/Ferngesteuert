package de.paul.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import de.paul.main.Ferngesteuert;
import de.paul.manager.IngameKeyListener;

public class MyKeyOption extends JButton implements NativeKeyListener {
	private static final long serialVersionUID = 1L;

	private int keycode;
	private String name;
	private KeyOption keyOption;

	public MyKeyOption(String name, int keycode, KeyOption keyOption) {
		super();
		this.keycode = keycode;
		this.name = name;
		this.keyOption = keyOption;
		updateText();
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = Ferngesteuert.get().getKeyManager().setNativeKeyListener(MyKeyOption.this);
				System.out.println("Clicked");
				if (!success)
					setKey(NativeKeyEvent.VC_UNDEFINED);
			}
		});
	}

	public void updateText() {
		String keyname = "None";
		if (keycode != NativeKeyEvent.VC_UNDEFINED)
			keyname = NativeKeyEvent.getKeyText(keycode);
		setText(name + ": " + keyname);
	}

	public void setKey(int keycode) {
		System.out.println("Set Key: " + keycode);
		Ferngesteuert.get().getKeyManager().setNativeKeyListener(new IngameKeyListener());
		this.keycode = keycode;
		switch (keyOption) {
		case NextItem:
			Ferngesteuert.get().getOptions().setNextItemKey(keycode);
			break;
		case NextQuest:
			Ferngesteuert.get().getOptions().setNextQuestKey(keycode);
			break;
		case Start:
			Ferngesteuert.get().getOptions().setStartKey(keycode);
			break;
		default:
			break;
		}
		updateText();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		setKey(e.getKeyCode());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
}
