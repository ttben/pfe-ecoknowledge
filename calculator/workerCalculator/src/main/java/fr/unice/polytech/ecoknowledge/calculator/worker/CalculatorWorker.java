package fr.unice.polytech.ecoknowledge.calculator.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.Cache;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.Calculator;
import fr.unice.polytech.ecoknowledge.calculator.worker.core.views.goals.GoalResult;
import fr.unice.polytech.ecoknowledge.common.worker.Worker;
import fr.unice.polytech.ecoknowledge.data.exceptions.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotReadableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.NotSavableElementException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.model.Goal;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;
import java.net.URL;

public class CalculatorWorker extends Worker {

	public CalculatorWorker(String name, int fakeProcessingTime) {
		super(name, fakeProcessingTime);
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

				// FIXME: 21/02/2016 MongoDBHandler.getInstance().updateGoalResult(currentGoalResult);

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

	@Override
	protected URL getURLProperties() {
		URL url = ClassLoader.getSystemClassLoader().getResource("calculator.properties");
		return url;
	}
}
