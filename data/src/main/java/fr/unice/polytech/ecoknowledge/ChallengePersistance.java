package fr.unice.polytech.ecoknowledge;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import fr.unice.polytech.ecoknowledge.connexion.ConnexionManager;
import org.bson.Document;

/**
 * Created by Benjamin on 24/11/2015.
 */
public class ChallengePersistance {
	private static final String COLLECTION_NAME = "challenge";
	private static final String DB_NAME = "pfe";

	public static JsonObject store(JsonObject json) {

		MongoCollection<Document> collection = ConnexionManager.getInstance().getCollection(COLLECTION_NAME);
		collection.insertOne(Document.parse(json.toString()));

		MongoCursor cursor = collection.find(Document.parse(json.toString())).iterator();
		Document doc = (Document) cursor.next();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(doc.toJson()).getAsJsonObject();
		return (JsonObject) persistedJsonObject.get("id");
	}
}
