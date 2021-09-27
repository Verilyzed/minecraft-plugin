package de.verilyzed.events;

import de.verilyzed.generic.FileManager;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.User;
import de.verilyzed.service.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unchecked")
public class onPlayerJoinEvent implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerJoin(PlayerJoinEvent e) {
        //Welcome message
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§6+§8] §f" + e.getPlayer().getName());
            e.setJoinMessage("");
        }

        JSONObject json = initUserJSON(e.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(KrassAlla.plugin, () -> {
            User user = new User(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), 100, new JSONObject());
            UserService.ensureUserExists(user);
        });
    }

    private JSONObject initUserJSON(Player e) {
        Path jsonpath = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder() + "/PlayerData/" + e.getUniqueId() + ".json");
        JSONObject json = new JSONObject();
        json.put("name", e.getName());
        json.put("money", 100);
        JSONArray backpack = new JSONArray();
        json.put("backpack", backpack);
        if (!Files.exists(jsonpath)) {
            FileManager.setJSONObject(e.getUniqueId(), json);
        } else {
            KrassAlla.log("File already exists.");
        }
        return json;
    }
}