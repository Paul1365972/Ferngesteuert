package de.paul.sets;

import java.io.File;
import java.util.List;

public class Loader {

	public static void loadStringSets(String path, List<StringSet> ref) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		for (File f : dir.listFiles()) {
			if (!isExtension(f.getPath(), "txt"))
				continue;
			String name = f.getName();
			name = name.substring(0, name.length() - 4);
			ref.add(new StringSet(f.getPath(), name));
		}
	}

	public static void loadSounds(File dir, List<File> sounds) {
		if (!dir.exists())
			dir.mkdirs();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				loadSounds(f, sounds);
			}else{
				if (!isExtension(f.getPath(), "mp3"))
					continue;
				sounds.add(f);
			}
		}
	}
	
	private static boolean isExtension(String path, String end) {
		String extension = "";
		int i = path.lastIndexOf('.');
		int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
		if (i > p) {
			extension = path.substring(i + 1);
		}
		return extension.equalsIgnoreCase(end);
	}
}