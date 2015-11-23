import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Sébastien on 23/11/2015.
 */
public class Test {

    public static void main(String args[]) {
/*
        MongoClient c = new MongoClient("localhost" , 27017);
        MongoDatabase db = c.getDatabase("db");
        Document d = new Document();
        d.put("life", "life");
        d.put("lala", "lalala");

        MongoCollection table = db.getCollection("table");
        table.insertOne(d);

        BasicDBObject query = new BasicDBObject();
        query.put("life", "life");

        MongoCursor cursor = table.find(query).iterator();
        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }

*/
        try {

            /**** Connect to MongoDB ****/
            // Since 2.10.0, uses MongoClient
            MongoClient mongo = new MongoClient("localhost", 27017);

            /**** Get database ****/
            // if database doesn't exists, MongoDB will create it for you
            DB db = mongo.getDB("testdb");

            /**** Get collection / table from 'testdb' ****/
            // if collection doesn't exists, MongoDB will create it for you
            DBCollection table = db.getCollection("user");

            /**** Insert ****/
            // create a document to store key and value
            BasicDBObject document = new BasicDBObject();
            document.put("name", "mkyong");
            document.put("age", 30);
            document.put("createdDate", new Date());
            table.insert(document);

            /**** Find and display ****/
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", "mkyong");

            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }

            /**** Update ****/
            // search document where name="mkyong" and update it with new values
            BasicDBObject query = new BasicDBObject();
            query.put("name", "mkyong");

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", "mkyong-updated");

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            table.update(query, updateObj);

            /**** Find and display ****/
            BasicDBObject searchQuery2
                    = new BasicDBObject().append("name", "mkyong-updated");

            DBCursor cursor2 = table.find(searchQuery2);

            while (cursor2.hasNext()) {
                System.out.println(cursor2.next());
            }

            /**** Done ****/
            System.out.println("Done");

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
