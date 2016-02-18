package fr.unice.polytech.ecoknowledge.language.api.implem;

import fr.unice.polytech.ecoknowledge.language.api.implem.enums.DURATION_TYPE;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeable;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IChallengeableIcon;
import fr.unice.polytech.ecoknowledge.language.api.interfaces.IDurationnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sébastien on 25/11/2015.
 */
public class ChallengeBuilder implements IChallengeableIcon {

	// ------- FIELDS ------- //

	// Specific fields of the challenge builder
	private String name;
	private String iconURL = null;
	private Period p = null;
	private Integer time = null;
	private DURATION_TYPE type = null;
	private List<Level> levels = new ArrayList<>();


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

	Challenge end() {
		return new Challenge(JSONBuilder.parse(this), this);
	}

	// ------- Accessors ------- //

	void addPeriod(Period period) {
		p = period;
	}

	private void reinit() {
		p = null;
		time = null;
		type = null;
	}

	Period getP() {
		return p;
	}

	Integer getTime() {
		return time;
	}

	void setTime(Integer time) {
		this.time = time;
	}

	DURATION_TYPE getType() {
		return type;
	}

	void setType(DURATION_TYPE type) {
		this.type = type;
	}

	String getName() {
		return name;
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
