package fr.unice.polytech.ecoknowledge.server;

import fr.unice.polytech.ecoknowledge.Controller;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sébastien on 24/11/2015.
 */


@Path("/badge")

public class BadgeService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBadge(String jsonParam){


        JSONObject json = new JSONObject(jsonParam);

        JSONObject response = Controller.getInstance().createBadge(json);

        if(response.getBoolean("valid"))
            return Response.ok(response.toString()).build();
        return Response.status(Response.Status.BAD_REQUEST).entity(json.toString()).build();
    }

    @GET
    @Path("/${badgeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadge(@PathParam("badgeId") String badgeId){

        JSONObject response = Controller.getInstance().searchBadge();

        if(response.getBoolean("valid"))
            return  Response.ok(response.toString()).build();
        return Response.status(Response.Status.NOT_FOUND).build();

    }
}
