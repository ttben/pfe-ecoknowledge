import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import exceptions.ChallengeNotFoundException;
import exceptions.GoalNotFoundException;
import exceptions.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBConnector implements DocumentBDDConnector {
	public static String DB_NAME = "pfe";
	public static String TRACKING_REQUESTS_COLLECTION = "trackingRequests";
	public static String CHALLENGES_COLLECTION = "challenges";
	public static String USERS_COLLECTION = "users";
	public static String GOALS_COLLECTION = "goals";
	public static String RESULTS_COLLECTION = "results";

	final Logger logger = LogManager.getLogger(MongoDBConnector.class);

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

	public void storeTrackingRequest(JsonObject trackingRequestsDescription) {
		MongoCollection<Document> collection = getCollection(TRACKING_REQUESTS_COLLECTION);
		collection.insertOne(Document.parse(trackingRequestsDescription.toString()));
		logger.info("\n\t+ Saved new tracking request :\n" + trackingRequestsDescription);
	}

	public JsonArray findAllTrackingRequest() {
		// TODO: 24/01/2016 filter db requests where now between start and end dates, and where now - lastTimeUpdated >= THRESHOLD_TO_DEFINE
		return findAll(TRACKING_REQUESTS_COLLECTION);
	}


	@Override
	public void storeChallenge(JsonObject challengeJsonDescription) {
		MongoCollection<Document> collection = getCollection(CHALLENGES_COLLECTION);
		collection.insertOne(Document.parse(challengeJsonDescription.toString()));
		logger.info("\n\t+ Just inserted challenge :\n" + challengeJsonDescription);
	}

	@Override
	public void storeGoal(JsonObject goalJsonDescription) {
		MongoCollection<Document> collection = getCollection(GOALS_COLLECTION);
		collection.insertOne(Document.parse(goalJsonDescription.toString()));
		logger.info("\n\t+ Just inserted goal :\n" + goalJsonDescription);
	}

	@Override
	public void storeResult(JsonObject goalResultJsonDescription) {
		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);
		collection.insertOne(Document.parse(goalResultJsonDescription.toString()));
		logger.info("\n\t+ Just inserted result :\n" + goalResultJsonDescription);
	}

	@Override
	public void storeUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);
		collection.insertOne(Document.parse(userJsonDescription.toString()));
		logger.info("\n\t+ Just inserted user :\n" + userJsonDescription);
	}

	@Override
	public void updateUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);

		String id = userJsonDescription.get("id").getAsString();
		Document newUserDocument = Document.parse(userJsonDescription.toString());

		collection.replaceOne(Filters.eq("id", id), newUserDocument);
	}

	public void updateGoalResult(JsonObject goalResultJsonDescription) {
		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);

		String id = goalResultJsonDescription.get("id").getAsString();
		Document newGoalResultDocument = Document.parse(goalResultJsonDescription.toString());

		collection.replaceOne(Filters.eq("id", id), newGoalResultDocument);
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

	public JsonArray findNotTakenChallengeForUser(String userID) {
		JsonArray result = new JsonArray();

		List<Document> challengesIDCursor =
				getCollection(GOALS_COLLECTION)
						.find(Filters.eq("user", userID))
						.projection(Projections.include("challenge"))
						.into(new ArrayList<Document>());


		MongoCursor<Document> challengesCollection =
				getCollection(CHALLENGES_COLLECTION)
						.find(Filters.in("id", challengesIDCursor))
						.iterator();


		return result;
	}

	@Override
	public JsonObject findGoalResult(String goalResultID) {
		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);

		String res = "";
		MongoCursor<Document> findIterable = collection.find().iterator();
		while (findIterable.hasNext()) {
			res += findIterable.next().toJson().toString() + "\n";
		}

		logger.info("DB CONTENT : " + res);
		logger.info("Searched id : " + goalResultID);

		Document result = collection.find(Filters.eq("id", goalResultID)).projection(Projections.exclude("_id")).first();

		logger.info("Document found : " + result);

		JsonParser parser = new JsonParser();

		if (result == null) {
			return null;
		}

		JsonObject persistedJsonObject = parser.parse(result.toJson()).getAsJsonObject();

		logger.info("Goal result found : " + persistedJsonObject);

		return persistedJsonObject;
	}

	@Override
	public void deleteGoalByID(String goalID) throws GoalNotFoundException {
		MongoCollection<Document> collection = getCollection(GOALS_COLLECTION);
		Document result = collection.find(Filters.eq("id", goalID)).projection(Projections.exclude("_id")).first();

		if(result != null) {
			collection.deleteOne(result);
		} else {
			throw new GoalNotFoundException(goalID);
		}
	}

	@Override
	public void deleteChallengeByID(String challengeID) throws ChallengeNotFoundException {
		MongoCollection<Document> collection = getCollection(CHALLENGES_COLLECTION);
		Document result = collection.find(Filters.eq("id", challengeID)).projection(Projections.exclude("_id")).first();

		if(result != null) {
			collection.deleteOne(result);
		} else {
			throw new ChallengeNotFoundException(challengeID);
		}
	}

	@Override
	public void deleteUserByID(String userID) throws UserNotFoundException {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);
		Document result = collection.find(Filters.eq("id", userID)).projection(Projections.exclude("_id")).first();

		if(result != null) {
			collection.deleteOne(result);
		} else {
			throw new UserNotFoundException(userID);
		}
	}

	@Override
	public void updateGoal(JsonObject goalJsonDescription) {
		MongoCollection<Document> collection = getCollection(GOALS_COLLECTION);

		String id = goalJsonDescription.get("id").getAsString();
		Document newGoalDocument = Document.parse(goalJsonDescription.toString());

		collection.replaceOne(Filters.eq("id", id), newGoalDocument);
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

	public void drop(String dbName) {
		logger.info("Dropping db" + dbName);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = mongoDatabase.getCollection(dbName);
		collection.drop();
		logger.info(dbName + " dropped !");
	}

	public void deleteAllTrackingRequests() {
		getCollection(TRACKING_REQUESTS_COLLECTION).drop();
	}
}
