package fr.unice.polytech.ecoknowledge.calculator.worker.core.views;

import com.google.gson.JsonElement;

/**
 * Created by Sébastien on 02/12/2015.
 */
public interface ViewForClient {

	JsonElement toJsonForClient();
}
