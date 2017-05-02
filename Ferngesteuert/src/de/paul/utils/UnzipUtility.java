package de.paul.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtility {

	private static byte[] buffer = new byte[1024];

	public static void unZipIt(InputStream is, InputStream is2, String destination) {
		try {
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {
				if (!ze.isDirectory() && !ze.getName().equalsIgnoreCase("/")) {
					File file = new File(destination + "/" + ze.getName());
					File dirs = new File(file.getParent());
					dirs.mkdirs();
					file.createNewFile();
					write(zis, destination + "/" + ze.getName());
					System.out.println("Created File: " + destination + "/" + ze.getName());
				}
				ze = zis.getNextEntry();
			}
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(ZipInputStream zis, String destination) throws IOException {
		FileOutputStream fos = new FileOutputStream(destination);
		int len;
		while ((len = zis.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
	}
}
