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
import fr.unice.polytech.ecoknowledge.domain.data.MongoDBHandler;
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

				String logs = name + " received goal (" + goal.getId() + ") user (" + goal.getUser().getId()+")";
				logger.warn(logs);


				Calculator calculator = new Calculator(new Cache());
				GoalResult currentGoalResult = calculator.evaluate(goal);

				currentGoalResult.setId(goal.getId());

				//	Storing goal result
				MongoDBHandler.getInstance().getBddConnector()
						.updateGoalResult(currentGoalResult.toJsonForClient());

				//	Set goal result of goal
				goal.setGoalResultID(currentGoalResult.getId());
				MongoDBHandler.getInstance().updateGoal(goal);

				Thread.sleep(fakeProcessingTime);

			} else {
				System.out.println("OTHER Received: " + message);
			}
		}
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
