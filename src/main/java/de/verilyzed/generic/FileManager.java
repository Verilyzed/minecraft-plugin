package de.verilyzed.generic;

import de.verilyzed.krassalla.KrassAlla;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.UUID;

public class FileManager {
    public static JSONObject getJSONObject(UUID uuid) {
        try {
            FileReader fileReader = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + uuid + ".json");
            Scanner scanner = new Scanner(fileReader);

            JSONParser parser = new JSONParser();
            scanner.close();

            return (JSONObject) parser.parse(scanner.nextLine());
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
