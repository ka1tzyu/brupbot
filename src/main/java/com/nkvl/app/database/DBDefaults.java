package com.nkvl.app.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.Document;

public final class DBDefaults {
    public static void createDocument(String collection, Document doc) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.insertOne(doc);

    }
    public static Document getDocument(String collection, String key, String value) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        return coll.find(new Document(key, value)).first();
    }
    public static Document getDocument(String collection, String key, long value) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        return coll.find(new Document(key, value)).first();
    }
    public static String getValueOf(String collection, String key, String value, String keyOfValue) {
        Document doc = getDocument(collection, key, value);
        return doc.get(keyOfValue).toString();
    }
    public static String getValueOf(String collection, String key, long value, String keyOfValue) {
        Document doc = getDocument(collection, key, value);
        return doc.get(keyOfValue).toString();
    }
    public static boolean isDocumentExist(String collection, String key, String value) {
        return getDocument(collection, key, value) != null;
    }
    public static boolean isDocumentExist(String collection, String key, long value) {
        return getDocument(collection, key, value) != null;
    }
    public static void updateDocument(String collection, String key, String value,
                                      String keyToUpdate, String valueToUpdate) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.updateOne(Filters.eq(key, value), Updates.set(keyToUpdate, valueToUpdate));
    }
    public static void updateDocument(String collection, String key, long value,
                                      String keyToUpdate, String valueToUpdate) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.updateOne(Filters.eq(key, value), Updates.set(keyToUpdate, valueToUpdate));
    }
    public static void updateDocument(String collection, String key, long value,
                                      String keyToUpdate, int valueToUpdate) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.updateOne(Filters.eq(key, value), Updates.set(keyToUpdate, valueToUpdate));
    }
    public static void deleteDocument(String collection, String key, String value) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.deleteOne(Filters.eq(key, value));
    }
    public static void deleteDocument(String collection, String key, long value) {
        MongoCollection<Document> coll = DBConnect.dbClient.getCollection(collection);
        coll.deleteOne(Filters.eq(key, value));
    }
}
