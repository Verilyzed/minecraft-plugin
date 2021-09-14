package de.verilyzed.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("deprecation")
public class onPlayerQuitEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        //Quitting message
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§4-§8] §f" + e.getPlayer().getName());
            e.setQuitMessage("");
        }
    }
}
