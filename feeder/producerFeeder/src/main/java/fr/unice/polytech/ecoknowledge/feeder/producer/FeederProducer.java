package fr.unice.polytech.ecoknowledge.feeder.producer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fr.unice.polytech.ecoknowledge.common.producer.Producer;
import fr.unice.polytech.ecoknowledge.data.core.MongoDBConnector;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.net.URL;

public class FeederProducer extends Producer {

	public boolean isRunning = true;
	MongoDBConnector dbConnector = MongoDBConnector.getInstance();

	public FeederProducer(int refreshingFrequency) {
		super(refreshingFrequency);
	}

	@Override
	protected URL getURLProperties() {
		URL url = ClassLoader.getSystemClassLoader().getResource("feeder.properties");
		return url;
	}

	@Override
	public void run() {
		try {
			createProducer();

			while (isRunning) {
				updateSensorData();
				Thread.sleep(refreshingFrequency);
			}

			// Clean up
			session.close();
			connection.close();

		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	private void updateSensorData() throws JMSException {
		JsonArray trackingRequests = dbConnector.findAllTrackingRequest();

		if (trackingRequests.size() == 0) {
			logger.debug(getClass().getSimpleName() + " Nothing to do here, lets sleep for a while ...");
		} else {
			for (JsonElement currentTrackingRequest : trackingRequests) {
				String text = currentTrackingRequest.toString();
				TextMessage message = session.createTextMessage(text);
				logger.debug(getClass().getSimpleName() + " Sending tracking request : " + text);
				producer.send(message);
			}
		}
	}

}
