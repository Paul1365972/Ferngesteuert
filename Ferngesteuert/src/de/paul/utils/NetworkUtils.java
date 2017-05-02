package de.paul.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import de.paul.main.Ferngesteuert;

public class NetworkUtils {

	public static void downloadMP3(String name) {
		System.out.println("Download");
		try {
			String urlParameters = "text=" + name.replace(' ', '+') + "&lang=en";
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = "http://soundoftext.com/sounds";
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.addRequestProperty("Accept", "*/*");
			conn.addRequestProperty("Accept-Encoding", "gzip,deflate");
			conn.addRequestProperty("Accept-Language", "de,en-US;q=0.8,en;q=0.6,en-GB;q=0.4");
			conn.addRequestProperty("Cache-Control", "no-cache");
			conn.addRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("DNT", "1");
			conn.addRequestProperty("Host", "http://soundoftext.com");
			conn.setRequestProperty("Pragma", "no-cache");
			conn.setRequestProperty("Referer", "http://soundoftext.com/");
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
			conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}
			System.out.println(conn.getResponseCode());
			InputStream in = new URL("http://soundoftext.com/static/sounds/en/" + name.replace(' ', '_') + ".mp3").openStream();
			File file = new File(Ferngesteuert.soundsQuestsPath.concat("\\" + name + ".mp3"));
			FileOutputStream fos = new FileOutputStream(file);
			int length = -1;
			byte[] buffer = new byte[1024];// buffer for portion of data from
			// connection
			while ((length = in.read(buffer)) > -1) {
				fos.write(buffer, 0, length);
			}
			fos.close();
			in.close();
			Ferngesteuert.get().getQuestSounds().add(file);
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
}
