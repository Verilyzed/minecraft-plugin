package de.verilyzed.krassalla;

import de.verilyzed.commands.*;
import de.verilyzed.events.onInventoryCloseEvent;
import de.verilyzed.events.onPlayerJoinEvent;
import de.verilyzed.events.onPlayerQuitEvent;
import de.verilyzed.tabcompleter.JsonTabCompleter;
import de.verilyzed.tabcompleter.MoneyTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX;

    public static String dataFolder;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();

        enableCommands();

        enableTabCompleter();

        enableListener();

        log("Plugin loaded.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Path path = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder() + "/PlayerData/");

        dataFolder = getDataFolder().toString();

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PREFIX = this.getConfig().getString("Config.General.PREFIX");
    }

    public void enableCommands() {
        Objects.requireNonNull(getCommand("test")).setExecutor(new test());
        Objects.requireNonNull(getCommand("buy")).setExecutor(new buy());
        Objects.requireNonNull(getCommand("echo")).setExecutor(new echo());
        Objects.requireNonNull(getCommand("backpack")).setExecutor(new backpack());
        Objects.requireNonNull(getCommand("money")).setExecutor(new money());
        Objects.requireNonNull(getCommand("json")).setExecutor(new json());
        Objects.requireNonNull(getCommand("bounty")).setExecutor(new bounty());
    }

    public void enableTabCompleter() {
        Objects.requireNonNull(getCommand("json")).setTabCompleter(new JsonTabCompleter());
        Objects.requireNonNull(getCommand("money")).setTabCompleter(new MoneyTabCompleter());
    }

    public void enableListener() {
        getServer().getPluginManager().registerEvents(new onPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new onInventoryCloseEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void log(String text) {
        System.out.println(PREFIX + text);
    }
}
