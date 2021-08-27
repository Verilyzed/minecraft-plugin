package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
            try {
                Path path = Files.createFile(jsonpath);

                JSONObject json = new JSONObject();
                json.put("money", 100);

                FileWriter file = new FileWriter(jsonpath.toString());
                file.write(json.toJSONString());

            } catch (IOException exception) {
                KrassAlla.log("File already exists.");
            }
        } else {
            KrassAlla.log("File already exists.");
        }
    }
}
