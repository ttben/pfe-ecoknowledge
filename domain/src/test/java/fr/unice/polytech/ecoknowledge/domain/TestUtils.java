package fr.unice.polytech.ecoknowledge.domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Benjamin on 30/11/2015.
 */
public class TestUtils {
	public static JsonObject getFakeJson(int filenumber) {
		BufferedReader br = null;
		String result = "";

		try {

			String currentLine;

			br = new BufferedReader(new FileReader("./src/test/java/fr/unice/polytech/ecoknowledge/domain/challenge-example-sample"
					+ filenumber +".json"));

			while ((currentLine = br.readLine()) != null) {
				result = result.concat(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return new JsonParser().parse(result).getAsJsonObject();
	}
}
