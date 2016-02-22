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

	public CalculatorProducer(int refreshingFrequency) {
		super(refreshingFrequency);
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
