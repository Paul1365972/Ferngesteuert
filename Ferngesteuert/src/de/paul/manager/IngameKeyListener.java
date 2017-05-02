package de.paul.manager;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import de.paul.main.Ferngesteuert;
import de.paul.main.Options;

public class IngameKeyListener implements NativeKeyListener {

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		Options o = Ferngesteuert.get().getOptions();
		int keycode = e.getKeyCode();
		if (o.isHotkeyActive() && keycode != NativeKeyEvent.VC_UNDEFINED) {
			if (keycode == o.getNextItemKey()) {
				Ferngesteuert.get().nextItem();
			} else if (keycode == o.getNextQuestKey()) {
				Ferngesteuert.get().nextQuest();
				Ferngesteuert.get().resetTimer();
			} else if (keycode == o.getStartKey()) {
				Ferngesteuert.get().setRunning(!Ferngesteuert.get().isRunning());
			}
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

}
