package fr.unice.polytech.ecoknowledge.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.unice.polytech.ecoknowledge.data.utils.ConnexionManager;
import org.bson.Document;
import org.json.JSONObject;

/**
 * Created by Sébastien on 24/11/2015.
 */
public class BadgePersistance {

    private static final String TABLE_NAME = "badge";

    public static JSONObject store(JSONObject json) {

        MongoCollection<Document> collection = ConnexionManager.getInstance().getCollection(TABLE_NAME);
        collection.insertOne(Document.parse(json.toString()));

        MongoCursor cursor = collection.find(Document.parse(json.toString())).iterator();
        Document doc = (Document) cursor.next();

        JSONObject jsob = new JSONObject(doc.toJson());
        return (JSONObject) jsob.get("id");

    }

}
