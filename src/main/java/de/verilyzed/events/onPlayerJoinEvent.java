package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
        }

        Path jsonpath = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\PlayerData\\" + e.getPlayer().getUniqueId() + ".json");

        //Create file for each new joining user
        if (!Files.exists(jsonpath)) {
            FileWriter file = null;
            try {
                JSONObject json = new JSONObject();
                json.put("money", 100);

                JSONArray backpack = new JSONArray();

                json.put("backpack", backpack);

                file = new FileWriter(jsonpath.toString());
                file.write(json.toJSONString());

            } catch (IOException exception) {
                KrassAlla.log("IOException at onPlayerJoin.");
            } finally {
                try {
                    file.close();
                } catch (IOException exception) {
                    KrassAlla.log("IOException at onPlayerJoin.");
                }
            }
        } else {
            KrassAlla.log("File already exists.");
        }
    }
}
