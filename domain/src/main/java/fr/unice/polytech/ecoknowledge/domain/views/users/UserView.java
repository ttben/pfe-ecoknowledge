package fr.unice.polytech.ecoknowledge.domain.views.users;

import com.google.gson.JsonObject;
import fr.unice.polytech.ecoknowledge.domain.model.User;
import fr.unice.polytech.ecoknowledge.domain.views.ViewForClient;

/**
 * Created by Sébastien on 02/12/2015.
 */
public class UserView implements ViewForClient {

    private String firstName;
    private String lastName;
    private String profilePic;
    private String id;

    public UserView(User user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        profilePic = user.getPicUrl();
        id = user.getId().toString();
    }

    @Override
    public JsonObject toJsonForClient() {
        JsonObject result = new JsonObject();

        result.addProperty("id", id);
        result.addProperty("firstName", firstName);
        result.addProperty("lastName", lastName);
        if(profilePic != null)
            result.addProperty("profilePic", profilePic);

        return result;
    }
}
