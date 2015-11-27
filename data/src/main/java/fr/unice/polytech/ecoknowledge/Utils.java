package fr.unice.polytech.ecoknowledge;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import fr.unice.polytech.ecoknowledge.connexion.ConnexionManager;
import org.bson.BsonString;
import org.bson.Document;

/**
 * Created by Benjamin on 27/11/2015.
 */
public class Utils {

	public String displayTableNames() {
		ConnexionManager connexionManager = ConnexionManager.getInstance();
		MongoClient mongoConnection = connexionManager.getMongoConnection();

		String result = "\n==============================================\n";
		result = result.concat("\t\t\tTables");
		result = result.concat("\n==============================================\n");

		for (String s : mongoConnection.listDatabaseNames()) {
			result = result.concat("\t\t+\tTable : " + s + "\n");
		}

		return result;
	}

	public String displayAllContentInStr() {
		ConnexionManager connexionManager = ConnexionManager.getInstance();
		MongoClient mongoConnection = connexionManager.getMongoConnection();

		String result = "\n==============================================\n";
		result = result.concat("\t\t\tTables");
		result = result.concat("\n==============================================\n");

		for (String s : mongoConnection.listDatabaseNames()) {
			MongoDatabase newDB = mongoConnection.getDatabase(s);

			result = result.concat("\n\t+\tTable : ");
			result = result.concat(s);
			result = result.concat("\n-------------------------------------------------------\n");

			MongoIterable<String> listCollectionNames = newDB.listCollectionNames();
			for (String collectionName : listCollectionNames) {
				for (Document challenges : newDB.getCollection(collectionName).find()) {
					result = result.concat(challenges.toJson().toString() + "\n");
				}
			}

		}

		return result;
	}

	public Object createTable(String name) {

		MongoClient mongoClient = ConnexionManager.getInstance().getMongoConnection();
		MongoDatabase newDB = mongoClient.getDatabase(name);

		newDB.createCollection("challenges");

		Document obj = new Document("lol", new BsonString("caca"));

		newDB.getCollection("challenges").insertOne(obj);

		String result = "\n==============================================\n";
		result = result.concat("\t\t\tTables");
		result = result.concat("\n==============================================\n");

		for (Document challenges : newDB.getCollection("challenges").find()) {
			result = result.concat(challenges.toJson().toString());
		}

		return result;
	}
}
