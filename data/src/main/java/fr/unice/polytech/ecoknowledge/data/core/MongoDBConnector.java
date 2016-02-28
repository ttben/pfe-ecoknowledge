package fr.unice.polytech.ecoknowledge.data.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import fr.unice.polytech.ecoknowledge.data.exceptions.ChallengeNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.GoalNotFoundException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserBadPasswordException;
import fr.unice.polytech.ecoknowledge.data.exceptions.UserNotFoundException;
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
	public static String SENSOR_DATA_COLLECTION = "sensorData";
	private static MongoDBConnector instance;
	final Logger logger = LogManager.getLogger(MongoDBConnector.class);
	private MongoClient mongoClient;

	private MongoDBConnector() {
		mongoClient = new MongoClient(System.getenv("MONGOHQ_URL"), 27017);
	}

	public static MongoDBConnector getInstance() {

		//Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
		//d'éviter un appel coûteux à synchronized,
		//une fois que l'instanciation est faite.
		if (MongoDBConnector.instance == null) {
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation
			// multiple même par différents "threads".
			// Il est TRES important.
			synchronized(MongoDBConnector.class) {
				if (MongoDBConnector.instance == null) {
					MongoDBConnector.instance = new MongoDBConnector();
				}
			}
		}
		return MongoDBConnector.instance;
	}

	public MongoClient getMongoConnection() {
		return this.mongoClient;
	}

	public void storeTrackingRequest(JsonObject trackingRequestsDescription) {
		MongoCollection<Document> collection = getCollection(TRACKING_REQUESTS_COLLECTION);
		collection.insertOne(Document.parse(trackingRequestsDescription.toString()));
		logger.info("Saved new tracking request : " + trackingRequestsDescription);
	}

	public JsonArray findAllTrackingRequest() {
		return findAll(TRACKING_REQUESTS_COLLECTION);
	}


	@Override
	public void storeChallenge(JsonObject challengeJsonDescription) {
		MongoCollection<Document> collection = getCollection(CHALLENGES_COLLECTION);
		collection.insertOne(Document.parse(challengeJsonDescription.toString()));
		logger.info("Just inserted challenge : " + challengeJsonDescription.get("id")
				+ " into " + collection.getNamespace().getDatabaseName() + "/" + collection.getNamespace().getCollectionName());
	}

	@Override
	public void storeGoal(JsonObject goalJsonDescription) {
		MongoCollection<Document> collection = getCollection(GOALS_COLLECTION);
		collection.insertOne(Document.parse(goalJsonDescription.toString()));
		logger.info("Just inserted goal : " + goalJsonDescription.get("id")
				+ " into " + collection.getNamespace().getDatabaseName() + "/" + collection.getNamespace().getCollectionName());
	}

	@Override
	public void storeResult(JsonObject goalResultJsonDescription) {
		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);
		collection.insertOne(Document.parse(goalResultJsonDescription.toString()));
		logger.info("Just inserted result : " + goalResultJsonDescription.get("id")
				+ " into " + collection.getNamespace().getDatabaseName() + "/" + collection.getNamespace().getCollectionName());
	}

	@Override
	public void storeUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);
		collection.insertOne(Document.parse(userJsonDescription.toString()));
		logger.info("Just inserted user : " + userJsonDescription.get("id")
				+ " into " + collection.getNamespace().getDatabaseName() + "/" + collection.getNamespace().getCollectionName());
	}

	@Override
	public void updateUser(JsonObject userJsonDescription) {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);

		String id = userJsonDescription.get("id").getAsString();
		Document newUserDocument = Document.parse(userJsonDescription.toString());

		collection.replaceOne(Filters.eq("id", id), newUserDocument);
	}

	public void updateGoalResult(JsonObject goalResultJsonDescription) {
		String goalResultID = goalResultJsonDescription.get("id").getAsString();
		//logger.warn("Storing goal result (" + goalResultID + ")");

		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);

		String id = goalResultJsonDescription.get("id").getAsString();
		Document newGoalResultDocument = Document.parse(goalResultJsonDescription.toString());

		collection.updateOne(Filters.eq("id", id), new Document("$set", newGoalResultDocument),new UpdateOptions().upsert(true));

		JsonArray allGoalResult = this.findAllGoalResult();
		//logger.info("\n\nNumberOfGoalResults:"+allGoalResult.size() + "\n\nGoalResultDBContent\n " +  allGoalResult+"\n");
	}

	@Override
	public JsonArray findAllChallenges() {
		return findAll(CHALLENGES_COLLECTION);
	}

	@Override
	public JsonArray findAllGoals() {
		return findAll(GOALS_COLLECTION);
	}

	public JsonArray findAllGoalResult() { return findAll(RESULTS_COLLECTION);}

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

		Document result = collection.find(Filters.eq("id", goalResultID)).projection(Projections.exclude("_id")).first();

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

		if (result != null) {
			collection.deleteOne(result);
		} else {
			throw new GoalNotFoundException(goalID);
		}
	}

	@Override
	public void deleteChallengeByID(String challengeID) throws ChallengeNotFoundException {
		MongoCollection<Document> collection = getCollection(CHALLENGES_COLLECTION);
		Document result = collection.find(Filters.eq("id", challengeID)).projection(Projections.exclude("_id")).first();

		if (result != null) {
			collection.deleteOne(result);
		} else {
			throw new ChallengeNotFoundException(challengeID);
		}
	}

	@Override
	public void deleteUserByID(String userID) throws UserNotFoundException {
		MongoCollection<Document> collection = getCollection(USERS_COLLECTION);
		Document result = collection.find(Filters.eq("id", userID)).projection(Projections.exclude("_id")).first();

		if (result != null) {
			collection.deleteOne(result);
		} else {
			throw new UserNotFoundException(userID);
		}
	}

	public void deleteGoalResultByID(String goalResult) throws GoalNotFoundException {
		MongoCollection<Document> collection = getCollection(RESULTS_COLLECTION);
		Document result = collection.find(Filters.eq("id", goalResult)).projection(Projections.exclude("_id")).first();

		if (result != null) {
			collection.deleteOne(result);
		} else {
			throw new GoalNotFoundException("Goal result : " + goalResult);
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

	public void drop(String collectionName) {
		logger.info("Dropping db" + DB_NAME + "/"+collectionName+" ! ");
		MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);

		MongoCollection collection = mongoDatabase.getCollection(collectionName);
		collection.drop();

		logger.info(collectionName + " dropped !");
	}

	public void deleteAllTrackingRequests() {
		getCollection(TRACKING_REQUESTS_COLLECTION).drop();
	}

	public void updateTrackingRequest(JsonObject newDocumentJsonDescription) {

		MongoCollection<Document> collection = getCollection(TRACKING_REQUESTS_COLLECTION);
		Document newDocument = Document.parse(newDocumentJsonDescription.toString());

		String id = newDocumentJsonDescription.get("id").getAsString();

		collection.updateOne(Filters.eq("id", id), new Document("$set", newDocument));
	}

	public void storeData(JsonObject newDocumentJsonDescription) {
		MongoCollection<Document> collection = getCollection(SENSOR_DATA_COLLECTION);
		Document newDocument = Document.parse(newDocumentJsonDescription.toString());

		String id = newDocumentJsonDescription.get("id").getAsString();

		collection.updateOne(Filters.eq("id", id), new Document("$set", newDocument), new UpdateOptions().upsert(true));

		logger.info("Saved new sensor data : " + newDocumentJsonDescription);
	}

	public JsonObject getSensorData(String sensorName) {
		return findOne(SENSOR_DATA_COLLECTION, sensorName);
	}

	public JsonObject getSensorDataBetweenDates(String sensorName, long dateStart, long dateEnd) {
		JsonObject allSensorData = getSensorData(sensorName);

		logger.debug("All data in DB for sensor " + sensorName + " : " + allSensorData);

		JsonArray remainingDataValues = new JsonArray();

		//	Handle case where no value has ever been stored
		if(allSensorData != null) {

			JsonArray sensorDataValues = allSensorData.getAsJsonArray("values");

			for (JsonElement elementDataValue : sensorDataValues) {
				JsonObject currentDataValue = elementDataValue.getAsJsonObject();
				long currentDate = currentDataValue.get("date").getAsLong();

				if (currentDate <= dateEnd && currentDate >= dateStart) {
					JsonObject remainingDataValue = new JsonObject();
					remainingDataValue.addProperty("date", currentDate);
					remainingDataValue.addProperty("value", currentDataValue.get("value").getAsDouble());

					remainingDataValues.add(remainingDataValue);
				}
			}
		}

		JsonObject result = new JsonObject();
		result.addProperty("id", sensorName);
		result.add("values", remainingDataValues);

		return result;
	}

	public JsonObject findUserByLogging(String mail, String password) throws UserBadPasswordException {

		JsonArray users = findAllMatchingKeyValue(USERS_COLLECTION, "mail", mail);
		if(users == null || users.size() == 0) return null;

		if(!users.get(0).getAsJsonObject().get("password").getAsString().equals(password))
			throw new UserBadPasswordException("User with mail ".concat(mail).concat(" didn't match this password ... -> ").concat(users.get(0).getAsJsonObject().toString()));

		return users.get(0).getAsJsonObject();
	}
}
