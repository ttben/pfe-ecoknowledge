package fr.unice.polytech.ecoknowledge.domain.views;

import com.google.gson.JsonObject;

/**
 * Created by Sébastien on 02/12/2015.
 */
public interface ViewForClient {

    JsonObject toJsonForClient();
}
