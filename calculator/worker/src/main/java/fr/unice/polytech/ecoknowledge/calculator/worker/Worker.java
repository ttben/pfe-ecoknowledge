package fr.unice.polytech.ecoknowledge.calculator.worker;

import fr.unice.polytech.ecoknowledge.data.exceptions.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.calculator.Calculator;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;
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
		System.out.println("JMS Exception occured.  Shutting down client.");
	}

	public void onMessage(Message message) {

		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				Object obj = objectMessage.getObject();

				if(!(obj instanceof Goal)) {
					String errorDescription = "A calculator worker just received a non Goal object: ";
					errorDescription = errorDescription.concat(obj.getClass().getName());

					logger.fatal(errorDescription);

					throw new IllegalArgumentException(errorDescription);
				}

				Goal goal = (Goal) obj;

				System.out.println(name + " received: " + goal.getId() + ". Now sleeping for " + fakeProcessingTime + " ms");
				Calculator calculator = new Calculator(null);
				calculator.evaluate(goal);

				Thread.sleep(fakeProcessingTime);

			} else {
				System.out.println("OTHER Received: " + message);
			}
		}
		// TODO: 18/02/2016 LOG AND HANDLE IT
		catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GoalNotFoundException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (NotSavableElementException e) {
			e.printStackTrace();
		} catch (NotReadableElementException e) {
			e.printStackTrace();
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
