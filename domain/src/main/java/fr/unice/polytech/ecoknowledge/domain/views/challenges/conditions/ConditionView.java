package fr.unice.polytech.ecoknowledge.domain.views.challenges.conditions;

import com.google.gson.JsonObject;

/**
 * Created by Benjamin on 30/11/2015.
 */
public interface ConditionView {
	JsonObject toJsonForClient();
}
