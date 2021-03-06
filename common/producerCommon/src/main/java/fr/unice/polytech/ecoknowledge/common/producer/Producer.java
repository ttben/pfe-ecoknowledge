package fr.unice.polytech.ecoknowledge.common.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Benjamin on 21/01/2016.
 */
public abstract class Producer implements Runnable {

	public static final String DEFAULT_BROKER_URL = "tcp://127.0.0.1:61616";
	public static final String DEFAULT_QUEUE_NAME = "default-queue-name";
	public boolean isRunning = true;

	protected final Logger logger = LogManager.getLogger(Producer.class);

	protected int refreshingFrequency;

	protected Session session;
	protected MessageProducer producer;
	protected Connection connection;

	protected URL urlToResourceFile;

	/**
	 * @param refreshingFrequency duration in milliseconds between two db reads
	 */
	public Producer(int refreshingFrequency) {
		this.refreshingFrequency = refreshingFrequency;
	}

	protected void createProducer() throws JMSException, IOException {
		logger.warn("Creating producer "  + getClass().getSimpleName() + " ...");

		Properties prop = getProperties();
		String brokerURL = getBrokerURL(prop);

		String nameOfCalculatorQueue = getNameOfQueue(prop);

		logger.info("Start a connection to the ActiveMQ broker : " + brokerURL);

		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

		// Create a Connection
		connection = connectionFactory.createQueueConnection();
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

	protected abstract URL getURLProperties();

	protected Properties getProperties() throws IOException {
		urlToResourceFile = getURLProperties();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlToResourceFile.openStream()));

		Properties prop = new Properties();

		if (bufferedReader == null) {
			System.err.println(urlToResourceFile.getPath() + " resource not found !+");
			throw new FileNotFoundException(urlToResourceFile.getPath());
		}

		prop.load(bufferedReader);

		return prop;
	}

	private String getNameOfQueue(Properties prop) {
		logger.warn("Retrieving name of queue to create");
		String queueName = prop.getProperty("queue-name");

		if (queueName == null || queueName.isEmpty()) {
			logger.warn(urlToResourceFile.getPath() + " resource does not contain field queue-name or field is empty ! Using default name:" + DEFAULT_QUEUE_NAME);
			queueName = DEFAULT_QUEUE_NAME;
		}

		logger.warn("Using queue named : " + queueName);
		return queueName;
	}

	private String getBrokerURL(Properties prop) {
		logger.warn("Retrieving broker URI");
		String brokerURL = prop.getProperty("brokerURI");

		if (brokerURL == null || brokerURL.isEmpty()) {
			logger.warn(urlToResourceFile.getPath() + " resource does not contain field brokerURI or field is empty ! Using default URI:" + DEFAULT_BROKER_URL);
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
}
