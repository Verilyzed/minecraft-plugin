package de.verilyzed.events;

import de.verilyzed.generic.BusinessLogic;
import de.verilyzed.generic.FileManager;
import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class onPlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //Welcome message
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§6+§8] §f" + e.getPlayer().getName());
            e.setJoinMessage("");
        }

        Path jsonpath = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "/PlayerData/" + e.getPlayer().getUniqueId() + ".json");

        //Create file for each new joining user
        if (!Files.exists(jsonpath)) {
            JSONObject json = new JSONObject();
            json.put("name", e.getPlayer().getName());
            json.put("money", 100);

            JSONArray backpack = new JSONArray();

            json.put("backpack", backpack);

            FileManager.setJSONObject(e.getPlayer().getUniqueId(), json);
            BusinessLogic.createUserinDatabase(e.getPlayer().getUniqueId(), json);
        } else {
            KrassAlla.log("File already exists.");
        }
    }
}
