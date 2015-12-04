package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.config.AddressReacher;
import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeableIcon;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;
import fr.unice.polytech.ecoknowledge.language.api.util.HTTPCall;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IChallengeableIcon {

	// ------- FIELDS ------- //

	// Configs to end the request
	private static final String path = "challenges";
	private boolean send = true;

	// Specific fields of the challenge builder
	private String name;
	private String iconURL = null;
	private Period p = null;
	private Integer time = null;
	private DURATION_TYPE type = null;
	private List<Level> levels = new ArrayList<>();

	// Output
	private JSONObject description = null;


	// ------- CONSTRUCTOR ------- //

	public ChallengeBuilder(String name) {
		this.name = name;
	}


	// ------- API Methods ------- //

	@Override
	public IDurationnable availableFrom(int day) {
		reinit();
		Period p = new Period(this, day);
		return p;
	}

	@Override
	public IDurationnable availableFrom(int day, int month) {
		reinit();
		Period p = new Period(this, day, month);
		return p;
	}

	@Override
	public IDurationnable availableFrom(int day, int month, int year) {
		reinit();
		Period p = new Period(this, day, month, year);
		return p;
	}

	@Override
	public IChallengeable withIcon(String url) {
		this.iconURL = url;
		return this;
	}

	void end() {
		String IPAddress = AddressReacher.getAddress();
		System.out.println("/----- Generating description -----/");
		description = JSONBuilder.parse(this);
		if (IPAddress != null && send) {
			Response r = HTTPCall.POST(IPAddress, path, description);
			System.out.println(r.getStatus() == 200 ?
					"\t---> Success sending the challenge" +
							"\nResult :\n" + r.readEntity(String.class)
					: "\t---> Challenge Failed : \n\t" + r.getStatusInfo());
		}
	}

	// ------- Accessors ------- //

	void addPeriod(Period period) {
		p = period;
	}

	// For tests
	ChallengeBuilder dontSend() {
		send = false;
		return this;
	}

	private void reinit() {
		p = null;
		time = null;
		type = null;
		description = null;
	}

	Period getP() {
		return p;
	}

	Integer getTime() {
		return time;
	}

	DURATION_TYPE getType() {
		return type;
	}

	String getName() {
		return name;
	}

	JSONObject getDescription() {
		return description;
	}

	void setTime(Integer time) {
		this.time = time;
	}

	void setType(DURATION_TYPE type) {
		this.type = type;
	}

	String getIcon() {
		return iconURL;
	}

	List<Level> getLevels() {
		return levels;
	}

	void addLevel(Level level) {
		levels.add(level);
	}
}
