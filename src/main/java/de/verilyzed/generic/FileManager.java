package de.verilyzed.generic;

import de.verilyzed.krassalla.KrassAlla;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class FileManager {
    public static JSONObject getJSONObject(UUID uuid) {
        try {
            FileReader fileReader = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + uuid + ".json");
            Scanner scanner = new Scanner(fileReader);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());
            scanner.close();

            return jsonObject;
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static boolean setJSONObject(UUID uuid, JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(KrassAlla.dataFolder + "/PlayerData/" + uuid + ".json");

            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
