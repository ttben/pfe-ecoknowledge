package fr.unice.polytech.ecoknowledge.language.api.util;

import org.json.JSONObject;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

/**
 * Created by Sébastien on 27/11/2015.
 */

public class HTTPCall {

    public static Response POST(String ipAddress, String service, JSONObject media){
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(ipAddress + service);
        Invocation.Builder b = resource.request();
        System.out.println("\t---> Sending request to '" + ipAddress+service + "'");

        Entity e = Entity.entity(media.toString(5), MediaType.APPLICATION_JSON);

        return b.post(e);
    }

}
