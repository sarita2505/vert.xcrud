package org.example.db_connection;

import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfigLoader {
    private static Properties properties = new Properties();
    private static JsonObject jsonObject = new JsonObject();

    public static JsonObject getDbConfig() {
        if (jsonObject.isEmpty()) {
            readConfig();
            for (Object key : properties.keySet()) {
                jsonObject.put(key.toString(), properties.getProperty(key.toString()));
            }
        }
        return jsonObject;
    }

    private static void readConfig() {
        try {
            InputStream stream = DatabaseConfigLoader.class.getClassLoader().getResourceAsStream("data.properties");
            properties.load(stream);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
