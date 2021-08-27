package de.verilyzed.krassalla;

import org.bukkit.plugin.java.JavaPlugin;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX = "§b§6[§2KrassAlla§b§6]§f ";

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void log(String text) {
        System.out.println(PREFIX + text);
    }
}
