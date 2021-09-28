package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.User;
import de.verilyzed.service.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;
import java.util.logging.LogRecord;


public class onPlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //Welcome message
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§6+§8] §f" + e.getPlayer().getName());
        }
        Bukkit.getScheduler().runTaskAsynchronously(KrassAlla.plugin, () -> {
            User user = new User(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
            UserService.ensureUserExists(user);
            KrassAlla.log.log(new LogRecord(Level.FINE, "User now exists in DB"));
        });
    }
}