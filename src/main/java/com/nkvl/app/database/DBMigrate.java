package com.nkvl.app.database;

public final class DBMigrate {
    public static void main(String[] args) {
        DBConnect.dbClient.createCollection("user");
        DBConnect.dbClient.createCollection("user_med");
        DBConnect.dbClient.createCollection("user_stat");
    }
}
