package fr.unice.polytech.ecoknowledge.calculator.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Benjamin on 21/01/2016.
 */
public class Producer implements Runnable {

	public static final String DEFAULT_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String DEFAULT_NAME_OF_CALCULATOR_QUEUE = "test";
	public boolean isRunning = true;

	final Logger logger = LogManager.getLogger(Producer.class);

	private MongoDBHandler db = MongoDBHandler.getInstance();
	private int refreshingFrequency;

	private Session session;
	private MessageProducer producer;
	private Connection connection;

	/**
	 *
	 * @param refreshingFrequency
	 * duration in milliseconds between two db reads
	 */
	public Producer(int refreshingFrequency) {
		this.refreshingFrequency = refreshingFrequency;
	}

	public void run() {
		try {
			createProducer();

			while (isRunning) {
				updateGoals(session, producer);
				Thread.sleep(this.refreshingFrequency);
			}

			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	private void createProducer() throws JMSException {
		logger.warn("Creating producer ...");

		Properties prop = getProperties();
		String brokerURL = getBrokerURL(prop);

		String nameOfCalculatorQueue = getNameOfQueue(prop);

		logger.info("Start a connection to the ActiveMQ broker : " + brokerURL);

		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();

		logger.info("Create a session and a queue named " + nameOfCalculatorQueue);

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue(nameOfCalculatorQueue);

		logger.info("Creating a producer in a non persistent message mode");
		// Create a MessageProducer from the Session to the Topic or Queue
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}

	private String getNameOfQueue(Properties prop) {
		logger.warn("Retrieving name of queue to create");
		String queueName = prop.getProperty("queue-name");

		if(queueName == null || queueName.isEmpty()) {
			logger.warn("calculator.properties resource does not contain field queue-name or field is empty ! Using default name:" + DEFAULT_NAME_OF_CALCULATOR_QUEUE);
			queueName = DEFAULT_NAME_OF_CALCULATOR_QUEUE;
		}

		logger.warn("Using queue named : " + queueName);
		return queueName;
	}

	private String getBrokerURL(Properties prop) {
		logger.warn("Retrieving broker URI");
		String brokerURL = prop.getProperty("brokerURI");

		if(brokerURL == null || brokerURL.isEmpty()) {
			logger.warn("calculator.properties resource does not contain field brokerURI or field is empty ! Using default URI:" + DEFAULT_BROKER_URL);
			brokerURL = DEFAULT_BROKER_URL;
		}

		URI url = null;
		try {
			url = new URI(brokerURL);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		logger.warn("Using broker URI: " + url);
		return brokerURL;
	}

	private Properties getProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		String brokerURL = "";

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		input = classLoader.getResourceAsStream("calculator.properties");

		if (input == null) {
			System.err.println("calculator.properties resource not found !+");
			brokerURL = DEFAULT_BROKER_URL;
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.err.println("Malformed calculator configs !");
		}
		return prop;
	}

	private void updateGoals(Session session, MessageProducer producer) throws NotReadableElementException, IncoherentDBContentException, JMSException {
		List<Goal> goalList = db.readAllGoals();

		if (goalList.size() == 0) {
			System.out.println("Nothing to do here, lets sleep for a while ...");
		} else {
			for (Goal currentGoalDescription : goalList) {
				// TODO: 18/02/2016 CHECK IF GOAL MUST BE UPDATED OR NOT
				/*
					This method can be, at least for a start, a 'simple' scheduled task
					that does not check if goal must be updated or not, but simply ask
					to update every goal every 2 minutes (for example)
				 */

				TextMessage message = session.createTextMessage();

				ObjectMapper mapper = new ObjectMapper();
				try {
					String goalStrDescription = mapper.writeValueAsString(currentGoalDescription);
					message.setText(goalStrDescription);
					producer.send(message);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
