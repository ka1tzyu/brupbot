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
    public static boolean isUserExist(long id) {
        return DBDefaults.isDocumentExist("user", "_id", id);
    }
    public static String getUserValue(long id, String key) {
        return DBDefaults.getValueOf("user", "_id", id, key);
    }
    public static void deleteUser(long id) {
        DBDefaults.deleteDocument("user", "_id", id);
    }
    public static void updateUser(long id, String key, String value) {
        DBDefaults.updateDocument("user", "_id", id, key, value);
    }


}
