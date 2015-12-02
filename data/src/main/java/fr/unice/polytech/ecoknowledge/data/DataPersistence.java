package fr.unice.polytech.ecoknowledge.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import fr.unice.polytech.ecoknowledge.data.utils.ConnexionManager;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 01/12/2015.
 */
public class DataPersistence {
	public enum Collections {
		CHALLENGE("challenges"),
		BADGES("badges"),
		USER("users"),
		GOAL("goals");

		private String collectionName;

		Collections(String collectionName) {
			this.collectionName = collectionName;
		}
	}

	public static String DB_NAME = "pfe";

	public static JsonObject store(Collections targetCollection, JsonObject json) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		collection.insertOne(Document.parse(json.toString()));

		MongoCursor cursor = collection.find(Document.parse(json.toString()) ).projection(Projections.exclude("_id")).iterator();
		Document doc = (Document) cursor.next();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(doc.toJson()).getAsJsonObject();

		return (JsonObject) persistedJsonObject;
	}


	public static List<JsonObject> findGoal(String idUser) {

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(Collections.GOAL.collectionName);

		FindIterable<Document> docs = collection.find(new Document("user.id", idUser));

		ArrayList<JsonObject> res = new ArrayList<>();
		for(Document doc : docs) {
			JsonParser parser = new JsonParser();
			res.add(parser.parse(doc.toJson()).getAsJsonObject());
		}

		return res;
	}


	public static JsonObject read(Collections targetCollection, String id) {
		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		JsonParser parser = new JsonParser();
		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		return persistedJsonObject;
	}

	public static JsonArray readAll(Collections targetCollection) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

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

	public static void drop(Collections targetCollection) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);
		collection.drop();
	}

	public static void drop(Collections targetCollection, String id) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		collection.deleteOne(result);
	}
}
