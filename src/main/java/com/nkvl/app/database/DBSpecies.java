package com.nkvl.app.database;

import org.bson.Document;

public final class DBSpecies {
    public static void createUser(long id, String name, String username) {
        Document user = new Document("_id", id)
                .append("name", name)
                .append("username", username)
                .append("time", 0)
                .append("medals", new Document(
                        "emx", 0)
                        .append("emed", "none")
                        .append("hmx", 0)
                        .append("hmed", "none"));
        DBDefaults.createDocument("user", user);
    }

}
