package de.verilyzed.krassalla;

import de.verilyzed.commands.buy;
import de.verilyzed.commands.test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX;

    @Override
    public void onEnable() {
        // Plugin startup logic

        loadConfig();

        enableCommands();

        log("Plugin loaded.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        PREFIX = this.getConfig().getString("Config.General.PREFIX");
    }

    public void enableCommands() {
        getCommand("test").setExecutor(new test());
        getCommand("buy").setExecutor(new buy());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static void log(String text) {
        System.out.println(PREFIX + text);
    }
}
