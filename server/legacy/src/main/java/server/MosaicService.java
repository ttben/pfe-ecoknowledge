package server;

import com.google.gson.JsonArray;
import fr.unice.polytech.ecoknowledge.domain.Controller;
import fr.unice.polytech.ecoknowledge.domain.data.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.IncoherentDBContentException;
import fr.unice.polytech.ecoknowledge.domain.data.exceptions.NotReadableElementException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Sébastien on 09/12/2015.
 *
 *
 * Just now to keep the 1.1 Requirement for the 13/12/15 delivery
 *
 */

@Path("/mosaic")
public class MosaicService {

    @GET
    public Response getUserMosaic(@QueryParam("userID") String userID){
        System.out.println("MOSAIC");
        JsonArray resultJsonArray = new JsonArray();
        try {
            JsonArray notTaken = Controller.getInstance().getNotTakenChallengesOfUser(userID);
            System.out.println("\n\nNOT TAKEN :\n" + notTaken.toString() + "\n");
            JsonArray goals = Controller.getInstance().getGoalsResultOfUser(userID);
            System.out.println("\n\nGOALS:\n" + goals.toString() + "\n");

            resultJsonArray.addAll(notTaken);
            resultJsonArray.addAll(goals);
        } catch (IncoherentDBContentException | GoalNotFoundException | IOException | NotReadableElementException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }

        return Response.ok().entity(resultJsonArray.toString()).build();
    }

}
