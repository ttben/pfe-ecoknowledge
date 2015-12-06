package fr.unice.polytech.ecoknowledge.domain.data.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import fr.unice.polytech.ecoknowledge.domain.data.DocumentBDDConnector;
import org.bson.Document;

public class MongoDBConnector implements DocumentBDDConnector {
	public static String DB_NAME = "pfe";
	public static String CHALLENGES_COLLECTION = "challenges";
	public static String USERS_COLLECTION = "users";
	public static String GOALS_COLLECTION = "goals";

	private static MongoDBConnector instance;
	private MongoClient mongoClient;

	public MongoClient getMongoConnection() {
		return this.mongoClient;
	}

	private MongoDBConnector() {
		mongoClient = new MongoClient(System.getenv("MONGOHQ_URL"), 27017);
	}

	public static MongoDBConnector getInstance() {
		if (instance == null) {
			instance = new MongoDBConnector();
		}

		return instance;
	}

	@Override
	public void storeChallenge(JsonObject challengeJsonDescription) {
		MongoCollection<Document> collection = getCollection(CHALLENGES_COLLECTION);
		collection.insertOne(Document.parse(challengeJsonDescription.toString()));
	}

	@Override
	public void storeGoal(JsonObject goalJsonDescription) {
		MongoCollection<Document> collection = getCollection(GOALS_COLLECTION);
		collection.insertOne(Document.parse(goalJsonDescription.toString()));
	}

	@Override
	public void storeUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);
		collection.insertOne(Document.parse(userJsonDescription.toString()));
	}

	@Override
	public void updateUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);

		String id = userJsonDescription.get("id").getAsString();
		Document newUserDocument = Document.parse(userJsonDescription.toString());

		collection.replaceOne(Filters.eq("id", id), newUserDocument);
	}

	@Override
	public JsonArray findAllChallenges() {
		return findAll(CHALLENGES_COLLECTION);

	}

	@Override
	public JsonArray findAllGoals() {
		return findAll(GOALS_COLLECTION);
	}

	@Override
	public JsonArray findAllUsers() {
		return findAll(USERS_COLLECTION);
	}

	@Override
	public JsonArray findGoalsOfUser(String userId) {
		return findAllMatchingKeyValue(GOALS_COLLECTION, "user", userId);
	}

	@Override
	public JsonObject findChallenge(String challengeID) {
		return findOne(CHALLENGES_COLLECTION, challengeID);
	}

	@Override
	public JsonObject findGoal(String goalID) {
		return findOne(GOALS_COLLECTION, goalID);
	}

	@Override
	public JsonObject findUser(String userID) {
		return findOne(USERS_COLLECTION, userID);
	}

	private JsonArray findAll(String targetCollection) {
		JsonArray jsonArray = new JsonArray();

		MongoCollection<Document> collection = getCollection(targetCollection);
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

	private JsonObject findOne(String targetCollection, String id) {
		MongoCollection<Document> collection = getCollection(targetCollection);

		Document result = collection.find(Filters.eq("id", id)).projection(Projections.exclude("_id")).first();

		JsonParser parser = new JsonParser();

		if (result == null) {
			return null;
		}

		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		return persistedJsonObject;
	}

	private JsonArray findAllMatchingKeyValue(String targetCollection, String key, String value) {
		MongoCollection<Document> collection = getCollection(targetCollection);

		JsonArray result = new JsonArray();

		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).projection(Projections.exclude("_id")).iterator();

		try {
			while (cursor.hasNext()) {
				result.add(new JsonParser().parse(cursor.next().toJson()).getAsJsonObject());
			}
		} finally {
			cursor.close();
		}

		return result;
	}

	private MongoCollection<Document> getCollection(String targetCollection) {
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(targetCollection);
		return collection;
	}
}
