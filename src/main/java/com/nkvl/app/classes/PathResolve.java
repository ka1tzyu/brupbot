package com.nkvl.app.classes;

import com.nkvl.app.App;

public final class PathResolve {
    public static String getPathTo(String pattern) {
        switch (pattern) {
            case "log.conf" -> {
                return App.isDebug ? "src/main/resources/log4j.properties" : "classes/log4j.properties";
            }
            case "app.conf" -> {
                return App.isDebug ? "src/main/resources/app.config" : "classes/app.config";
            }
            default -> {
                return "";
            }
        }
    }
}
