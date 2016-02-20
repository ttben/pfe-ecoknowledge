package fr.unice.polytech.ecoknowledge.calculator.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.data.exceptions.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.calculator.Cache;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
import fr.unice.polytech.ecoknowledge.domain.views.goals.GoalResult;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by Benjamin on 21/01/2016.
 */
public class Worker implements Runnable, ExceptionListener, MessageListener {
	static int number = 0;
	private String name;
	private int fakeProcessingTime;

	final Logger logger = LogManager.getLogger(Worker.class);


	public Worker(String name, int fakeProcessingTime) {
		this.name = name;

		this.fakeProcessingTime = fakeProcessingTime;
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occurred.  Shutting down client.");
	}

	public void onMessage(Message message) {

		try {
			if (message instanceof TextMessage) {
				TextMessage objectMessage = (TextMessage) message;
				String obj = objectMessage.getText();

				ObjectMapper mapper = new ObjectMapper();
				Goal goal = mapper.readValue(obj, Goal.class);

				System.out.println(name + " received: " + goal.getId() + ". Now sleeping for " + fakeProcessingTime + " ms");
				Calculator calculator = new Calculator(new Cache());
				GoalResult currentGoalResult = calculator.evaluate(goal);

				MongoDBHandler.getInstance().updateGoalResult(currentGoalResult);

				Thread.sleep(fakeProcessingTime);

			} else {
				System.out.println("OTHER Received: " + message);
			}
		}
		// TODO: 18/02/2016 LOG AND HANDLE IT
		catch (JMSException | InterruptedException | IOException | GoalNotFoundException
				| UserNotFoundException | NotSavableElementException | NotReadableElementException e) {

			String errorDescription = "A calculator worker just raised an error ";
			errorDescription = errorDescription.concat(e.getMessage());
			errorDescription = errorDescription.concat("Caused by");
			errorDescription = errorDescription.concat(e.getMessage());

			logger.fatal(errorDescription);
			e.printStackTrace();

			throw new IllegalArgumentException(errorDescription);

		}
	}

	public void run() {
		try {

			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			connection.setExceptionListener(this);

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("test");

			// Create a MessageConsumer from the Session to the Topic or Queue
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);

		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
