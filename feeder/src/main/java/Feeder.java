import fr.unice.polytech.ecoknowledge.domain.data.EcoknowledgeDataHandler;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Benjamin on 14/01/2016.
 */
public class Feeder implements Runnable {

	EcoknowledgeDataHandler ecoknowledgeDataHandler = MongoDBHandler.getInstance();

	static int number = 1;

	int current = 0;

	Feeder() {
		current = ++number;
	}

	@Override
	public void run() {
		URLConnection connection = null;
		try {
			URL oracle = new URL("http://www.oracle.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			}


			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.printf("Thread [%d] - Je suis en train de ruuuuuun\n", current);

	}
}
