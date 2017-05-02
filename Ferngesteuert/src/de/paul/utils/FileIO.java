package de.paul.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileIO {

	public static void storeText(String path, String[] text) {
		System.out.println("Store (" + path + "): " + Arrays.toString(text));
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			PrintStream ps = new PrintStream(f);
			for (String line : text) {
				ps.println(line);
			}
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] loadText(String path, String[] defaultText) {
		System.out.println("Load (" + path + "): " + Arrays.toString(defaultText));
		try {
			File f = new File(path);
			if (!f.exists()) {
				storeText(path, defaultText);
			}
			List<String> lines = new ArrayList<>();

			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			String[] ret = new String[lines.size()];
			lines.toArray(ret);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void copy(InputStream is, String path) {
		File file = new File(path);
		try {
			if (!file.exists())
				file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				fos.write(bytes, 0, read);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
