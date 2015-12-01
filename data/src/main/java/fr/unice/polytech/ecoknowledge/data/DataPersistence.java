package fr.unice.polytech.ecoknowledge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import fr.unice.polytech.ecoknowledge.data.utils.ConnexionManager;
import org.bson.Document;

/**
 * Created by Benjamin on 01/12/2015.
 */
public class DataPersistence {
	public static String CHALLENGE_COLLECTION = "challenges";
	public static String BADGE_COLLECTION = "badges";
	public static String USER_COLLECTION = "users";

	public static String DB_NAME = "pfe";

	public static JsonObject store(String collectionName, JsonObject json) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

		collection.insertOne(Document.parse(json.toString()));

		MongoCursor cursor = collection.find(Document.parse(json.toString()) ).projection(Projections.exclude("_id")).iterator();
		Document doc = (Document) cursor.next();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(doc.toJson()).getAsJsonObject();

		return (JsonObject) persistedJsonObject;
	}

	public static JsonObject read(String collectionName, String id) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		return persistedJsonObject;
	}

	public static JsonArray readAll(String collectionName) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

		MongoCursor<Document> cursor = collection.find().projection(Projections.exclude("_id")).iterator();
		try {
			while (cursor.hasNext()) {
				jsonArray.add(new JsonParser().parse(cursor.next().toJson()).getAsJsonObject());
			}
		} finally {
			cursor.close();
		}

		return jsonArray;
	}

	public static void drop(String collectionName) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
		collection.drop();
	}

	public static void drop(String collectionName, String id) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		collection.deleteOne(result);
	}
}
