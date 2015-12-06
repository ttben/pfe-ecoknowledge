package fr.unice.polytech.ecoknowledge.domain.views.challenges;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Challenge;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 06/12/2015.
 */
public class ChallengeViewList implements ViewForClient {

	private List<Challenge> challengeList = new ArrayList<>();

	public ChallengeViewList(List<Challenge> challengeList) {
		this.challengeList = challengeList;
	}

	public List<Challenge> getChallengeList() {
		return challengeList;
	}

	public void setChallengeList(List<Challenge> challengeList) {
		this.challengeList = challengeList;
	}

	@Override
	public JsonElement toJsonForClient() {
		JsonArray result = new JsonArray();

		for (Challenge currentChallenge : challengeList) {
			JsonObject currentChallengeJsonToClient = new ChallengeView(currentChallenge).toJsonForClient();
			result.add(currentChallengeJsonToClient);
		}

		return result;
	}
}
