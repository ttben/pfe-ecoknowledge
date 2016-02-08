package fr.unice.polytech.ecoknowledge.domain.views.users;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.model.challenges.Badge;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 02/12/2015.
 */
public class UserView implements ViewForClient {

	private String firstName;
	private String lastName;
	private String profilePic;
	private String id;
	private List<Badge> badges = new ArrayList<>();

	public UserView(User user) {
		firstName = user.getFirstName();
		lastName = user.getLastName();
		profilePic = user.getPicUrl();
		id = user.getId().toString();

		if(user.getBadges() != null) {
			badges = new ArrayList<>(user.getBadges());
		}

	}

	@Override
	public JsonObject toJsonForClient() {

		JsonObject result = new JsonObject();

		result.addProperty("id", id);
		result.addProperty("firstName", firstName);
		result.addProperty("lastName", lastName);
		if (profilePic != null)
			result.addProperty("profilePic", profilePic);

		int points = 1;

		JsonArray badgesJsonArray = new JsonArray();
		for(Badge currentBadge : this.badges) {
			JsonObject currentBadgeJsonDescription = new JsonObject();
			// TODO: 04/12/2015 badgeview
			currentBadgeJsonDescription.addProperty("name", currentBadge.getName());
			currentBadgeJsonDescription.addProperty("points", currentBadge.getReward());
			currentBadgeJsonDescription.addProperty("image", currentBadge.getImage());

			badgesJsonArray.add(currentBadgeJsonDescription);
			points += currentBadge.getReward();
		}

		result.addProperty("points", points);
		//result.add("badges", badgesJsonArray);

		return result;
	}
}
