package fr.unice.polytech.ecoknowledge.domain.data;

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
import fr.unice.polytech.ecoknowledge.domain.data.utils.MongoDBConnector;
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
		System.out.println("\n+ DataPersistence : store on " + targetCollection + " : " + json.toString());

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		collection.insertOne(Document.parse(json.toString()));

		return json;
	}


	public static List<JsonObject> findGoal(String idUser) {

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(Collections.GOAL.collectionName);

		FindIterable<Document> docs = collection.find(new Document("user", idUser));

		ArrayList<JsonObject> res = new ArrayList<>();
		for (Document doc : docs) {
			JsonParser parser = new JsonParser();
			res.add(parser.parse(doc.toJson()).getAsJsonObject());
		}

		return res;
	}


	public static JsonObject read(Collections targetCollection, String id) {
		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		JsonParser parser = new JsonParser();

		if (result == null) {
			return null;
		}

		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		return persistedJsonObject;
	}

	public static JsonArray readAll(Collections targetCollection) {
		JsonArray jsonArray = new JsonArray();

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
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

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);
		collection.drop();
	}

	public static void drop(Collections targetCollection, String id) {
		System.out.println("\n+ DataPersistence : drop on " + targetCollection + " : " + id);

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		if(result != null)
			collection.deleteOne(result);
	}


	public static void update(Collections targetCollection, String id, String newOne) {

		MongoClient mongoClient = MongoDBConnector.getInstance().getMongoConnection();
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection.collectionName);

		collection.replaceOne(Filters.eq("id", id), Document.parse(newOne));
	}
}
