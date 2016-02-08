package fr.unice.polytech.ecoknowledge.domain.views.users;

import com.google.gson.JsonArray;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

import java.util.ArrayList;
import java.util.List;

public class UserViewList implements ViewForClient {
	private List<User> userList = new ArrayList<>();

	public UserViewList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}


	@Override
	public JsonArray toJsonForClient() {
		JsonArray result = new JsonArray();

		for (User currentUser : userList) {
			UserView currentUserView = new UserView(currentUser);
			result.add(currentUserView.toJsonForClient());
		}

		return result;
	}
}
