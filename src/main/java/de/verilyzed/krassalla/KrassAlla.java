package de.verilyzed.krassalla;

import de.verilyzed.commands.buy;
import de.verilyzed.commands.echo;
import de.verilyzed.commands.test;
import de.verilyzed.events.onPlayerJoinEvent;
import de.verilyzed.events.onPlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();

        enableCommands();

        enableListener();

        log("Plugin loaded.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Path path = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder().toString() + "\\PlayerData\\");

        try {
            Files.createDirectories(path);
        } catch (IOException e) {

        }

        PREFIX = this.getConfig().getString("Config.General.PREFIX");
    }

    public void enableCommands() {
        getCommand("test").setExecutor(new test());
        getCommand("buy").setExecutor(new buy());
        getCommand("echo").setExecutor(new echo());
    }

    public void enableListener() {
        getServer().getPluginManager().registerEvents(new onPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuitEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void log(String text) {
        System.out.println(PREFIX + text);
    }
}
