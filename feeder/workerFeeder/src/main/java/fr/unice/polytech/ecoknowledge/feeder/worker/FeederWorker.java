package fr.unice.polytech.ecoknowledge.feeder.worker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.unice.polytech.ecoknowledge.common.worker.Worker;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.net.URL;

public class FeederWorker extends Worker {
	public FeederWorker(String name, int fakeProcessingTime) {
		super(name, fakeProcessingTime);
	}

	@Override
	protected URL getURLProperties() {
		URL url = ClassLoader.getSystemClassLoader().getResource("feeder.properties");
		return url;
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println(name + " received: " + text + ".");

				JsonObject trackingRequestJsonObject = new JsonParser().parse(text).getAsJsonObject();
				logger.debug(name + " received tracking request : " + trackingRequestJsonObject);

				String sensorName = trackingRequestJsonObject.get("targetSensor").getAsString();

				String result = new HTTPCaller().sendGet(sensorName);

				JsonObject dataJsonObject = new JsonParser().parse(result).getAsJsonObject();
				MongoDBConnector.getInstance().storeData(dataJsonObject);

				Thread.sleep(fakeProcessingTime);


			} else {
				System.out.println("OTHER Received: " + message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
