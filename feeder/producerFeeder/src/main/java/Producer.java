import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import core.MongoDBConnector;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Benjamin on 21/01/2016.
 */
public class Producer implements Runnable {

	MongoDBConnector dbConnector = MongoDBConnector.getInstance();

	public boolean isRunning = true;

	public void run() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("test");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			while(isRunning) {
				JsonArray trackingRequests = dbConnector.findAllTrackingRequest();

				if(trackingRequests.size() == 0) {
					System.out.println("Nothing to do here, lets sleep for a while ...");
				}
				else {
					for(JsonElement currentTrackingRequest : trackingRequests) {
						String text = currentTrackingRequest.toString();
						TextMessage message = session.createTextMessage(text);
						System.out.println("Sending tracking request : " + text);
						producer.send(message);
					}
				}
				Thread.sleep(10000);
			}

			/*
			// Create a messages
			String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();

			// Tell the producer to send the message

			for (int i = 0; i < 1500; i++) {
				TextMessage message = session.createTextMessage(text);
				System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
				producer.send(message);

			}
			*/

			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
