package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class onPlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //Welcome message
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§6+§8] §f" + e.getPlayer().getName());
        }

        //Create file for each new joining user
        if (!Files.exists(Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\PlayerData\\" + e.getPlayer().getUniqueId() + ".json"))) {
            try {
                Files.createFile(Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\PlayerData\\" + e.getPlayer().getUniqueId() + ".json"));
            } catch (IOException exception) {
                KrassAlla.log("File already exists.");
            }
        } else {
            KrassAlla.log("File already exists.");
        }
    }
}
