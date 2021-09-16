package de.verilyzed.generic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Other {
    public static @NotNull UUID getPlayerUUID(String Name) {
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (!Name.equalsIgnoreCase(receiver.getName()))
                continue;
            return receiver.getUniqueId();
        }
        return UUID.fromString("");
    }

    public static List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            names.add(p.getName());
        }

        return names;
    }

    public static List<String> getOnlinePlayerNames(String str) {
        List<String> names = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            names.add(str + p.getName());
        }

        return names;
    }
}
