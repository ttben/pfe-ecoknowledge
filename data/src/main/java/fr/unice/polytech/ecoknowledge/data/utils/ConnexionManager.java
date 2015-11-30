package fr.unice.polytech.ecoknowledge.data.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by Sébastien on 24/11/2015.
 */
public class ConnexionManager {

    private static final String DB_NAME = "ecodatabase";
    private static ConnexionManager instance;
    private MongoClient mongo;

    public MongoClient getMongoConnection() {
        return this.mongo;
    }

    private ConnexionManager() {
        System.err.println("BDD URL is set to " + System.getenv("MONGOHQ_URL"));

        boolean local = false;
        //  deployed
        try {
            mongo = new MongoClient(new MongoClientURI(System.getenv("MONGOHQ_URL")));
            System.err.println("DEPLOYED MODE");
        }
        catch(Exception e) {
            local = true;
        }

        //  local
        if(local) {
            System.err.println("LOCAL MODE");
            mongo = new MongoClient(System.getenv("MONGOHQ_URL"));
        }
    }

    public static ConnexionManager getInstance() {
        if(instance == null)
            instance = new ConnexionManager();
        return instance;
    }

    public MongoCollection<Document> getCollection(String name){
        return mongo.getDatabase(name).getCollection(name);
    }
}
