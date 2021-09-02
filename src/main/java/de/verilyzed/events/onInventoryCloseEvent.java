package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class onInventoryCloseEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Backpack")) {
            e.getPlayer().sendMessage(KrassAlla.PREFIX + "Closing Backpack.");

            Inventory inv = e.getInventory();

            JSONObject json = new JSONObject();

            try {
                FileReader fr = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + e.getPlayer().getUniqueId() + ".json");
                Scanner scanner  = new Scanner(fr);

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());

                jsonObject.remove("backpack");

                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < inv.getSize(); i++) {
                    if (inv.getItem(i) != null) {
                        ItemStack item = inv.getItem(i);

                        JSONArray itemArray = new JSONArray();
                        itemArray.add(i);
                        itemArray.add(item.getType().toString());
                        itemArray.add(item.getAmount());

                        jsonArray.add(itemArray);
                    }
                }

                jsonObject.put("backpack", jsonArray);


                FileWriter fileWriter = new FileWriter(KrassAlla.dataFolder + "/PlayerData/" + e.getPlayer().getUniqueId() + ".json");
                fileWriter.write(jsonObject.toJSONString());

                fileWriter.close();
                scanner.close();
                fr.close();
            } catch (IOException | ParseException exception) {
                exception.printStackTrace();
            }
        }
    }
}
