package fr.unice.polytech.ecoknowledge.feeder.worker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

				JsonObject trackingRequestJsonObject = new JsonParser().parse(text).getAsJsonObject();
				logger.debug(name + " received tracking request : " + trackingRequestJsonObject);

				String sensorName = trackingRequestJsonObject.get("targetSensor").getAsString();

				String result = new HTTPCaller().sendGet(sensorName);

				JsonObject newDataJsonObject = new JsonParser().parse(result).getAsJsonObject();
				JsonObject oldData = MongoDBConnector.getInstance().getSensorData(sensorName);

				logger.debug("OLD DATA FOR " + sensorName + " are " + oldData);

				JsonObject mergeDataJsonObject = new JsonObject();

				if(oldData != null) {
					mergeDataJsonObject.addProperty("id", sensorName);

					JsonArray oldValuesArrayForGivenSensor = oldData.getAsJsonArray("values");
					JsonArray newValuesArrayForGivenSensor = newDataJsonObject.getAsJsonArray("values");

					for (int newValueIndex = 0; newValueIndex < newValuesArrayForGivenSensor.size(); newValueIndex++) {
						JsonObject currentNewElement = newValuesArrayForGivenSensor.get(newValueIndex).getAsJsonObject();
						long currentNewDate = currentNewElement.get("date").getAsLong();

						boolean newValue = true;

						for (int oldValueIndex = 0; oldValueIndex < oldValuesArrayForGivenSensor.size(); oldValueIndex++) {
							JsonObject currentOldElement = oldValuesArrayForGivenSensor.get(oldValueIndex).getAsJsonObject();
							long currentOldDate = currentOldElement.get("date").getAsLong();
							if (currentNewDate == currentOldDate) {
								newValue = false;
								break;
							}
						}

						if (newValue) {
							oldValuesArrayForGivenSensor.add(currentNewElement);
						}

					}

					mergeDataJsonObject.add("values", oldValuesArrayForGivenSensor);

				} else {
					mergeDataJsonObject = newDataJsonObject;
				}


				MongoDBConnector.getInstance().storeData(mergeDataJsonObject);

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
