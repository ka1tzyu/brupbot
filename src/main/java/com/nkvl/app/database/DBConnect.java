package com.nkvl.app.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.nkvl.app.classes.ConfigurationReader;

public final class DBConnect {
    public static final MongoClient client = MongoClients.create(
            String.format("mongodb://%s:27017",
                    ConfigurationReader.getPropertyValue("DBIPAdress")));
    public static final MongoDatabase dbClient = client.getDatabase("brupbot");
}
