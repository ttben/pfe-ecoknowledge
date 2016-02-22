package fr.unice.polytech.ecoknowledge.domain.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TestUtils {
	public static JsonObject getFakeJson(String filename) throws Exception {
		BufferedReader br = null;
		URL url = ClassLoader.getSystemClassLoader().getResource(filename);

		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			return new JsonParser().parse(br).getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		throw new Exception();
	}
}
