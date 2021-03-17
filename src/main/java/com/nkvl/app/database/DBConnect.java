package com.nkvl.app.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public final class DBConnect {
    public static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    public static final MongoDatabase dbClient = client.getDatabase("brupbot");
}
