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
        System.err.println("BDD URL SET TO");
        System.err.println(System.getenv("MONGOHQ_URL"));

        //mongo ds059524.mongolab.com:59524/heroku_3jmsqjbq -u pfe -p ecoknowledge
        //   mongodb://username:password@host1:port1/database


        boolean local = false;
        //  deployed
        try {
            mongo = new MongoClient(new MongoClientURI(System.getenv("MONGOHQ_URL")));
            System.err.println("DEPLOYED MODE");
        }
        catch(Exception e) {
            e.printStackTrace();
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
