package fr.unice.polytech.ecoknowledge.calculator.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.common.producer.Producer;
import fr.unice.polytech.ecoknowledge.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class CalculatorProducer extends Producer {

	private MongoDBHandler db = MongoDBHandler.getInstance();

	int nbOfGoalsToPush;

	public CalculatorProducer(int refreshingFrequency, int nbOfGoalsToPush) {
		super(refreshingFrequency);
		this.nbOfGoalsToPush = nbOfGoalsToPush;
	}

	@Override
	protected URL getURLProperties() {
		URL url = ClassLoader.getSystemClassLoader().getResource("calculator.properties");
		return url;
	}

	@Override
	public void run() {
		try {
			createProducer();

			int nbOfGoalsDone = 0;

			while (isRunning) {
				updateGoals(session, producer);
				Thread.sleep(this.refreshingFrequency);
				if(++nbOfGoalsDone >=  nbOfGoalsToPush) {
					isRunning = false;
				}
			}

			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	private void updateGoals(Session session, MessageProducer producer) throws NotReadableElementException, IncoherentDBContentException, JMSException {
		List<Goal> goalList = db.readAllGoals();

		if (goalList.size() == 0) {
			System.out.println("Nothing to do here, lets sleep for a while ...");
		} else {

			for (Goal currentGoalDescription : goalList) {
				TextMessage message = session.createTextMessage();

				ObjectMapper mapper = new ObjectMapper();
				try {
					String goalStrDescription = mapper.writeValueAsString(currentGoalDescription);
					message.setText(goalStrDescription);
					//logger.warn("Producer sending new message : (" + currentGoalDescription.getId() + ")");
					producer.send(message);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
