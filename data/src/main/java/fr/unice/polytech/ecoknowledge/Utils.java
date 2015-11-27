package fr.unice.polytech.ecoknowledge;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import fr.unice.polytech.ecoknowledge.connexion.ConnexionManager;
import org.bson.Document;

/**
 * Created by Benjamin on 27/11/2015.
 */
public class Utils {

	public String displayTableNames() {
		ConnexionManager connexionManager = ConnexionManager.getInstance();
		MongoClient mongoConnection = connexionManager.getMongoConnection();

		String result = "\n==============================================\n";
		result = result.concat("\t\tTables\n");
		result = result.concat("\n==============================================\n");

		for (String s : mongoConnection.listDatabaseNames()) {
			result = result.concat("+\tTable : " + s);
		}

		return result;
	}
}
