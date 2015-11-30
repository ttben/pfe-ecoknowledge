package fr.unice.polytech.ecoknowledge;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import fr.unice.polytech.ecoknowledge.connexion.ConnexionManager;
import org.bson.Document;

/**
 * Created by Benjamin on 24/11/2015.
 */
public class ChallengePersistence {
	private static final String COLLECTION_NAME = "challenge";
	private static final String DB_NAME = "pfe";

	public static JsonObject store(JsonObject json) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);

		collection.insertOne(Document.parse(json.toString()));

		JsonObject parameters = new JsonParser().parse("{ _id : false } ").getAsJsonObject();


		MongoCursor cursor = collection.find(Document.parse(json.toString()) ).projection(Projections.exclude("_id")).iterator();
		Document doc = (Document) cursor.next();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(doc.toJson()).getAsJsonObject();

		System.out.println("\n\nPERSISTED OBJECT : " + persistedJsonObject.toString());

		return (JsonObject) persistedJsonObject;
	}

	public static JsonObject read(String projectId) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_NAME);

		JsonObject parameters = new JsonParser().parse("{ _id : false } ").getAsJsonObject();


		Document result = collection.find(Filters.eq("id", projectId)).projection(Projections.exclude("_id")).first();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		return persistedJsonObject;
	}
}
