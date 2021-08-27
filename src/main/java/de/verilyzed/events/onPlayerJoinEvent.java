package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class onPlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\");

        if (!Files.exists(Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\" + e.getPlayer().getUniqueId() + ".json"))) {
            try {
                Files.createFile(Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\" + e.getPlayer().getUniqueId() + ".json"));
            } catch (IOException exception) {
                KrassAlla.log("File already exists.");
            }
        } else {
            KrassAlla.log("File already exists.");
        }
    }
}
