package de.paul.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import de.paul.main.Ferngesteuert;
import de.paul.main.Options;

public class TimeManager {

	private Timer timer;
	private Options options;

	public TimeManager() {
		System.out.println("Init TimeManager");
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int time = Ferngesteuert.get().getTime();
				time++;
				fireEvents(time);
			}
		});
		options = Ferngesteuert.get().getOptions();
		System.out.println("Finished");
	}
	
	private void fireEvents(int time) {
		if (time >= options.getInterval()) {
			Ferngesteuert.get().resetTimer();
			Ferngesteuert.get().nextQuest();
		} else {
			Ferngesteuert.get().setTime(time);
		}
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}
}
