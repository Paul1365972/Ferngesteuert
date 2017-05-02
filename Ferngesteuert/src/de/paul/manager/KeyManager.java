package de.paul.manager;

import java.util.logging.LogManager;
//import java.util.logging.Logger;
//import java.util.logging.Level;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyManager {

	private NativeKeyListener currentNativeKeyListener;

	public KeyManager() {
		System.out.println("Init KeyManager");
		LogManager.getLogManager().reset();
		//Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		//logger.setLevel(Level.INFO);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Finished");
	}

	public boolean setNativeKeyListener(NativeKeyListener nativeKeyListener) {
		if (nativeKeyListener == currentNativeKeyListener) {
			return false;
		}
		GlobalScreen.removeNativeKeyListener(currentNativeKeyListener);
		GlobalScreen.addNativeKeyListener(nativeKeyListener);
		currentNativeKeyListener = nativeKeyListener;
		return true;
	}
}
