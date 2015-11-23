/**
 * Created by Sébastien on 23/11/2015.
 */


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MongoDBConnexionTest {

    private MongoCollection collection;

    @Before
    public void createDB(){

        // Connect to mongoDB
        MongoClient mongo = new MongoClient(System.getenv("MONGOHQ_URL"), 27017);
        // Search for the database
        MongoDatabase db = mongo.getDatabase("db");
        // Search for the table
        collection = db.getCollection("table");

    }

    @After
    public void destroyCollection(){

        // Destroy the table
        collection.drop();

    }

    @Test
    public void connexion(){

        // Create an object to store
        Document document = new Document();
        document.put("test1", "test1");
        document.put("test2", "test2");
        collection.insertOne(document);

        // Search this object
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("test1", "test1");

        MongoCursor cursor = collection.find(searchQuery).iterator();

        // Make sure there is an non empty answer
        Assert.assertTrue(cursor.hasNext());

        Object bdbo = cursor.next();

        // Make sure there is an object of the good type
        Assert.assertEquals(bdbo.getClass(), Document.class);

        Document myDoc = (Document) bdbo;

        // Make sure the object contains the second key
        Assert.assertEquals(myDoc.get("test2"), "test2");

    }

    @Test
    public void insertJson(){

        // Create a document from a JSON expression
        Document myDoc = Document.parse("{\"expression\":{\"left\":8,\"operand\":\"<\",\"right\":9}}");

        // Push it in the DataBase
        collection.insertOne(myDoc);

        // Search this object
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.get("expression");

        MongoCursor cursor = collection.find(searchQuery).iterator();

        // Make sure there is an non empty answer
        Assert.assertTrue(cursor.hasNext());

        Object bdbo = cursor.next();

        // Make sure it's a document
        Assert.assertEquals(bdbo.getClass(), Document.class);

        Document doc = (Document) bdbo;

        JSONObject jsob = new JSONObject(doc.toJson());

        // Check a part of this JSON
        Assert.assertEquals(((JSONObject)jsob.get("expression")).get("left"), 8);

    }
}
