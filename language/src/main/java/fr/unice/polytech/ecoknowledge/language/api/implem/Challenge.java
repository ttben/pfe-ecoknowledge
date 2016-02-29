package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.config.AddressReacher;
import fr.unice.polytech.ecoknowledge.language.api.util.HTTPCall;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class Challenge {

	// Configs to end the request
	private static final String path = "challenges";

	// Attributes
	JSONObject description = null;
	ChallengeBuilder cb = null;


	Challenge(JSONObject description, ChallengeBuilder cb) {
		this.description = description;
		this.cb = cb;
	}

	public static ChallengeBuilder create(String name) {
		return new ChallengeBuilder(name);
	}

	void send() {
		String IPAddress = AddressReacher.getAddress();
		System.out.println("/----- Generating description -----/");
		description = JSONBuilder.parse(cb);
		if (IPAddress != null) {
			Response r = HTTPCall.POST(IPAddress, path, description);
			System.out.println(r.getStatus() == 200 ?
					"\t---> Success sending the challenge" +
							"\nResult :\n" + r.readEntity(String.class)
					: "\t---> Challenge Failed : \n\t" + r.getStatusInfo());
		}
	}

	JSONObject getDescription() {
		return description;
	}
}
