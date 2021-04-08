package com.nkvl.app.database;

import com.nkvl.app.App;
import com.nkvl.app.classes.Wasted;
import org.apache.log4j.Level;
import org.bson.Document;

import java.util.*;

public final class DBSpecies {
    public static void createUser(long id, String name, String username) {
        Document user = new Document("_id", id)
                .append("name", name)
                .append("username", username)
                .append("time", 0);
        Document user_med = new Document("_id", id)
                .append("emed", "нету")
                .append("hmed", "нету")
                .append("emx", 0)
                .append("hmx", 0);
        Document user_stat = new Document("_id", id);
        DBDefaults.createDocument("user", user);
        DBDefaults.createDocument("user_med", user_med);
        DBDefaults.createDocument("user_stat", user_stat);
    }
    public static boolean isUserExist(long id) {
        return DBDefaults.isDocumentExist("user", "_id", id);
    }
    public static String getUserValue(long id, String key) {
        return DBDefaults.getValueOf("user", "_id", id, key);
    }
    public static String getUserMedValue(long id, String key) {
        return DBDefaults.getValueOf("user_med", "_id", id, key);
    }
    public static void updateUserMed(long id, String key, String value) {
        DBDefaults.updateDocument("user_med", "_id", id, key, value);
    }
    public static void updateUserMed(long id, String key, int value) {
        DBDefaults.updateDocument("user_med", "_id", id, key, value);
    }
    @Deprecated
    public static String getStatByDate(long id, String key, String mode) {
        if (mode.equals("easy")) {
            return DBDefaults.getValueOf("user_stat", "_id", id, key + "--e");
        } else {
            return DBDefaults.getValueOf("user_stat", "_id", id, key + "--h");
        }

    }
    public static void updateStatValue(long id, int value, String mode) {
        if (mode.equals("easy")) {
            DBDefaults.updateDocument("user_stat", "_id", id, Wasted.getToday() + "--e", value);
        } else {
            DBDefaults.updateDocument("user_stat", "_id", id, Wasted.getToday() + "--h", value);
        }

    }
    @Deprecated
    public static void deleteUser(long id) {
        DBDefaults.deleteDocument("user", "_id", id);
    }
    @Deprecated
    public static void updateUser(long id, String key, String value) {
        DBDefaults.updateDocument("user", "_id", id, key, value);
    }
    public static void updateUser(long id, String key, int value) {
        DBDefaults.updateDocument("user", "_id", id, key, value);
    }
    public static Dictionary<String, Integer> getUserStatValues(long id, String mode, int range) {
        Document doc = DBDefaults.getDocument("user_stat", "_id", id);
        List<Object> keys = new LinkedList<>(Arrays.asList(doc.keySet().toArray()));

        keys.removeIf(obj -> !obj.toString().contains(mode.equals("easy") ? "--e" : "--h"));

        while (keys.size() > range) {
            keys.remove(0);
        }

        Dictionary<String, Integer> results = new Hashtable<>();

        for (Object obj : keys) {
            results.put(obj.toString(), doc.getInteger(obj));
        }

        return results;
    }

}
