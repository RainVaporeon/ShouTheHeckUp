package com.spiritlight.shoutheheckup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigSpirit {
    private static boolean antiLoop = false;

    public static void getConfig() throws IOException {
        File config = new File("config/STHU.json");
        if (config.exists()) {
            try {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(new FileReader("config/STHU.json"));
                for (JsonElement element : jsonObject.getAsJsonArray("players")) {
                    STHU.players.add(element.getAsString());
                }
                for (JsonElement element : jsonObject.getAsJsonArray("bannedWords")) {
                    STHU.bannedWords.add(element.getAsString());
                }
                STHU.hidelevel = Integer.parseInt(String.valueOf(jsonObject.get("hidelevel")));
                STHU.filterChat = jsonObject.get("chatfilter").getAsBoolean();
            }  catch (NullPointerException exception) {
                System.out.println("New configuration files found?");
                if(antiLoop) return;
                antiLoop = true;
                writeConfig();
                getConfig();
            }
        } else {
            writeConfig();
        }
    }

    public static void writeConfig() throws IOException {
        JsonWriter writer = new JsonWriter(new FileWriter("config/STHU.json"));
        writer.beginObject();
        writer.name("hidelevel").value(STHU.hidelevel);
        writer.name("chatfilter").value(STHU.filterChat);
        writer.name("players");
        writer.beginArray();
        for (String player : STHU.players) {
            writer.value(player);
        }
        writer.endArray();
        writer.name("bannedWords");
        writer.beginArray();
        for (String word : STHU.bannedWords) {
            writer.value(word);
        }
        writer.endArray();
        writer.endObject();
        writer.close();
    }
}