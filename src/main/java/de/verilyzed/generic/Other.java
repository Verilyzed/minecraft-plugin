package de.verilyzed.generic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
}
