package de.paul.resources;

import java.io.InputStream;

public class Resources {
	
	public static InputStream get(String name) {
		return Resources.class.getResourceAsStream(name);
	}
}
